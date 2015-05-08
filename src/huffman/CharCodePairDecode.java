package huffman;

/**
 * Class to lump a character and the length of its canonical
 * Huffman code together, so it's easier to sort and figure
 * out the codes during Decode.
 *
 * @author John Paul Welsh
 */
public class CharCodePairDecode {
    protected char character;
    protected int len;
    protected String code = "";

    public CharCodePairDecode(char ch, int len) {
        this.character = ch;
        this.len       = len;
    }

    @Override
    public String toString() {
        return "char = " + this.character + ", code = " + this.code;
    }
}
