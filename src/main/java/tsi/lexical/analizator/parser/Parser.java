package tsi.lexical.analizator.parser;

import tsi.lexical.analizator.domain.*;
import tsi.lexical.analizator.domain.Error;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by artyom on 15.3.12.
 */
public class Parser {

    private static boolean isNum; //numeric const trigger
    private static boolean isStr; //string const trigger
    private static boolean isValid; //does text contains invalid characters

    public static List<ILexeme> textRunner(Text text,
                                          Map<String, Boolean> keywords,
                                          Map<String, Boolean> specials,
                                          Map<String, Boolean> specialsDouble) {

        StringBuilder stringBuilderVariable = new StringBuilder();
        StringBuilder stringBuilderSpecial = new StringBuilder();
        List<ILexeme> lexemes = new ArrayList<>();
        List<ILexeme> errors = new ArrayList<>();

        isValid = true; //set the trigger, assume that the text is valid

        for (int line = 1, localPosition = 0, i = 0; i < text.getText().length(); i++, localPosition++) {

            if (!isStr) {

                char c = text.getText().charAt(i);

                if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) { // A-Z a-z

                    isNum = false; //reset trigger, characters detected

                    specialHelper(stringBuilderSpecial, specialsDouble, lexemes);
                    stringBuilderVariable.append(c);

                } else if (c >= 48 && c <= 57) { // 0-9

                    specialHelper(stringBuilderSpecial, specialsDouble, lexemes);
                    stringBuilderVariable.append(c);

                } else if (specials.get(String.valueOf((int) c)) != null) { // special

                    if (c == 39) {
                        isStr = true; //set trigger, single quote detected
                    }

                    variableHelper(keywords, stringBuilderVariable, lexemes);
                    stringBuilderSpecial.append(c);

                    //do not allow stringBuilderSpecial to grow more than 2 chars
                    if (stringBuilderSpecial.length() == 2) {

                        specialHelper(stringBuilderSpecial, specialsDouble, lexemes);
                    }

                } else {

                    //unless it is not a carriage return
                    if (c != 10) {
                        System.out.println("Invalid character code: " + (int) c + ", value: " + c);

                        errorHelper(errors, text, i, line, localPosition);

                        isValid = false;

                    } else {

                        //carriage return is a special symbol, handle variable or keyword
                        variableHelper(keywords, stringBuilderVariable, lexemes);

                        //line counter
                        line ++;

                        //reset line local position counter
                        localPosition = 0;
                    }
                }

            } else {

                //handle single quote
                specialHelper(stringBuilderSpecial, specialsDouble, lexemes);

                int j = i;
                char c;

                while (true) {

                    c = text.getText().charAt(j);

                    if (c == 39) {

                        //another single quote is found, end of constant string
                        variableHelper(keywords, stringBuilderVariable, lexemes);
                        stringBuilderSpecial.append(c);
                        specialHelper(stringBuilderSpecial, specialsDouble, lexemes);

                        //proceed main loop from the end of the quote
                        i = j;

                        //reset trigger
                        isStr = false;

                        break;

                    } else {

                        stringBuilderVariable.append(c);
                        j++;
                    }
                }
            }
        }

        return isValid ? lexemes : errors;
    }

    private static void specialHelper(StringBuilder stringBuilderSpecial, Map<String, Boolean> specialsDouble, List<ILexeme> lexemes) {

        //special single
        if (stringBuilderSpecial.length() == 1) {

            Lexeme special = null;

            if (stringBuilderSpecial.toString().charAt(0) == 32) special = new Lexeme("spe", "_space");
            else if (stringBuilderSpecial.toString().charAt(0) == 10) {
                special = new Lexeme("spe", "_line");
            }
            else special = new Lexeme("spe", stringBuilderSpecial.toString());
            lexemes.add(special);
            stringBuilderSpecial.setLength(0);

            //special double
        } else if (stringBuilderSpecial.length() == 2) {

            if (specialsDouble.get(stringBuilderSpecial.toString()) != null) {

                Lexeme specialDouble = new Lexeme("sdo", stringBuilderSpecial.toString());
                lexemes.add(specialDouble);
                stringBuilderSpecial.setLength(0);

            } else {

                //coincidental double
                for (int j = 0; j < stringBuilderSpecial.length(); j ++) {

                    char c = stringBuilderSpecial.charAt(j);

                    Lexeme special = null;

                    if (c == 32) special = new Lexeme("spe", "_space");
                    else special = new Lexeme("spe", c + "");
                    lexemes.add(special);
                }

                stringBuilderSpecial.setLength(0);
            }

        } else if (stringBuilderSpecial.length() == 3) {
            System.out.println(stringBuilderSpecial);

        }
    }

    private static void variableHelper(Map<String, Boolean> keywords, StringBuilder stringBuilderVariable, List<ILexeme> lexemes) {

        //keyword
        if (keywords.get(stringBuilderVariable.toString().toLowerCase()) != null) {

            Lexeme keyword = new Lexeme("key", stringBuilderVariable.toString());
            lexemes.add(keyword);
            stringBuilderVariable.setLength(0);

            //variable
        } else if (stringBuilderVariable.length() > 0) {

            String key;

            if (isStr) {
                key = "str";

            } else if (isNum) {
                key = "num";

            } else {
                key = "var";
            }

            Lexeme variable = new Lexeme(key, stringBuilderVariable.toString());
            lexemes.add(variable);
            stringBuilderVariable.setLength(0);

            isNum = true; //set trigger
            isStr = false; //reset trigger
        }
    }

    private static void errorHelper(List<ILexeme> errors, Text text, int position, int line, int localPosition) {

        String errorFragment = "error at: " + text.getLines().get(line);
        String errorPosition = "unrecognized char at: line " + line + ", column " + localPosition;

        Error error = new Error("err", text.getText().charAt(position) + "", (int)text.getText().charAt(position), errorFragment, errorPosition);
        errors.add(error);
    }
}