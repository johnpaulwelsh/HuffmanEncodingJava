package huffman;

import java.io.*;

public class Decode {

    // won't be void
    public static void readInputFile(String path) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            // other stuff

        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
    }

    public static void writeToOutput(String path) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
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
