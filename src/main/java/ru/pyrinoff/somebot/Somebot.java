package ru.pyrinoff.somebot;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pyrinoff.somebot.component.Bootstrap;

public class Somebot {

    String commandPackage;

    public static String databasePackages;

    public Somebot() {
    }

    public Somebot(final String commandPackage) {
        this.commandPackage = commandPackage;
        databasePackages = commandPackage;
    }

    public void start() {
        @NotNull final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.pyrinoff.somebot");
        if (commandPackage != null) context.scan(commandPackage);
        context.refresh();
        Bootstrap bootstrap = context.getBean(Bootstrap.class);
        bootstrap.start();
    }

}
