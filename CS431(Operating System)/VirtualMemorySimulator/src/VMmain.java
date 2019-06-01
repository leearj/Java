// Michael Trinh,Jaeseung Lee, Chy Lim

import java.io.File;

public class VMmain {

    public static int hex2int(String val) {
        return Integer.parseInt(val, 16);
    }
    
    public static void main(String[] args) {
        File testFile = null;
        // Read in test file from command line
        if(args.length > 0){
            testFile = new File("test_files\\" + args[0]);
        } else {
            testFile = new File("test_files\\test_1.txt");
        }
        OS os = new OS(testFile);
        
    }
}
