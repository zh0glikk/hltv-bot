package com.zh0glikk.hltvbot.KeyBoards;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayersKeyboard {

    public InlineKeyboardMarkup getKeyboard(List<String> players, List<String> urls) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for ( int i = 0; i < players.size(); i++ ) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setText(players.get(i));
            btn.setCallbackData("PlayerCallBack:" + urls.get(i));

            rowInline.add(btn);

            rowsInline.add(rowInline);
        }

        inlineKeyboardMarkup.setKeyboard(rowsInline);

        return inlineKeyboardMarkup;
    }
}
