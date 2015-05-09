package huffman;

import java.util.*;

/**
 * Class to perform Huffman encoding onto an input text.
 *
 * @author John Paul Welsh
 */
public class HuffEncoder {

    private List<Character> origInputChars;
    private Set<Character> inputCharsSet;
    private Map<Character, Integer> frequencies;
    private List<CharCodePair> huffPairs;
    public static Map<Character, String> canonCodes;
    private HuffmanTree tree;

    /**
     * Constructor for HuffEncoder
     * @param ls the list of characters from the original input
     */
    public HuffEncoder(List<Character> ls) {
        this.origInputChars = ls;
        this.frequencies    = new TreeMap<Character, Integer>();
        this.huffPairs      = new ArrayList<CharCodePair>();
        this.inputCharsSet  = new TreeSet<Character>();
        this.canonCodes     = new TreeMap<Character, String>();
    }

    /**
     * The primary functionality of the encoding process. All the
     * high-level function calls are here.
     */
    public PackageToEncode encode() {
        origInputChars.add('\u0000');
        countFrequencies();
        fillSet();
        tree = new HuffmanTree();
        tree.setRoot(buildHuffTree());
        makeHuffCodes(tree.getRoot(), "");
        canonizeHuffCodes();
        return buildEntireOutput();
    }

    /**
     * Counts the frequencies of each character in the original list.
     */
    private void countFrequencies() {
        for (char c : origInputChars) {
            if (frequencies.containsKey(c))
                frequencies.put(c, frequencies.get(c) + 1);
            else
                frequencies.put(c, 1);
        }
    }

    /**
     * Fills a set with the original input characters, thereby removing
     * duplicates so when we make HuffmanTree nodes for each character,
     * we do it once for each.
     */
    private void fillSet() {
        for (char c : origInputChars) {
            inputCharsSet.add(c);
        }
    }

    /**
     * Builds a Huffman Tree, following the algorithm from the book.
     */
    private BinaryNode buildHuffTree() {
        tree.makeNodesForEachChar(inputCharsSet, frequencies);
        Queue<BinaryNode> queue = tree.getNodeQueue();

        while (queue.size() >= 2) {
            BinaryNode left = queue.poll();
            BinaryNode right = queue.poll();
            BinaryNode combined = new BinaryNode('0', left.frequency + right.frequency, left, right);
            queue.offer(combined);
        }

        // The only node left is the root
        return queue.poll();
    }

    /**
     * Traverses the Huffman Tree to build the codewords for each
     * character in the tree.
     */
    private void makeHuffCodes(BinaryNode current, String code) {
        // When we get to a leaf, we add the running code to the map
        if (current.left == null && current.right == null) {
            huffPairs.add(new CharCodePair(current.character, code));
        // Otherwise, we recurse using the left and right children,
        // and build the code accordingly
        } else {
            makeHuffCodes(current.left, code + "0");
            makeHuffCodes(current.right, code + "1");
        }
    }

    /**
     * Transforms the Huffman codewords into canonical Huffman codes.
     */
    private void canonizeHuffCodes() {
        sortCodesByLength();
        sortCodesByLex();
        redoCodesAndStore();
    }

    /**
     * Sorts the pairs of character and Huffman code according to the length
     * of the codeword, from longest to shortest. This is a stable sort.
     */
    private void sortCodesByLength() {
        Collections.sort(huffPairs, new Comparator<CharCodePair>() {
            public int compare(CharCodePair c1, CharCodePair c2) {
                return c2.code.length() - c1.code.length();
            }
        });
    }

    /**
     * Sorts the pairs of character and Huffman code according to the
     * lexographical ordering of the characters, from lowest to highest.
     * This is a stable sort.
     */
    private void sortCodesByLex() {
        Collections.sort(huffPairs, new Comparator<CharCodePair>() {
            public int compare(CharCodePair c1, CharCodePair c2) {
                return (c1.code.length() == c2.code.length())
                        ? c1.character - c2.character
                        : 0;
            }
        });
    }

    /**
     * Recodes the codewords for each pair of character and Huffman code,
     * according to the canonization process, and stores the final pairing
     * in a Map.
     */
    private void redoCodesAndStore() {
        // Do all the stuff for the first element. For however long its code is,
        // make its new code all zeroes for the same length.
        CharCodePair ccp = huffPairs.get(0);
        int len = ccp.code.length();
        String canonFirstCode = "";
        for (int i = 0; i < len; i++) {
            canonFirstCode += "0";
        }
        canonCodes.put(ccp.character, canonFirstCode);

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
        for (int j = 1; j < huffPairs.size(); j++) {
            ccp = huffPairs.get(j);
            currLen = ccp.code.length();

            runningBinaryCode += 1;

            if (oldLen > currLen) {
                runningBinaryCode >>>= (oldLen - currLen);
            }

            String canonCodeStr = Integer.toBinaryString(runningBinaryCode);

            int goalLength = canonCodeStr.length();
            if (currLen != goalLength) {
                for (int i = 0; i < currLen - goalLength; i++) {
                    canonCodeStr = "0" + canonCodeStr;
                }
            }

            canonCodes.put(ccp.character, canonCodeStr);

            oldLen = currLen;
        }
    }

    /**
     * Builds the text that will be outputted to the file. The file writer
     * will be in charge of translating it into bytes.
     *
     * @return the final String to be "written" to the file
     */
    public PackageToEncode buildEntireOutput() {

        // The number of character-code pairings (k)
        int k = huffPairs.size();

        // The k pairings of character and canonical Huffman code
        char[] chs = new char[k];
        int[] codeLens = new int[k];
        for (int i = 0; i < k; i++) {
            CharCodePair ccp = huffPairs.get(i);
            chs[i] = ccp.character;
            codeLens[i] = canonCodes.get(ccp.character).length();
        }

        String[] textCodes = new String[origInputChars.size()];

        // The encoded test from the input file
        for (int j = 0; j < textCodes.length; j++) {
            textCodes[j] = canonCodes.get(origInputChars.get(j));
        }

        return new PackageToEncode(k, chs, codeLens, textCodes);
    }
}
