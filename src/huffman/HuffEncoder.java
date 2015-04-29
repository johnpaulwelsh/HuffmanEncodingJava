package huffman;

import java.util.List;
import java.util.TreeMap;

/**
 * Class to perform Huffman encoding onto an input text.
 *
 * @author John Paul Welsh
 */
public class HuffEncoder {

    private HuffmanTree tree;
    private List<Character> origInputChars;
    private TreeMap<Character, Integer> frequencies;

    public HuffEncoder(List<Character> ls) {
        this.origInputChars = ls;
        countFrequencies();
    }

    private void countFrequencies() {
        frequencies = new TreeMap<Character, Integer>();
        for (char c : origInputChars) {
            if (frequencies.containsKey(c)) {
                frequencies.put(c, frequencies.get(c) + 1);
            } else {
                frequencies.put(c, 1);
            }

        }
        System.out.println(frequencies.size());
        System.out.println(frequencies.get('l'));
    }
}
