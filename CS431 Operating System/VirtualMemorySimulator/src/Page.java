/* class Page: used in Physical Memory*/

class Page {
	String addr; // address
	int[] data; // page numbers

	Page(String addr, int[] data) {
		this.addr = addr; // 00, 0A
		this.data = data; // 184,88,185...
	}
        void print(){
            for(int i = 0; i<data.length;i++){
                System.out.print(data[i] + " ");
            }
        }
}