package tsi.lexical.analizator;

import tsi.lexical.analizator.domain.Lexeme;
import tsi.lexical.analizator.parser.Parser;
import tsi.lexical.analizator.utils.FileUtils;

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

        Map<String, Boolean> keywords = FileUtils.configFileReader(KEYWORDS_FILE_NAME);
        Map<String, Boolean> specials = FileUtils.configFileReader(SPECIALS_FILE_NAME);
        Map<String, Boolean> specialsDouble = FileUtils.configFileReader(SPECIALS_DOUBLE_FILE_NAME);

        String text = FileUtils.fileReader(INPUT_FILE_NAME);

        List<Lexeme> lexemes = Parser.textRunner(text, keywords, specials, specialsDouble);

        FileUtils.resultWriter(lexemes, OUTPUT_FILE_NAME);
    }
}
