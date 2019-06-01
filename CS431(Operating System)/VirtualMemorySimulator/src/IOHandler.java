import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class IOHandler {

    Scanner sc;
    File[] pageFiles;
    File[] files; // 255 original page files
    File[] testFiles;
    String directory;
    Page[] pages_cpy; // 255 copied page files
    String[][] csvOutput;
    int size;
    
    IOHandler() {
        File dir = new File("page_files");
        pages_cpy = new Page[256];
        csvOutput = new String[5000][8]; 
        size = 0;
        readPageFiles(); // this automatically makes the copy of pages_cpy and create file.
		/* readTestFiles(); */
    }

    public void setPages_cpy(Page[] pages_cpy) {
        this.pages_cpy = pages_cpy;
    }

    void readPageFiles() {
        System.out.println("Pages copying...");
        Scanner sc;
        File dir;
        dir = new File("page_files");
        files = dir.listFiles(); // this array has all the list of page files: there will be 255 of them.
        Page[] pTemp = new Page[256]; // pages temp
        int[] dTemp = new int[256];
          
        for (int i = 0; i < 255; i++) { // Loop: 00~FF in page_files
            dTemp = new int[256]; // stores all the 256 lines data into the array:184,88...
            try {
                sc = new Scanner(files[i]);
                for (int j = 0; j < 255; j++) {
                    dTemp[j] = sc.nextInt();
                }
                pTemp[i] = new Page(files[i].getName().replace(".pg", ""), dTemp);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        makePageFilesCopy(pTemp);
        System.out.println("Pages successfully copied");
    }

    /*
     * copy all the contents and of given input page files and then store into one
     * folder.
     */
    private void makePageFilesCopy(Page[] pages_cpy) {
        setPages_cpy(pages_cpy);
        File dir = new File("page_files");
        File cpyDir = new File("page_files_cpy");
        if (!cpyDir.exists()) {
            cpyDir.mkdir();
        }

        String path = cpyDir.getAbsolutePath();

        for (int i = 0; i < pages_cpy.length; i++) {
            try {
                File cpyFile = new File(path + "\\" + files[i].getName());
                PrintWriter pw = new PrintWriter(cpyFile);
                BufferedReader reader = new BufferedReader(new FileReader(files[i]));

                String line = reader.readLine();
                while (line != null) {
                    pw.println(line);
                    line = reader.readLine();
                }
                pw.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * returns an array of PageFiles. *
     */
    void writePageFilesToHDD() {
        getPages_cpy();
    }

    public Page[] getPages_cpy() {
        pages_cpy[0].print();
        return pages_cpy;
    }

    void readTestFiles(String testFileName) {
        // Move the instruction reading part to here.
    }

    void outputCSVfile(String testFileName) {
        	// [0]:Address, [1]:RW(0or1), [2]:the value read or written,
        // [3]:soft_miss(0or1), [4]:hard_miss(0or1), [5]:A_hit(0,1), [6]:page number of
        // evicted page, [7]:was that page's dirty bit set.
        try {
            File outputFile = new File(testFileName + ".CSV");
            FileWriter fw;
            fw = new FileWriter(outputFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            String[] header = {"address", "r/w", "value", "soft", "hard", "hit", "evicted_pg#", "dirty_evicted_page"};
            pw.println(header[0] + "," + header[1] + "," + header[2] + ", " + header[3] + ", " + header[4] + ", "
                    + header[5] + ", " + header[6] + ", " + header[7]);

            for (int i = 0; i < csvOutput.length; i++) {
                pw.println(csvOutput[i][0] + ", " + csvOutput[i][1] + ", " + csvOutput[i][2] + ", " + csvOutput[i][3]
                        + ", " + csvOutput[i][4] + ", " + csvOutput[i][5] + ", " + csvOutput[i][6] + ", "
                        + csvOutput[i][7]);
            }
            pw.close();
            outputFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	// STEP3 // going each instruction in MMU void step3() { int count = 0;
	/*
     * while(fonr(instruction)!=null){ Instruction ins = new}
     * 
     * use pop to get instruction, check if that instruction is in TLB, if there is
     * no instruction in TLB, then check the table.
     */
}
