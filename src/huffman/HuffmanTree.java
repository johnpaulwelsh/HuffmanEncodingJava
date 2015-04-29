package huffman;

/**
 * Class to represent a Huffman Binary Tree.
 *
 * @author John Paul Welsh
 */
public class HuffmanTree {

    /**
     * Class to represent a Huffman Binary Tree Node.
     */
    private class BinaryNode implements Comparable<BinaryNode> {
        char character;
        String code;
        BinaryNode left;
        BinaryNode right;

        public BinaryNode(char character, String code, BinaryNode left, BinaryNode right) {
            this.character = character;
            this.code = code;
            this.left = left;
            this.right = right;
        }

        public BinaryNode(char character, String code) {
            this(character, code, null, null);
        }

        @Override
        public int compareTo(BinaryNode otherChar) {
            char us = this.character;
            char them = otherChar.character;
            if (us > them) {
                return -1;
            } else if (us < them) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private BinaryNode root;
    private BinaryNode left;
    private BinaryNode right;

    public HuffmanTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return (this.root == null);
    }

    public void clear() {
        this.root = null;
    }

    public void insert(BinaryNode node) {

    }

    public BinaryNode remove(BinaryNode node) {

        return null;
    }

    public char getCharacterAt(BinaryNode node) {
        return (node == null) ? 0x00 : node.character;
    }

    public String getCodeAt(BinaryNode node) {
        return (node == null) ? null : node.code;
    }
}
