package com.zh0glikk.hltvbot.KeyBoards;

import model.TopTeam;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class TopTeamsKeyboard {

    public InlineKeyboardMarkup getKeyboard(List<TopTeam> teams) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for ( int i = 0; i < 5; i++ ) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            for ( int j = 0; j < 6; j++ ) {
                InlineKeyboardButton btn = new InlineKeyboardButton();
                btn.setText(teams.get(i * 6 + j).getPosition());
                btn.setCallbackData("TeamCallBack:" + teams.get(i * 6 + j).getName());


                rowInline.add(btn);

            }
            rowsInline.add(rowInline);
        }

        inlineKeyboardMarkup.setKeyboard(rowsInline);

        return inlineKeyboardMarkup;
    }
}
