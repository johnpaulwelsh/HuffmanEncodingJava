package huffman;

/**
 * Class to represent a Huffman binary code.
 */
public class Code {
    protected String code;

    public Code(String code) {
        this.code = code;
    }

    public void setCode(String newCode) {
        this.code = newCode;
    }

    public String getCode() {
        return this.code;
    }
}
