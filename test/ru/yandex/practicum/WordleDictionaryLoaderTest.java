package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoaderTest {
    PrintWriter log = new PrintWriter(System.out, true);

    @Test
    void shouldLoad5WordsAndNormaliseWords() throws IOException {
        WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
        String fileName = "test.txt";
        WordleDictionary dictionary = loader.load(fileName);
        List<String> expected = new ArrayList<>(List.of("актер","арбуз", "лодка", "ребус", "факел"));
        Assertions.assertEquals(expected, dictionary.getWords());
    }
}
