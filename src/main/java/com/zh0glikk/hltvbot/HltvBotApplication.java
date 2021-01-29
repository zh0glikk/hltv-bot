package com.zh0glikk.hltvbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class HltvBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HltvBotApplication.class, args);

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(context.getBean(Bot.class));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
