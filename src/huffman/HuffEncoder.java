package huffman;

import java.util.*;

/**
 * Class to perform Huffman encoding onto an input text.
 *
 * @author John Paul Welsh
 */
public class HuffEncoder {

    private List<Character> origInputChars;
    private Map<Character, Integer> frequencies;
    private Map<Character, Integer> huffCodes;
    private Map<Character, Integer> canonCodes;
    private HuffmanTree tree;

    public HuffEncoder(List<Character> ls) {
        this.origInputChars = ls;
    }

    public void encode() {
        countFrequencies();
        tree = new HuffmanTree();
        tree.insert(buildHuffTree());
        makeHuffCodes();
        canonizeHuffCodes();
    }

    /**
     * Counts the frequencies of each character in the original list.
     * Stores them in a TreeMap for efficient lookup, and because
     * red-black trees are amazing.
     */
    private void countFrequencies() {
        frequencies = new TreeMap<Character, Integer>();
        for (char c : origInputChars) {
            if (frequencies.containsKey(c))
                frequencies.put(c, frequencies.get(c) + 1);
            else
                frequencies.put(c, 1);
        }
    }

    /**
     * Builds a Huffman Tree, following the algorithm from the book.
     */
    private BinaryNode buildHuffTree() {
        tree.makeNodesForEachChar(origInputChars, frequencies);
        List<BinaryNode> queue = tree.getNodeQueue();
        Collections.sort(queue);

        while (queue.size() > 2) {
            BinaryNode x = queue.remove(0);
            BinaryNode y = queue.remove(1);
            BinaryNode z = new BinaryNode('0', x.frequency + y.frequency, x, y);
            queue.add(z);
            Collections.sort(queue);
        }

        return queue.get(0);
    }

    /**
     * Traverses the Huffman Tree to get the codewords for each
     * character in the tree.
     */
    private void makeHuffCodes() {
        huffCodes = new TreeMap<Character, Integer>();
        for (BinaryNode leaf : tree.getNodeQueue()) {
            huffCodes.put(leaf.character, tree.buildCodeForLeaf(leaf));
        }
    }

    /**
     * Transforms the Huffman codewords into canonical Huffman codes.
     */
    private void canonizeHuffCodes() {
        sortCodesByLength(canonCodes);
        sortCodesByLex(canonCodes);
    }

    private void sortCodesByLength(Map<Character, Integer> codes) {

    }

    private void sortCodesByLex(Map<Character, Integer> codes) {

    }
}