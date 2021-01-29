package com.zh0glikk.hltvbot.KeyBoards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class HomeKeyboard {
    public static final String topTeams = "Top teams";
    public static final String todayMatches = "Today matches";
    public static final String results = "Results";

    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add(topTeams);
        row.add(todayMatches);
        row.add(results);

        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }
}
