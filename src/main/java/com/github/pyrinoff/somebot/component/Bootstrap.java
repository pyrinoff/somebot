package com.github.pyrinoff.somebot.component;

import com.github.pyrinoff.somebot.abstraction.AbstractBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@DependsOn("PropertyService")
public class Bootstrap {

    @Autowired AbstractBot[] bots;

    public void start() {
        for (AbstractBot oneBot : bots) {
            try {
                oneBot.initialize();
            }
            catch (Exception e) {
                System.out.println("Cant initialize bot: " + oneBot.getClass().getName());
            }
        }
    }

}
