package tsi.lexical.analizator;

import tsi.lexical.analizator.domain.ILexeme;
import tsi.lexical.analizator.domain.Lexeme;
import tsi.lexical.analizator.domain.Text;
import tsi.lexical.analizator.parser.Parser;
import tsi.lexical.analizator.utils.FileUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by artyom on 15.3.12.
 */
public class App {

    private static final String INPUT_FILE_NAME = "in.txt";
    private static final String KEYWORDS_FILE_NAME = "keywords.txt";
    private static final String SPECIALS_FILE_NAME = "specials.txt";
    private static final String SPECIALS_DOUBLE_FILE_NAME = "specials_double.txt";
    private static final String OUTPUT_FILE_NAME = "out.txt";

    public static void main(String[] args) {

        Map<String, Boolean> keywords = null;
        try {
            keywords = FileUtils.configFileReader(KEYWORDS_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Map<String, Boolean> specials = null;
        try {
            specials = FileUtils.configFileReader(SPECIALS_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Map<String, Boolean> specialsDouble = null;
        try {
            specialsDouble = FileUtils.configFileReader(SPECIALS_DOUBLE_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Text text = null;
        try {
            text = FileUtils.fileReader(INPUT_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        List<ILexeme> lexemes = Parser.textRunner(text, keywords, specials, specialsDouble);

        try {
            FileUtils.resultWriter(lexemes, OUTPUT_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
