package com.zh0glikk.hltvbot.TextPatterns;

import com.vdurmont.emoji.EmojiParser;
import model.Player;

public class PlayerPattern {

    public static String all(Player player) {
        String text = "";

        text += "Nickname: " + player.getNickName() + "\n";
        text += "Real name: " + player.getRealName() + "\n";
        text += "Age: " + player.getAge() + "\n";
        text += "Current team: " + player.getCurrentTeam() + "\n";
        text += "\nStatistics\n";
        text += "Rating 2.0: " + player.getRating2_0();

        if ( player.getRating2_0() > 1.1 ) {
            text  += EmojiParser.parseToUnicode( ":fire:");
        }

        text += "\n";
        text += "Headshots: " + player.getHeadshots() + "\n";
        text += "Kills per round: " + player.getKillsPerRound() + "\n";
        text += "Maps played: " + player.getMapsPlayed() + "\n";
        text += "Death per round: " + player.getDeathPerRound() + "\n";

        return text;
    }
}
