class PageTable {
	PageTableEntry[] table;
        int maxSize;
	PageTable() {
            maxSize = 256;
            table = new PageTableEntry[maxSize];
	}

        /* lookup PageTable for entry, returns null if not found */
        PageTableEntry getPageTableEntry(String vPgNum){
            int index = VMmain.hex2int(vPgNum);
            if(index < maxSize){
                return table[index];
            }
            return null;
        }
        
        /* lookup TLB for index of entry, returns -1 if not found */
        int getIndexOfFrameNum(int pfn){ 
            for(int i=0;i<maxSize;i++){
                if(table[i] != null){
                    if(table[i].getPageFrameNum() == pfn){
                       return i;
                    }
                }
            }
            return -1;
        }
        
        void setPageTableToEntryWithPFN(int pfn,PageTableEntry entry){
            table[getIndexOfFrameNum(pfn)].setEntry(entry);
        }
        
        void setPageTableToEntryWithVPgNum(String vPgNum,PageTableEntry entry){
            table[VMmain.hex2int(vPgNum)] = entry;
        }
        
        PageTableEntry getEntryFromFrameNum(int pfn){
            for(int i = 0; i < table.length; i++){
                if(table[i] != null){
                    if(table[i].getPageFrameNum()== pfn ){
                        return table[i];
                    }
                }
            }
            return null;
        }
        
        /* adds Page Table Entry to Page Table, overwrites record when full using FIFO 
         * returns evicted Page Table entry or null, index determined by V-Page# */
        PageTableEntry addPageTableEntry(TLBEntry entry){
            PageTableEntry PTEntry = new PageTableEntry(entry);
            PageTableEntry evictedEntry = null;
            int pageNumIndex = VMmain.hex2int(entry.getVirtualPageNum());
            if(table[pageNumIndex] != null){
                evictedEntry = table[pageNumIndex];
            }
            table[pageNumIndex] = PTEntry;
            return evictedEntry;
        }
        
        /* replaces a table entry using virtual page number */
        void replaceEntry(PageTableEntry entry, String vPgNum){
            table[VMmain.hex2int(vPgNum)].setEntry(entry);
        }
        
        void printPageTable(){
            for(int i = 0; i < table.length; i++){
                if(table[i] != null){
                    System.out.print(i + " [");
                    table[i].printEntry();
                }
            }
        }
}

class PageTableEntry {
	int vBit, rBit, dBit, pfNum; // PageTableEntries for TLB: V-Page#, V, R, D, PageFrame#
        
        public PageTableEntry(PageTableEntry entry){
            this(entry.getVBit(),entry.getRBit(),entry.getDBit(),entry.getPageFrameNum());
        }
        
        public PageTableEntry(TLBEntry entry){
            this(entry.getVBit(),entry.getRBit(),entry.getDBit(),entry.getPageFrameNum());
        }
        
        public PageTableEntry(int pfNum){
            this(0, 1, 0, pfNum);
        }
        
	PageTableEntry(int vBit, int rBit, int dBit, int pfNum) {
            this.vBit = vBit;
            this.rBit = rBit;
            this.dBit = dBit;
            this.pfNum = pfNum;
	}
        
        /* getters and setters */
        int getVBit() {return vBit;}
        int getRBit() {return rBit;}
        int getDBit() {return dBit;}
        int getPageFrameNum() {return pfNum;}
        void setVBit(int vBit){this.vBit = vBit;}
        void setRBit(int rBit){this.rBit = rBit;}
        void setdBit(int dBit){this.dBit = dBit;}
        void setPageFrameNum(int pfNum){this.pfNum = pfNum;}
        
        void setEntry(PageTableEntry entry){
            vBit = entry.getVBit();
            rBit = entry.getRBit();
            dBit = entry.getDBit();
            pfNum = entry.getPageFrameNum();
        }
        void printEntry(){
            System.out.println(vBit+" | "+rBit+" | "+dBit+" | "+pfNum+"]");
        }
}