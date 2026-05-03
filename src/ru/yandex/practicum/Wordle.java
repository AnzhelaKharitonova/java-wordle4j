package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    public static final String fileName = "words_ru.txt";

    public static void main(String[] args) {
        try {
            Writer writer = new FileWriter("log.txt");
            PrintWriter log = new PrintWriter(writer, true);
            try {
                WordleDictionary dictionary = new WordleDictionaryLoader(log).load(fileName);
                WordleGame game = new WordleGame(dictionary, log);

                game.reset();
                play(game, log);
            } catch (Exception e) {
                e.printStackTrace(log);
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void play(WordleGame game, PrintWriter log)   {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Вам нужно угадать слово из пяти букв \n у вас есть 6 попыток \n Enter - дать подсказку.");

        while (game.isGameOngoing()) {
            try {
                log.println("Ждем ввода пользователя");
                String input = scanner.nextLine();
                if (input.isBlank()) {
                    log.println("Пользователь запросил подсказку");
                    input = game.giveHint();
                    log.println("Компьютер выдал подсказку - " + input);
                    System.out.println(input);
                } else {
                    input = WordleDictionary.normalize(input);
                    game.validate(input);
                    log.println("Пользователь ввел слово " + input);
                }
                game.makeMove(input);
                System.out.println(game.getResume());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace(log);
            }
        }
        System.out.println("Загаданное слово - " + game.getAnswer());
        log.println("Игра окончена");

    }

}

