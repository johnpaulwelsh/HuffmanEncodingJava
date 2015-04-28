package huffman;

import java.io.*;

public class Decode {

    private static BufferedInputStream bis;
    private static BufferedOutputStream bos;

    public static void readInputFile(String path) {
        try {
            bis = new BufferedInputStream(new FileInputStream(path));
            // other stuff

        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
    }

    public static void writeToOutput(String path) {
        try {
            bos = new BufferedOutputStream(new FileOutputStream(path));
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
