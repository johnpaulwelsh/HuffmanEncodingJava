package huffman;

import java.io.*;
import java.util.*;

public class Encode {

    private static BufferedReader br;
    private static BufferedWriter bw;

    public static void readInputFile(String path) {
        List<String> chList = new ArrayList<String>();

        try {
            String line;
            String[] lineList;
            br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                lineList = line.split("");
                chList.addAll(Arrays.asList(lineList));
            }

            br.close();

        } catch (FileNotFoundException fnf) {
            System.err.format("File not found, fool: %s%n", fnf);
        } catch (IOException io) {
            System.err.format("IO exception, fool: %s%n", io);
        }
    }

    public static void writeToOutput(String path) {
        try {
            bw = new BufferedWriter(new FileWriter(path));
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
