package com.zh0glikk.hltvbot.TextPatterns;

import model.TopTeam;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamPatternTest {

    @Test
    public void testAll() {
        List<String> players = Arrays.asList(new String[]{"device", "xyp9x", "magisk"});
        List<String> urls = new ArrayList<>();


        TopTeam topTeam = new TopTeam(
                "#5",
                "Astralis",
                "(1000 points)",
                players,
                urls,
                ""
        );

        assertEquals(TeamPattern.all(topTeam),
                "#5. Astralis (1000 points)\n" +
                        "Players: [device, xyp9x, magisk]\n");
    }

    @Test
    public void testForStat() {
        List<String> players = Arrays.asList(new String[]{"device", "xyp9x", "magisk"});
        List<String> urls = new ArrayList<>();

        TopTeam topTeam = new TopTeam(
                "#5",
                "Astralis",
                "(1000 points)",
                players,
                urls,
                ""
        );

        assertEquals(TeamPattern.forStat(topTeam).toString(),
                "#5. Astralis, (1000 points)\n");
    }

}