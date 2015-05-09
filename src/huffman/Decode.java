package huffman;

import java.io.*;
import java.util.*;

public class Decode {
    private static List<CharCodePairDecode> charsAndCodeLens;
    private static Map<String, Character> codeCharMap;
    private static char[] fullCodeCharArray;
    private static List<Character> outputText;

    /**
     * Basically a copy-paste of the canonical-code builder from Encode,
     * but refitted to work for Decode.
     */
    private static void sortCodesIntoCanon() {
        Collections.sort(charsAndCodeLens, new Comparator<CharCodePairDecode>() {
            @Override
            public int compare(CharCodePairDecode c1, CharCodePairDecode c2) {
                return c2.len - c1.len;
            }
        });

        Collections.sort(charsAndCodeLens, new Comparator<CharCodePairDecode>() {
            @Override
            public int compare(CharCodePairDecode c1, CharCodePairDecode c2) {
                return (c1.len == c2.len)
                        ? c1.character - c2.character
                        : 0;
            }
        });

        // Do all the stuff for the first element. For however long its code is,
        // make its new code all zeroes for the same length.
        CharCodePairDecode ccp = charsAndCodeLens.get(0);
        int len = ccp.len;
        String canonFirstCode = "";
        for (int i = 0; i < len; i++) {
            canonFirstCode += "0";
        }
        ccp.code = canonFirstCode;

        // For the rest of the codes, increment the binary number and store it
        // if the length of the current code and the previous one's match.
        // If the current code is shorter than the old one, shift the binary
        // to the right, padding with zeroes, a number of digits equal to the
        // difference between the lengths. Finally, pad the left side of the
        // string form of the binary with zeroes, to make it match the expected
        // length.
        int runningBinaryCode = Integer.parseInt(canonFirstCode, 2);
        int oldLen = canonFirstCode.length();
        int currLen;
        for (int j = 1; j < charsAndCodeLens.size(); j++) {
            ccp = charsAndCodeLens.get(j);
            currLen = ccp.len;

            runningBinaryCode += 1;

            if (oldLen > currLen) {
                runningBinaryCode += 1;
                runningBinaryCode >>>= (oldLen - currLen);
            }

            String canonCodeStr = Integer.toBinaryString(runningBinaryCode);

            while (canonCodeStr.length() < currLen) {
                canonCodeStr = "0" + canonCodeStr;
            }

            ccp.code = canonCodeStr;

            oldLen = currLen;
        }
    }

    /**
     * Puts the read-in canonical Huffman codes into a map,
     * with the character as the key.
     */
    private static void placeCodesIntoMap() {
        codeCharMap = new HashMap<String, Character>();
        for (CharCodePairDecode ccp : charsAndCodeLens) {
            codeCharMap.put(ccp.code, ccp.character);
        }
    }

    /**
     * Reads in the binary data from the given file,
     * one byte at a time.
     *
     * @param path the filepath for the input file
     */
    public static void readInputFile(String path) {
        try {
            charsAndCodeLens = new ArrayList<CharCodePairDecode>();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));

            // First byte will be the number of characters in the alphabet
            int numChars = bis.read();

            // The next `numChars` pairs of bytes will be
            //   1) the character value
            //   2) the length of the codeword for that character
            int charValue;
            int codeLength;
            for (int i = 0; i < numChars; i++) {
                charValue = bis.read();
                codeLength = bis.read();

                charsAndCodeLens.add(new CharCodePairDecode((char) charValue, codeLength));
            }

            sortCodesIntoCanon();
            placeCodesIntoMap();

            // The rest of the binary is the compressed codewords for each character
            // in the original input
            int currByte;
            StringBuilder sb = new StringBuilder();
            while ((currByte = bis.read()) >= 0) {
                String tempCode = Integer.toBinaryString(currByte);
                while (tempCode.length() < 8) {
                    tempCode = "0" + tempCode;
                }
                sb.append(tempCode);
            }

            fullCodeCharArray = sb.toString().toCharArray();

            bis.close();

        } catch (FileNotFoundException fnf) {
            System.err.format("File not found, fool: %s%n", fnf);
        } catch (IOException io) {
            System.err.format("IO exception, fool: %s%n", io);
        }
    }

    /**
     * Decodes the text by the canonical codes in the binary file
     * with the characters they represent.
     */
    public static void decodeText() {
        String builtCode = "";
        outputText = new ArrayList<Character>();
        for (int i = 0; i < fullCodeCharArray.length; i++) {

            // Tack on the current bit to the running code
            builtCode += fullCodeCharArray[i];

            // If we've built enough of a code to match one in the map,
            // add the character that matches that code to the running
            // output
            if (codeCharMap.containsKey(builtCode) && codeCharMap.get(builtCode) != '\u0000') {
                outputText.add(codeCharMap.get(builtCode));
                builtCode = "";
            }
        }
    }

    /**
     * Writes the decoded text to a file.
     *
     * @param path the path to the destination file
     */
    public static void writeToOutput(String path) {
        try {
            BufferedWriter bos = new BufferedWriter(new FileWriter(path));

            StringBuilder sb = new StringBuilder();
            for (char c : outputText) {
                sb.append(c);
            }

            bos.write(sb.toString());

            bos.close();

        } catch (IOException io) {
            io.printStackTrace();
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
        readInputFile(inFileName);
        decodeText();
        writeToOutput(outFileName);
    }
}
