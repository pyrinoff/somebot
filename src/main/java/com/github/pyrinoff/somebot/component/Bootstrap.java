package com.github.pyrinoff.somebot.component;

import com.github.pyrinoff.somebot.abstraction.AbstractBot;
import com.github.pyrinoff.somebot.service.bot.tg.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
//@DependsOn("PropertyService")
public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired AbstractBot[] bots;

    public void start() {
        for (AbstractBot oneBot : bots) {
            try {
                oneBot.initialize();
            }
            catch (Exception e) {
                System.out.println("Cant initialize bot: " + oneBot.getClass().getName());
                e.printStackTrace();
                logger.error(Arrays.toString(e.getStackTrace()));
            }
        }
    }

}
