package huffman;

/**
 * Class to represent a Huffman Binary Tree Node.
 *
 * @author John Paul Welsh
 */
public class BinaryNode implements Comparable<BinaryNode> {
    char character;
    int frequency;
    BinaryNode left;
    BinaryNode right;

    /**
     * Constuctor for BinaryNode, given the character, frequency,
     * and one or both of the child nodes.
     * @param character the char (or group of chars) that this
     *                  node represents
     * @param frequency the frequency with which this char shows
     *                  up in the input
     * @param left      the left child node
     * @param right     the right child node
     */
    public BinaryNode(char character,
                      int frequency,
                      BinaryNode left,
                      BinaryNode right) {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    /**
     * Blank constructor for BinaryNode. Given the character and
     * frequency, it makes a new node with no children.
     *
     * @param character the char (or group of chars) that this
     *                  node represents
     * @param frequency the frequency with which this char shows
     *                  up in the input
     */
    public BinaryNode(char character, int frequency) {
        this(character, frequency, null, null);
    }

    /**
     * Compares the current BinaryNode to another given BinaryNode,
     * based on the frequency of the node.
     *
     * @param that the node being compared to
     * @return     -1 if this is less than that,
     *              1 if this is greater than that,
     *              0 if they are equal
     */
    @Override
    public int compareTo(BinaryNode that) {
        int us   = this.frequency;
        int them = that.frequency;
        if (us < them) {
            return -1;
        } else if (us > them) {
            return 1;
        } else {
            return 0;
        }
    }
}
