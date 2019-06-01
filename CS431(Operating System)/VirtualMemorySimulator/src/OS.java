import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
// OS resets r-bits every 10 instructions.
// OS uses the clock algorithm for page replacement
/*
 * OS to replace the page and write the evicted page back to the drive if the
 * dirty bit is set. The CPU should trap to the OS when hard misses occur.
 */

class OS {
	HDD hdd;
	CPU cpu;
	Page[] pages;
	PageTable pageTable;
        CircularLinkedList cll;
        PhysicalMemory mem;
        
	OS(File testFile) { // OS = highest level container
		hdd = new HDD();
                pageTable = new PageTable();
		cpu = new CPU(pageTable,testFile); // cpu needs access to page table, and OS doesn't need access to TLB
                mem = new PhysicalMemory();
                cll = new CircularLinkedList();
                cll.insertAtHead(0);
                initializeCLL();
                performAllInstructions();
//                System.out.println("TLB:");
//                cpu.mmu.tlb.printTLB();
//                System.out.println("Page Table:");
//		pageTable.printPageTable();
//                mem.print();
                hdd.io.outputCSVfile(testFile.getName());
                System.out.println("output saved");
	}
        
        void initializeCLL(){
            for(int i = 0; i < 16; i++){
                if(i == 15)
                    cll.tail = cll.head;
                else{
                    cll.insertAtTail(i+1);
                }
            }
        }
        
        void performAllInstructions() {
            int pageFrameNum = 0; // only used for first 16 hard misses
            int resetCount = 0;
            int writeValue = -1;
            while (cpu.instructionScanner.hasNextLine()) {
                int readOrWrite = Integer.parseInt(cpu.instructionScanner.nextLine());
                String address = cpu.instructionScanner.nextLine();
                Instruction instruction;
                if(readOrWrite == 1){ 
                    writeValue = Integer.parseInt(cpu.instructionScanner.nextLine());
                    instruction = new Instruction(readOrWrite, address, writeValue);
                }else {
                    instruction = new Instruction(readOrWrite, address);
                }
                String result = cpu.passInstruction(instruction, pageFrameNum);
                if (result.equals("hard")) { // hard miss
                    performHardMiss(instruction, pageFrameNum);
                    pageFrameNum++;
                }
                resetCount++;
                if(resetCount == 10){
                    resetRbitPageTable();
                    resetCount = 0;
                }
                saveOutput(instruction, result);
            }
            cpu.instructionScanner.close();
        }
        
    void saveOutput(Instruction instruction, String result){
        hdd.io.csvOutput[hdd.io.size][0]=instruction.address;
        hdd.io.csvOutput[hdd.io.size][1]=Integer.toString(instruction.rwBit);
        hdd.io.csvOutput[hdd.io.size][2]=Integer.toString(instruction.writeValue);
        hdd.io.csvOutput[hdd.io.size][3]=Integer.toString(0);
        hdd.io.csvOutput[hdd.io.size][4]=Integer.toString(0);
        hdd.io.csvOutput[hdd.io.size][5]=Integer.toString(0);
        switch(result){
            case "hit":
                hdd.io.csvOutput[hdd.io.size][3]=Integer.toString(1);
                break;
            case "soft":
                hdd.io.csvOutput[hdd.io.size][4]=Integer.toString(1);
                break;
            case "hard":
                hdd.io.csvOutput[hdd.io.size][5]=Integer.toString(1);
                break;
        }
        hdd.io.size++;        
    }    
        
    void performHardMiss(Instruction instruction,int pageFrameNum) {
        Page page = hdd.getPage(instruction.address);
        if (instruction.rwBit == 1) {
            page.data[VMmain.hex2int(instruction.address.substring(2, 4))] = 
                    instruction.writeValue;
        }
        if (pageFrameNum < 16) {// write to memory by page frame number    
            mem.write(pageFrameNum, page);
            TLBEntry entry = new TLBEntry(instruction.getAddress().substring(0,2), 
                    1, 0, 0, pageFrameNum);
            cpu.mmu.tlb.addTLBEntry(entry);
            pageTable.addPageTableEntry(entry);
            pageFrameNum++;
        } else { // if memory is full, use clock algorithm
            traverseAndCheck(page);
        }
    }
        
        /* traverse and check each page and evict if rbit = 0 */
        void traverseAndCheck(Page page){
            for(int i = 0; i<16;i++){
                PageTableEntry entry = getEntry(cll.head.data);
                if(entry != null){
                    if(entry.getRBit() == 0){
                    // evict
                    evict(page, entry, i);
                    }
                }
                else{
                    cll.head = cll.head.next;
                }
            }
        }
        
        /* get the Rbit from the pagetable mapped to the page frame num */
        PageTableEntry getEntry(int pageFrameNum){
            return pageTable.getEntryFromFrameNum(pageFrameNum);
        }
        
        void evict(Page page, PageTableEntry entry, int pageFrameNum){
            // copy old page to disk if dirty
            entry.setPageFrameNum(-1);
            entry.setVBit(0);
            String vPgNum = page.addr.substring(0, 2);
            PageTableEntry ptEntry= new PageTableEntry(1,1,0,pageFrameNum);
            pageTable.setPageTableToEntryWithVPgNum(vPgNum, ptEntry); // set page of the table
            cpu.mmu.tlb.addTLBEntry(new TLBEntry(ptEntry,vPgNum));
            for(int i = 0 ;i < 256; i++){
                mem.ram[pageFrameNum][i] = page.data[i];
            }
        }
        
        void resetRbitPageTable(){
            for(int i = 0;i<pageTable.maxSize;i++){
                PageTableEntry ptEntry = pageTable.table[i];
                if(ptEntry != null){
                    ptEntry.setRBit(0);
                }
            }
        }
        
        void write(Page page, int index){
            mem.write(index,page);
        }
    /** Inner Class1: CircularLinkedList **/    
    class CircularLinkedList {

        Node head;
        Node tail;
        int size;

        CircularLinkedList() {
            head = null;
            tail = null;
        }

        boolean isEmpty() {
            return head == null;
        }

        int getSize() {
            return 0;
        }

        void insertAtHead(int value) {
            Node nptr = new Node(value, null);
            nptr.setNext(head);

            // If the beginning node doesn't exist, create one.
            if (head == null) {
                head = nptr;
                nptr.setNext(head);
                tail = head;
            } // If not, set the pointer of tail to the head.
            else {
                tail.setNext(nptr);
                tail = nptr;
            }
        }

        void insertAtTail(int value) {
            Node nptr = new Node(value, null);
            nptr.setNext(head);
            if (head == null) {
                head = nptr;
                nptr.setNext(head);
                tail = head;
            } else {
                tail.setNext(nptr);
                tail = nptr;
            }
        }

        class Node {
                int data;
                Node next;

                Node() {this(0, null);} 
                Node(int data, Node next) {
                        this.data = data;
                        this.next = next;
                }

                int getData(){return data;}
                Node getNext(){return next;}
                void setData(int data){this.data = data;}
                void setNext(Node next){this.next = next;}
        }
    }
}
