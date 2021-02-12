package com.zh0glikk.hltvbot.TextPatterns;

import model.BigEvent;

public class EventPattern {
    public static String all(BigEvent event) {
        String result = "";

        result += event.getEventTitle() + "\n";
        result += event.getDate() + "\n";
        result += event.getLocation() + "\n";
        result += event.getTeams() + " teams" + "\n";
        result += "Prize: " + event.getPrize() + "\n";

        return result;
    }
}
