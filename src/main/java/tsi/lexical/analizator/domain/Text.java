package tsi.lexical.analizator.domain;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by artyom on 15.15.12.
 */
public class Text {

    private String text;
    private Map<Integer, String> lines;

    public Text(String text, Map<Integer, String> lines) {
        this.text = text;
        this.lines = lines;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<Integer, String> getLines() {
        return lines;
    }

    public void setLines(Map<Integer, String> lines) {
        this.lines = lines;
    }
}
