package huffman;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Decode {

    private static int numChars;
    private static Map<Character, Integer> charsAndCodes;
    private static List<String> textCodes;
    private static List<Character> decodedText;

    /**
     * Reads in the binary data from the given file,
     * one byte at a time.
     *
     * @param path the filepath for the input file
     */
    public static void readInputFile(String path) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            int currByte;

            // TODO use bitshift operators to read the bits out of a byte

            // First byte will be the number of characters in the alphabet
            numChars = bis.read();

            // The next `numChars` pairs of bytes will be
            //   1) the character value
            //   2) the length of the codeword for that character
            // Let's store these pairings in a map so we can efficiently look
            // them up later
            int charValue;
            int codeLength;
            charsAndCodes = new TreeMap<Character, Integer>();
            for (int i = 0; i < numChars; i++) {
                charValue = bis.read();
                codeLength = bis.read();
                charsAndCodes.put((char) charValue, codeLength);
            }

            // The rest of the binary is the compressed codewords for each character
            // in the original input, of varying lengths (which we know from)
            textCodes = new ArrayList<String>();
            while ((currByte = bis.read()) >= 0) {
                // Do some magic with knowing the number of bits for each
                // codeword and translating it to a String with
                // Integer.toBinaryString(), padding on the left with 0s
                // if necessary
            }

            bis.close();

        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        readInputFile(inFileName);

        // HOW DID WE GET HERE I USED TO KNOW YOU SO WELL

        writeToOutput(outFileName);
    }
}
