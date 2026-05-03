package ru.yandex.practicum;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public WordleDictionary load(String fileName) throws IOException {
        List<String> words = new ArrayList<>();
        try (FileReader reader = new FileReader(fileName, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(reader)) {
            while (br.ready()) {
                String word = br.readLine();
                if (word.length() == WordleDictionary.WORD_LENGTH) {
                    words.add(WordleDictionary.normalize(word));
                }
            }
            log.println("В словарь загружено " + words.size() + " слов");
        } catch (IOException e) {
            log.println("Ошибка при загрузке слов из файла");
            e.printStackTrace(log);
            throw e;
        }
        return new WordleDictionary(words, log);
    }

}
