package huffman;

import java.util.*;

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
        this.root = null;
        this.nodeQueue = new PriorityQueue<BinaryNode>();
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
     * Inserts a BinaryNode into the HuffmanTree.
     *
     * @param node the new node being inserted
     */
    public void insert(BinaryNode node) {
        insertAux(root, node);
    }

    private void insertAux(BinaryNode current, BinaryNode node) {
        if (current == null) {
            current = node;
        } else if (node.compareTo(current) < 0) {
            insertAux(current.left, node);
        } else {
            insertAux(current.right, node);
        }
    }

    public BinaryNode findNode(BinaryNode current, BinaryNode node) {
        if (current.compareTo(node) == 0) {
            return current;
        } else if (node.compareTo(current) < 0) {
            return findNode(current.left, node);
        } else {
            return findNode(current.right, node);
        }
    }

    /**
     * Traverses the HuffmanTree looking for the given leaf, and
     * accumulating the binary code as it goes.
     *
     * @param leaf the leaf node being searched for
     * @return     the binary Huffman code
     */
    public int buildCodeForLeaf(BinaryNode leaf) {
//        return buildAux(root, leaf, 0);
        BinaryNode curr = root;
        String accum = "";
        while (curr != null && leaf.character != curr.character) {
            if (leaf.compareTo(curr) < 0) {
                curr = curr.left;
                accum = accum + "0";
            } else {
                curr = curr.right;
                accum = accum + "1";
            }
        }
        System.out.println(accum);
        return Integer.parseInt(accum, 2);
    }

//    public int buildAux(BinaryNode current, BinaryNode leaf, int code) {
//        if (leaf.compareTo(current) == 0) {
//            return code;
//        } else if (leaf.compareTo(current) < 0) {
//            return buildAux(current.left, leaf, code + 1);
//        } else {
//            return buildAux(current.right, leaf, code + 10);
//        }
//    }

    public void setRoot(BinaryNode node) {
        this.root = node;
    }

    /**
     * Gets the character out of the given BinaryNode.
     * @param node the node in question
     * @return     the character in that node
     */
    public char getCharacterAt(BinaryNode node) {
        return (node == null) ? 0x00 : node.character;
    }

    /**
     * Gets the frequency out of the given BinaryNode.
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
