package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {
    private static final int MAX_STEPS = 6;
    private final PrintWriter log;
    private final WordleDictionary wholeDictionary;
    private String answer;
    private WordleDictionary dictionaryOfHints;
    private int steps;
    private boolean isGameOngoing;
    private String resume;

    public WordleGame(WordleDictionary wholeDictionary, PrintWriter log) {
        this.wholeDictionary = wholeDictionary;
        this.log = log;
    }

    public void reset() {
        answer = guessTheWord();
        log.println("Компьютер загадал слово - " + answer);
        dictionaryOfHints = new WordleDictionary(new ArrayList<>(wholeDictionary.getWords()), log);
        steps = MAX_STEPS;
        isGameOngoing = true;
        resume = "";
        log.println("Игра загружена");
    }

    public void makeMove(String input) {
        steps--;
        String mask = makeMask(input);
        resume = checkWithTheAnswer(input, mask);
        log.println(resume);

        if (steps == 0) {
            isGameOngoing = false;
        }
        dictionaryOfHints = prepareDictionaryOfHints(mask, input);
        log.println(" в словаре подсказок осталось - " + dictionaryOfHints.getWords().size() + " слов");
    }

    public WordleDictionary prepareDictionaryOfHints(String mask, String input) {
        List<String> words = dictionaryOfHints.getWords();
        words.remove(input);

        for (int i = 0; i < words.size(); ) {
            String word = words.get(i);
            boolean isInvalid = false;
            for (int j = 0; j < WordleDictionary.WORD_LENGTH; j++) {
                if (mask.charAt(j) == '-') {
                    if (word.contains(input.substring(j, j + 1))) {
                        isInvalid = true;
                        break;
                    }
                } else if (mask.charAt(j) == '^') {
                    if (!(word.contains(input.substring(j, j + 1))) || word.charAt(j) == input.charAt(j)) {
                        isInvalid = true;
                        break;
                    }
                } else if (mask.charAt(j) == '+') {
                    if (word.charAt(j) != input.charAt(j)) {
                        isInvalid = true;
                        break;
                    }
                }
            }
            if (isInvalid) {
                words.remove(word);
            } else {
                i++;
            }
        }
        return dictionaryOfHints;
    }


    public String checkWithTheAnswer(String input, String mask) {
        if (input.equals(answer)) {
            isGameOngoing = false;
            resume = "+++++" + "    слово отгадано";
        } else {
            resume = mask + "    количество попыток - " + steps + "/" + MAX_STEPS;
        }
        return resume;
    }

    public String giveHint() {
        return dictionaryOfHints.getRandomWord();
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isGameOngoing() {
        return isGameOngoing;
    }

    public String getResume() {
        return resume;
    }

    public void validate(String input) throws WordNotFoundInDictionary, InputException {
        if (input.length() != WordleDictionary.WORD_LENGTH) {
            throw new InputException("Слово должно состоять из " + WordleDictionary.WORD_LENGTH + " букв!");
        }
        if (!input.matches("^[\\p{IsCyrillic}\\s\\p{Punct}]+$")) {
            throw new InputException("Слово должно состоять только из букв кириллицы!");
        }
        if (!wholeDictionary.getWords().contains(input)) {
            throw new WordNotFoundInDictionary("Такого слова нет в словаре!");
        }
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String makeMask(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == answer.charAt(i)) {
                sb.append("+");
            } else if (answer.contains(input.substring(i, i + 1))) {
                sb.append("^");
            } else {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    private String guessTheWord() {
        return wholeDictionary.getRandomWord();
    }
}
