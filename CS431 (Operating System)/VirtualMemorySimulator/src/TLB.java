class TLB {
	TLBEntry[] table;
        int maxSize, currentSize, pageCounter;
	TLB() {
            maxSize = 16;
            currentSize = 0;
            table = new TLBEntry[maxSize]; // The TLB contains 16 entries.
	}
        
        /* lookup TLB for entry, returns null if not found */
        TLBEntry getTLBEntry(String vPgNum){ 
            for(int i=0;i<maxSize;i++){
               if(table[i] != null) {
                   if(table[i].getVirtualPageNum().equals(vPgNum))
                    return table[i];
               }
            }
            return null;
        }
        
        /* lookup TLB for index of entry, returns -1 if not found */
        int getIndexOfVirtualPageNumber(String vPgNum){ 
            for(int i=0;i<maxSize;i++){
               if(table[i].getVirtualPageNum().equals(vPgNum))
                   return i;
            }
            return -1;
        }
        
        /* adds TLB Entry to TLB, overwrites record when full using FIFO 
         * returns evicted TLB entry or null */
        TLBEntry addTLBEntry(TLBEntry entry){
            TLBEntry evictedEntry = null;
            if(currentSize == maxSize-1){
                currentSize = 0;
            }
            if(table[currentSize] != null){
                evictedEntry = table[currentSize];
            }
            table[currentSize] = entry;
            currentSize++;
            return evictedEntry;
        }
        
        /* replaces a table entry using virtual page number */
        void replaceEntry(TLBEntry entry, String vPgNum){
            table[getIndexOfVirtualPageNumber(vPgNum)].setEntry(entry);
        }
        
        void printTLB(){
            for(TLBEntry entry:table){
                if(entry != null)
                    entry.printEntry();
            }
        }
        
}

class TLBEntry {
	private String vPgNum;
        private int vBit, rBit, dBit, pfNum; // TableEntries for TLB: V-Page#, V, R, D, PageFrame#
        
        public TLBEntry(TLBEntry entry){
            this(entry.getVirtualPageNum(),entry.getVBit(),entry.getRBit(),
                    entry.getDBit(),entry.getPageFrameNum());
        }
        
        public TLBEntry(PageTableEntry ptEntry, String vPgNum){
            this(vPgNum,ptEntry.getVBit(),ptEntry.getRBit(),ptEntry.getDBit()
                    ,ptEntry.getPageFrameNum());
        }
        
        public TLBEntry(String vPgNum,int pfNum){
            this(vPgNum, 0, 1, 0, pfNum);
        }
        
	public TLBEntry(String vPgNum, int vBit, int rBit, int dBit, int pfNum) {
		this.vPgNum = vPgNum;
		this.vBit = vBit;
		this.rBit = rBit;
		this.dBit = dBit;
		this.pfNum = pfNum;
	}
        
        /* getters and setters */
        String getVirtualPageNum() {return vPgNum;}
        int getVBit() {return vBit;}
        int getRBit() {return rBit;}
        int getDBit() {return dBit;}
        int getPageFrameNum() {return pfNum;}
        void setVirtualPageNum(String vPgNum){this.vPgNum = vPgNum;};
        void setVBit(int vBit){this.vBit = vBit;}
        void setRBit(int rBit){this.rBit = rBit;}
        void setdBit(int dBit){this.dBit = dBit;}
        void setPageFrameNum(int pfNum){this.pfNum = pfNum;}
        void setEntry(TLBEntry entry){
            vPgNum = entry.getVirtualPageNum();
            vBit = entry.getVBit();
            rBit = entry.getRBit();
            dBit = entry.getDBit();
            pfNum = entry.getPageFrameNum();
        }
        void printEntry(){
            System.out.println("["+vPgNum+" | "+vBit+" | "+rBit+" | "+dBit+" | "+pfNum+"]");
        }
}