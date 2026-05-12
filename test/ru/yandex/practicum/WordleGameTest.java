package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {
    PrintWriter log = new PrintWriter(System.out, true);
    List<String> words;
    WordleDictionary dictionary;
    WordleGame game;

    @BeforeEach
    void beforeEach() {
        words = new ArrayList<>(List.of("арбуз", "актер", "булка", "норма", "ручка"));
        dictionary = new WordleDictionary(words, log);
        game = new WordleGame(dictionary, log);
    }

    @Test
    void validateTestShouldThrowInputException() throws InputException, WordNotFoundInDictionary {
        InputException exception = assertThrows(InputException.class, () -> {
            game.validate("привет");
        });
        assertEquals("Слово должно состоять из 5 букв!", exception.getMessage());
    }
    @Test
    void validateTestShouldThrowWordNotFoundInDictionary() throws InputException, WordNotFoundInDictionary {
        WordNotFoundInDictionary exception = assertThrows(WordNotFoundInDictionary.class, () -> {
            game.validate("столб");
        });
        assertEquals("Такого слова нет в словаре!", exception.getMessage());
    }
    @Test
    void checkWithTheAnswerTestReturnFalseIsGameOngoing() {
        game.setAnswer("актер");
        game.checkWithTheAnswer("актер", "+++++");
        assertFalse(game.isGameOngoing());
    }
    @Test
    void prepareDictionaryOfHintsTest() throws IOException, InputException, WordNotFoundInDictionary {
        game.reset();
        List<String> expected = new ArrayList<>(List.of("арбуз"));
        assertEquals(expected, game.prepareDictionaryOfHints("+---^", "актер").getWords());
    }
    @Test
    void checkWithTheAnswerTestReturnCorrectString() throws IOException, InputException, WordNotFoundInDictionary {
        game.reset();
        game.setAnswer("арбуз");
        game.makeMove("булка");
        String expected = "^^--^" + "    количество попыток - " + "5" + "/" + "6";
        assertEquals(expected, game.getResume());
    }

}
