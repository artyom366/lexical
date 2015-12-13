package tsi.lexical.analizator.domain;

/**
 * Created by Artyom on 12/13/2015.
 */
public class Error implements ILexeme {

    private String name;
    private String value;
    private int charCode;
    private String errorFragment;
    private String errorLocation;

    public Error(String name, String value, int charCode, String errorFragment, String errorLocation) {
        this.name = name;
        this.value = value;
        this.charCode = charCode;
        this.errorFragment = errorFragment;
        this.errorLocation = errorLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCharCode() {
        return charCode;
    }

    public void setCharCode(int charCode) {
        this.charCode = charCode;
    }

    public String getErrorFragment() {
        return errorFragment;
    }

    public void setErrorFragment(String errorFragment) {
        this.errorFragment = errorFragment;
    }

    public String getErrorLocation() {
        return errorLocation;
    }

    public void setErrorLocation(String errorLocation) {
        this.errorLocation = errorLocation;
    }

    @Override
    public String toString() {
        return name + ": " + value + " char_code: " + charCode;
    }

    @Override
    public void display() {
        System.out.println();
        System.out.println(toString());;
        System.out.println(errorFragment);
        System.out.println(errorLocation);
        System.out.println();
    }
}
