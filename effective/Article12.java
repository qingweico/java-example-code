package effective;

/**
 * 始终要覆盖toString
 *
 * @author:qiming
 * @date: 2021/10/27
 */
public class Article12 {

    // It is recommended that all subclass override the method,this is a good suggestion.

    private Integer areaCode = 432;
    private Integer prefix = 777;
    private Integer lineNum = 111;

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getPrefix() {
        return prefix;
    }

    public void setPrefix(Integer prefix) {
        this.prefix = prefix;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * Returns the String representation of this phone number.
     * The String consists of twelve character whose format is
     * "XXX-YYY-ZZZZ", where XXX is the area code, YYY is the prefix,
     * and ZZZZ is the line number. Each of the capital letters represents
     * s single decimal digit.
     * <p>
     * <p>
     * If any of the three parts of this phone number is too small to
     * fill up its field, hte field is padded with leading zero.
     * For example, if the value of the line number is 123, the last
     * four character of the string representation will be "0123".
     *
     * @return String representation of this phone number
     */
    @Override
    public String toString() {
        return String.format("%03d-%03d-%04d", areaCode, prefix, lineNum);
    }
}