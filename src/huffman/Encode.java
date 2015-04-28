package huffman;

import java.io.*;

public class Encode {

    private static BufferedReader br;
    private static BufferedWriter bw;

    public static void readInputFile(String path) {
        try {
            br = new BufferedReader(new FileReader(path));
            // other stuff

        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
    }

    public static void writeToOutput(String path) {
        try {
            bw = new BufferedWriter(new FileWriter(path));
            // other stuff

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String inFileName  = args[0];
        String outFileName = args[1];
    }
}
