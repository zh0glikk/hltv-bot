package com.zh0glikk.hltvbot.TextPatterns;

import model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerPatternTest {

    @Test
    public void testAll() {
        Player player = new Player(
                "device",
                "Nikolay Ritz",
                "25 years",
                "Astralis",
                Float.parseFloat("1.09"),
                Float.parseFloat("0.89"),
                "43%",
                Integer.parseInt("89"),
                Float.parseFloat("0.68")
        );

        assertEquals(PlayerPattern.all(player),
                "Nickname: device\n" +
                        "Real name: Nikolay Ritz\n" +
                        "Age: 25 years\n" +
                        "Current team: Astralis\n" +
                        "\n" +
                        "Statistics\n" +
                        "rating 2.0: 1.09\n" +
                        "headshots: 43%\n" +
                        "Kills per round: 0.89\n" +
                        "Maps played: 89\n" +
                        "Death per round: 0.68\n");
    }

}