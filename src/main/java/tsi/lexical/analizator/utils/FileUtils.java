package tsi.lexical.analizator.utils;


import tsi.lexical.analizator.domain.ILexeme;
import tsi.lexical.analizator.domain.Lexeme;
import tsi.lexical.analizator.domain.Text;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by artyom on 15.3.12.
 */
public class FileUtils {

    public static Map<String, Boolean> configFileReader(String fileName) throws IOException {

        Map<String, Boolean> paramMap = new HashMap<>();

        String basePath = new File("").getAbsolutePath();
        System.out.println("Base directory: " + basePath);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String current;
            while ((current = bufferedReader.readLine()) != null) {
                paramMap.put(current, true);
            }
        }

        System.out.println("File: " + fileName + ": OK");
        return paramMap;
    }

    public static Text fileReader(String fileName) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        Map<Integer, String> lines = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            System.out.println("File: " + fileName + "\n");

//            int value = 0;
//            while ((value = bufferedReader.read()) != -1) {
//                stringBuilder.append((char)value);
//                System.out.print((char) value);
//            }

            String line;
            int position = 1;
            while((line = bufferedReader.readLine()) != null) {

                stringBuilder.append(line);
                stringBuilder.append((char)10);

                lines.put(position, line);


                System.out.println(position + "  " + line);

                position++;
            }

            System.out.println("\n");
        }

        Text text = new Text(stringBuilder.toString(), lines);

        return text;
    }

    public static void resultWriter(List<ILexeme> lexemes, String fileName) throws IOException {

        for (ILexeme lexeme : lexemes) {

            lexeme.display();
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {

            for (ILexeme lexeme : lexemes) {

                bufferedWriter.write(lexeme.toString() + "\n");
            }
        }
    }
}
