package tsi.lexical.analizator.domain;

/**
 * Created by artyom on 15.3.12.
 */
public class Lexeme implements ILexeme {

    private String name;
    private String value;

    public Lexeme(String name, String value) {
        this.name = name;
        this.value = value;
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

    @Override
    public String toString() {
        return name + ": " + value;
    }

    @Override
    public void display() {
        System.out.println(toString());
    }
}

