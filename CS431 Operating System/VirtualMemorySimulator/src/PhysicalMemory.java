import java.io.File;

class PhysicalMemory {
	int size;
        final int maxSize;
	int[][] ram; // [n][0]=page-frame # , [n][1]=page of byte-addressable, byte-sized data.
					// Example: 16pages, 1kb data = ram[16][1024]

	PhysicalMemory() {
            size = 0;
            maxSize = 16;
            ram = new int[16][256];
	}
        
        boolean isEmpty(){
            return size == maxSize;
        }
        
        void write(int pfn, Page page){
            for(int i = 0 ;i < 256; i++){
                ram[pfn][i] = page.data[i];
            }
            size++;
        }
        
        void print(){
            for(int i = 0; i<maxSize;i++){
                for(int j = 0; j<256;j++){
                    System.out.print(ram[i][j]+" ");
                }
                System.out.println("");
            }
        }
}

/* class Page: used in Physical Memory*/

// class Page {
// 	String addr; // address
// 	int[] data; // page numbers

// 	Page(String addr, int[] data) {
// 		this.addr = addr; // 00, 0A
// 		this.data = data; // 184,88,185...
// 	}
// }