package huffman;

/**
 * Class to lump a character and its Huffman code together,
 * so it's easier to sort, and later canonize, the codes.
 *
 * @author John Paul Welsh
 */
public class CharCodePair {

    protected String code;
    protected char ch;

    public CharCodePair(char ch, String code) {
        this.ch   = ch;
        this.code = code;
    }
}
