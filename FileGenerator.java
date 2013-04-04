import java.io.*;
import java.nio.charset.Charset;

public class FileGenerator {

    public static void main(String[] args){
        genFile(Integer.parseInt(args[0]));
    }

    public static void genFile(int n) {
        try {
            // Create file 
            FileWriter fstream = new FileWriter(n + ".txt");
            BufferedWriter out = new BufferedWriter(fstream);
            for (int i = 0; i < 2 * n * n; i++) {
                String s = "" + (int)(Math.round(Math.random()));
                out.write(s + "\n");
            }
            //Close the output stream
            out.close();
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}