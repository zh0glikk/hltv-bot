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
    public static final String events = "Events";

    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add(topTeams + "\uD83C\uDF96");
        row.add(todayMatches + "\uD83D\uDCFA");
        keyboard.add(row);

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add(results + "\uD83D\uDCBE");
        secondRow.add(events + "\uD83C\uDFC6");

        keyboard.add(secondRow);

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }
}
