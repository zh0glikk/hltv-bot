package com.zh0glikk.hltvbot.TextPatterns;

import model.Match;

public class MatchPattern {

    public static String all(Match match) {
        String text = "";

        text += match.getTeam1() + " vs " + match.getTeam2() + " (" + match.getMatchMeta() + ")\n";
        text += "Time: " + match.getTime() + "\n";
        text += "Tournament: " + match.getTournament() + "\n";
        text += "Rating: " + match.getMatchRating() + "\n";

        return text;
    }

}
