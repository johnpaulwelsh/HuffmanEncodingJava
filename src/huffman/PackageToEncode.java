package huffman;

/**
 * Class to package up all the different pieces that need to be
 * encoded into a text file.
 */
public class PackageToEncode {
    protected int k;
    protected char[] chs;
    protected int[] codeLens;
    protected String[] textCodes;

    public PackageToEncode(int k, char[] chs, int[] codeLens, String[] textCodes) {
        this.k = k;
        this.chs = chs;
        this.codeLens = codeLens;
        this.textCodes = textCodes;
    }
}
