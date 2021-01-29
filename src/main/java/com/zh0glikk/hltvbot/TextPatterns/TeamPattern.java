package com.zh0glikk.hltvbot.TextPatterns;

import model.TopTeam;

public class TeamPattern {

    public static String forStat(TopTeam topTeam) {
        String text = "";

        text += topTeam.getPosition() + ". ";
        text += topTeam.getName() + ", ";
        text += topTeam.getPoints();

        if ( topTeam.getPosition().equals("#1") ) {
            text += "\uD83E\uDD47";
        } else if ( topTeam.getPosition().equals("#2") ) {
            text += "\uD83E\uDD48";
        } else if ( topTeam.getPosition().equals("#3") ) {
            text += "\uD83E\uDD49";
        }

        text += "\n";

        return text;
    }

    public static String all(TopTeam team) {
        String players = "";

        for ( String player : team.getPlayers() ) {
            players += player + ",";
        }

        String text = team.getPosition() + ". "
                + team.getName() + " "
                + team.getPoints();

        if ( team.getPosition().equals("#1") ) {
            text += "\uD83E\uDD47";
        } else if ( team.getPosition().equals("#2") ) {
            text += "\uD83E\uDD48";
        } else if ( team.getPosition().equals("#3") ) {
            text += "\uD83E\uDD49";
        }
        text += "\n";

        text += "Players: " + team.getPlayers() + "\n";

        return text;
    }
}
