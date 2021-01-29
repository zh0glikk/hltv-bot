package com.zh0glikk.hltvbot.TextPatterns;

import model.Result;

public class ResultsPattern {

    public static String all(Result result) {
        String text = "";

        text += result.getTeam1() + " "
                + result.getResultScore()
                + " " + result.getTeam2()
                + "\n";

        text += "Event: " + result.getEvent() + "\n";

        return text;
    }
}
