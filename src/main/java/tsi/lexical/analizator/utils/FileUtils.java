package tsi.lexical.analizator.utils;

import tsi.lexical.analizator.domain.Lexeme;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by artyom on 15.3.12.
 */
public class FileUtils {

    public static Map<String, Boolean> configFileReader(String fileName) {

        Map<String, Boolean> paramMap = new HashMap<>();

        String basePath = new File("").getAbsolutePath();
        System.out.println("Base directory: " + basePath);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String current;
            while ((current = bufferedReader.readLine()) != null) {
                paramMap.put(current, true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("File: " + fileName + ": OK");
        return paramMap;
    }

    public static String fileReader(String fileName) {

        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            System.out.println("File: " + fileName + "\n");

            int value = 0;
            while ((value = bufferedReader.read()) != -1) {
                stringBuilder.append((char)value);
                System.out.print((char) value);
            }

            System.out.println("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static void resultWriter(List<Lexeme> lexemes, String fileName) {

        for (Lexeme lexeme : lexemes) {
            System.out.println(lexeme.toString());
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {

            for (Lexeme lexeme : lexemes) {

                bufferedWriter.write(lexeme.toString() + "\n");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
