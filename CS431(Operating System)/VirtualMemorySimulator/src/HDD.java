
class HDD {
	Page[] pages;
	IOHandler io;
	
	HDD() {
		io = new IOHandler();
		pages = io.pages_cpy;
	}
        
        Page getPage(String address){
            address = address.substring(0, 2);
            int iAddress = VMmain.hex2int(address);
            for(int i = 0; i<256;i++){
                if(i == iAddress)
                    return pages[i];
            }
            return null;
        }
        
	/*public HDD(Object writePageFilesToHDD) {
		// TODO Auto-generated constructor stub
	}*/
}