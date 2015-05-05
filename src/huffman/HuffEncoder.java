package huffman;

import java.util.*;

/**
 * Class to perform Huffman encoding onto an input text.
 *
 * @author John Paul Welsh
 */
public class HuffEncoder {

    private List<Character>         origInputChars;
    private Set<Character>          inputCharsSet;
    private Map<Character, Integer> frequencies;
    private List<CharCodePair>      huffPairs;
    private Map<Character, String>  canonCodes;
    private HuffmanTree             tree;

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
    public void encode() {
        countFrequencies();
        fillSet();
        tree = new HuffmanTree();
        tree.setRoot(buildHuffTree());
        makeHuffCodes(tree.getRoot(), "");
        canonizeHuffCodes();
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
     * of the codeword. This is a stable sort.
     */
    private void sortCodesByLength() {
        Collections.sort(huffPairs, new Comparator<CharCodePair>() {
            public int compare(CharCodePair c1, CharCodePair c2) {
                return c1.code.length() - c2.code.length();
            }
        });
    }

    /**
     * Sorts the pairs of character and Huffman code according to the
     * lexographical ordering of the characters. This is a stable sort.
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

    }
}
