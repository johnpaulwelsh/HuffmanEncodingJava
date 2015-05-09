package huffman;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Class to represent a Huffman Binary Tree.
 *
 * @author John Paul Welsh
 */
public class HuffmanTree {

    /*
     * Private variables that the HuffmanTree uses.
     */
    private BinaryNode root;
    private Queue<BinaryNode> nodeQueue;
    private Queue<BinaryNode> leafQueueCopy;

    /**
     * Constructor for HuffmanTree.
     */
    public HuffmanTree() {
        this.root          = null;
        this.nodeQueue     = new PriorityQueue<BinaryNode>();
        this.leafQueueCopy = new PriorityQueue<BinaryNode>();
    }

    /**
     * Makes a new BinaryNode for each character in the input. These are not
     * yet in tree structure, but are stored in a List, from which the nodes
     * will be grabbed when we create the tree.
     *
     * @param inputChars  the List of input characters
     * @param frequencies the mapping from each char to its frequency
     *                    in the input file
     */
    public void makeNodesForEachChar(Set<Character> inputChars,
                                     Map<Character, Integer> frequencies) {
        for (char ch : inputChars) {
            int freq = frequencies.get(ch);
            nodeQueue.offer(new BinaryNode(ch, freq));
            leafQueueCopy.offer(new BinaryNode(ch, freq));
        }
    }

    public Queue<BinaryNode> getNodeQueue() {
        return nodeQueue;
    }

    public Queue<BinaryNode> getLeafQueueCopy() {
        return leafQueueCopy;
    }

    /**
     * Sets the root of the tree to be the given node.
     *
     * @param node the node being set as the root
     */
    public void setRoot(BinaryNode node) {
        this.root = node;
    }

    /**
     * Gets the root of the tree.
     *
     * @return the tree root
     */
    public BinaryNode getRoot() {
        return this.root;
    }

    /**
     * Gets the character out of the given BinaryNode.
     *
     * @param node the node in question
     * @return     the character in that node
     */
    public char getCharacterAt(BinaryNode node) {
        return (node == null) ? 0x00 : node.character;
    }

    /**
     * Gets the frequency out of the given BinaryNode.
     *
     * @param node the node in question
     * @return     the frequency of the character in that node
     */
    public int getFrequencyAt(BinaryNode node) {
        return (node == null) ? 0 : node.frequency;
    }

    /*
     * Boolean methods for whether an element has a child or children
     */
    public boolean hasChildren(BinaryNode node) {
        return hasLeftChild(node) || hasRightChild(node);
    }

    public boolean hasLeftChild(BinaryNode node) {
        return (node.left != null);
    }

    public boolean hasRightChild(BinaryNode node) {
        return (node.right != null);
    }
}
