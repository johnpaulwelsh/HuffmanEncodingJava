package huffman;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

        return chList;
    }

    /**
     * Writes the encoded text to a file.
     *
     * @param path the path to the destination file
     */
    public static void writeToOutput(String path, PackageToEncode outputPkg) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // The number of character-code pairs
            baos.write(outputPkg.k);

            // Each character and its accompanying code
            for (int i = 0; i < outputPkg.chs.length; i++) {
                baos.write(outputPkg.chs[i]);
                baos.write(outputPkg.codeLens[i]);
            }

            // Combine all the encoded strings into one
            StringBuilder sb = new StringBuilder();
            for(String s : outputPkg.textCodes) {
                sb.append(s);
            }
            String fullEncoded = sb.toString();

            // Split the combined string on every 8 characters/digits
            List<String> splitOn8 = new ArrayList<String>();
            String sub;
            int start = 0;
            int end = 8;
            while (end < fullEncoded.length()) {
                sub = fullEncoded.substring(start, end);
                splitOn8.add(sub);
                start = end;
                end += 8;
            }

            // Just substring to the end, since there are fewer than 8 bits left
            // (and pad it with zeroes if we need to)
            sub = fullEncoded.substring(start);
            for (int i = 0; i < 8 - sub.length(); i++) {
                sub = sub + 0;
            }
            splitOn8.add(sub);

            // Put each substring into the file, parsed as a byte
            for (String s : splitOn8) {
                baos.write(Integer.parseInt(s, 2));
            }

            // Write it to the file
            baos.writeTo(fos);

        } catch (IOException io) {
            System.err.format("IO exception, fool: %s%n", io);
        }
    }

    /**
     * Entry point to the program, fool.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String inFileName  = args[0];
        String outFileName = args[1];
        List<Character> ls = readInputFile(inFileName);
        HuffEncoder huff = new HuffEncoder(ls);
        PackageToEncode outputPkg = huff.encode();
        writeToOutput(outFileName, outputPkg);
    }
}
