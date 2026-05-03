package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryTest {
    PrintWriter log = new PrintWriter(System.out, true);

    @Test
    void shouldNormaliseWords() throws IOException {
        Assertions.assertEquals("свекла", WordleDictionary.normalize("Свёкла"));
    }

}
