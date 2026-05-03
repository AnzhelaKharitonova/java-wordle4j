package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    public static final int WORD_LENGTH = 5;
    private PrintWriter log;
    private List<String> words;

    public WordleDictionary(List<String> words, PrintWriter log) {
        this.words = words;
        this.log = log;
    }

    public List<String> getWords() {
        return words;
    }

    public String getRandomWord () {
        int random = new Random().nextInt(words.size());
        return words.get(random);
    }

    public static String normalize(String word) {
        return word.toLowerCase().replace("ё", "е");
    }
}

