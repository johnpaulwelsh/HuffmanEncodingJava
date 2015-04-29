package huffman;

import java.io.*;
import java.util.*;

public class Encode {

    /**
     * Reads in the character-based data from the given file,
     * one char at a time.
     *
     * @param path the filepath for the input file
     * @return     a List containing every char from the input file
     */
    public static List<Character> readInputFile(String path) {
        List<Character> chList = new ArrayList<Character>();

        try {
            int ch;
            BufferedReader br = new BufferedReader(new FileReader(path));

            // A character code of -1 represents the end of the input
            while ((ch = br.read()) >= 0) {
                chList.add((char) ch);
            }

            br.close();

        } catch (FileNotFoundException fnf) {
            System.err.format("File not found, fool: %s%n", fnf);
        } catch (IOException io) {
            System.err.format("IO exception, fool: %s%n", io);
        }

//        printList(chList);

        return chList;
    }

    public static void printList(List<Character> ls) {
        for (char c : ls) {
            System.out.println(c);
        }
    }

    public static void writeToOutput(String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            // other stuff
            bw.close();

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String inFileName  = args[0];
        String outFileName = args[1];
        readInputFile(inFileName);
    }
}
