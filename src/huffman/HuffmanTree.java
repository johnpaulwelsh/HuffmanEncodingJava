package huffman;

/**
 * Class to represent a Huffman Binary Tree.
 *
 * @author John Paul Welsh
 */
public class HuffmanTree {

    private class BinaryNode implements Comparable<BinaryNode> {
        String character;
        String code;
        BinaryNode left;
        BinaryNode right;

        public BinaryNode(String character, String code, BinaryNode left, BinaryNode right) {
            this.character = character;
            this.code = code;
            this.left = left;
            this.right = right;
        }

        public BinaryNode(String character, String code) {
            this(character, code, null, null);
        }

        @Override
        public int compareTo(BinaryNode otherChar) {
            return this.character.compareTo(otherChar.character);
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

    public String getCharacterAt(BinaryNode node) {
        return (node == null) ? "" : node.character;
    }

    public String getCodeAt(BinaryNode node) {
        return (node == null) ? null : node.code;
    }
}
