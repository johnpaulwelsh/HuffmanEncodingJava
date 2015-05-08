package huffman;

/**
 * Class to lump a character and its Huffman code together,
 * so it's easier to sort, and later canonize, the codes.
 *
 * @author John Paul Welsh
 */
public class CharCodePair {
    protected String code;
    protected char character;

    public CharCodePair(char ch, String code) {
        this.character = ch;
        this.code      = code;
    }
}
