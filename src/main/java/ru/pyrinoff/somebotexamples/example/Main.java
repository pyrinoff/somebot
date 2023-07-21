package ru.pyrinoff.somebotexamples.example;

import ru.pyrinoff.somebot.Somebot;

public class Main {

    public static void main(String[] args) {
        final Somebot somebot = Somebot.byClass(Main.class);
        somebot.start();
    }

}
