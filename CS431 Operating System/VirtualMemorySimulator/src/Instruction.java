class Instruction {
    int rwBit; // 0-read 1-write
    String address; // virtual address
    int writeValue;
        
    Instruction(int rwBit, String instr) { // default write value = 0
        this(rwBit, instr, 0);
    }

    Instruction(int rwBit, String address, int writeValue){
        this.rwBit = rwBit;
        this.address = address;
        this.writeValue = writeValue;
    }

    /*getters and setters*/
    int getRWBit(){return rwBit;}
    String getAddress(){return address;}
    int getWriteValue(){return writeValue;}
    void setRWBit(int rwBit){this.rwBit = rwBit;}
    void setAddress(String address){this.address = address;}
    void setWriteValue(int writeValue){this.writeValue = writeValue;}
}