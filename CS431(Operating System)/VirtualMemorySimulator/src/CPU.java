import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class CPU {
    MMU mmu;
    Scanner instructionScanner;
    
    CPU(PageTable pageTable, File testFile) {
        this.mmu = new MMU(pageTable);
        try {
            instructionScanner = new Scanner(testFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    String passInstruction(Instruction instruction,int count){
        if (instruction.rwBit == 0) {
            return mmu.performInstruction(instruction,count);
        } else if (instruction.rwBit == 1) {
            return mmu.performInstruction(instruction,count);
        }
        return null;
    }

    class MMU {
        TLB tlb;
        PageTable pageTable;
        
        MMU(PageTable pageTable) {
            tlb = new TLB();
            this.pageTable = pageTable;
        }
            
        /**
         * performs an instruction
         * returns "hit","soft",or "hard" to OS
         */
        String performInstruction(Instruction instruction, int count) {
            String vPgNum = instruction.address.substring(0, 2);
            TLBEntry entry = tlb.getTLBEntry(vPgNum);
            if (entry != null) { //converts hex to int
                return "hit";//hit
            } else {
                PageTableEntry ptEntry = pageTable.getPageTableEntry(vPgNum);
                if (ptEntry != null) {
                    return "soft";//soft
                } else {
                    if(count >= 16) count = -1;
                    mmu.addEntry(instruction.getAddress(), count);
                    return "hard";//hard
                }
            }
        }
        
        //physical address = pageSize*frameNumber+offset
        int performHit(TLBEntry entry, String vPgNum){
            entry.setRBit(1);
            tlb.replaceEntry(entry,vPgNum);
            return entry.getPageFrameNum();
        }
        
        int performSoftMiss(PageTableEntry ptEntry, String vPgNum){
            ptEntry.setRBit(1);
            pageTable.replaceEntry(ptEntry, vPgNum);
            tlb.addTLBEntry(new TLBEntry(ptEntry,vPgNum));
            return ptEntry.getPageFrameNum();
        }
        
        /* set d bit to 1 to both tlb */
        void setDBit(String vPgNum){
            TLBEntry TEntry = tlb.getTLBEntry(vPgNum);
            TEntry.setdBit(1);
            tlb.replaceEntry(TEntry, vPgNum);
        }

        /* set r bit to 1 to tlb */
        void setRBit(String vPgNum){
            TLBEntry TEntry = tlb.getTLBEntry(vPgNum);
            TEntry.setRBit(1);
            tlb.replaceEntry(TEntry, vPgNum);
        }
        
        // do this in hard miss, returns page table
        PageTable addEntry(String address,int count){
            String vPgNum = address.substring(0, 2);
            // frame number = converted int of hex(vPgNum) since 1-1 mapping for all pgNum
            int pfNum = count;
            TLBEntry entry = new TLBEntry(vPgNum,pfNum);
            tlb.addTLBEntry(entry);
            pageTable.addPageTableEntry(entry);
            return pageTable;
        }
        
    }
}