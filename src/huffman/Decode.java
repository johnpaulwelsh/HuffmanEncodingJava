package huffman;

import java.io.*;
import java.util.*;

public class Decode {

    private static int numChars;
    private static List<CharCodePairDecode> charsAndCodeLens;
    private static Map<String, Character> codeCharMap;
    private static List<String> textCodes;
    private static List<Character> decodedText;

    public static void sortCodesIntoCanon() {
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
            numChars = bis.read();

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
                sb.append(Integer.toBinaryString(currByte));
            }

            String fullStr = sb.toString();

            System.out.println(fullStr);

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
        String inFileName  = "encoded/sample2.huf";
        String outFileName = "dingo.huf";
        readInputFile(inFileName);



        writeToOutput(outFileName);
    }
}
