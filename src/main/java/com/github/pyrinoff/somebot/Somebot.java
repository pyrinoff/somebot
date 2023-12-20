package com.github.pyrinoff.somebot;

import com.github.pyrinoff.somebot.component.Bootstrap;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Somebot {

    private final static String PACKAGE_TO_SCAN = "com.github.pyrinoff.somebot";

    String commandPackage;

    public static String databasePackages;

    public Somebot() {
    }

    public static Somebot byClass(final Class<?> clazz) {
        return new Somebot(clazz.getPackage().getName());
    }

    public static Somebot byString(final String commandPackage) {
        return new Somebot(commandPackage);
    }

    private Somebot(final String commandPackage) {
        this.commandPackage = commandPackage;
        databasePackages = commandPackage;
    }

    public void start() {
        @NotNull final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(PACKAGE_TO_SCAN);
        if (commandPackage != null) context.scan(commandPackage);
        context.refresh();
        Bootstrap bootstrap = context.getBean(Bootstrap.class);
        bootstrap.start();
    }

}
