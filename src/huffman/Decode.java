package huffman;

import java.io.*;

public class Decode {

    // readInputFile won't be void

    /**
     * Reads in the binary data from the given file,
     * one byte at a time.
     *
     * @param path the filepath for the input file
     */
    public static void readInputFile(String path) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            // other stuff

        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
    }

    /**
     * Writes the decoded text to a file.
     *
     * @param path the path to the destination file
     */
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
