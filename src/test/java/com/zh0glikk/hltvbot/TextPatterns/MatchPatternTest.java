package com.zh0glikk.hltvbot.TextPatterns;

import model.Match;
import org.junit.jupiter.api.Test;

class MatchPatternTest {

    @Test
    public void testAll() {
        Match match = new Match("14:00",
                "Astralis",
                "NaVi",
                "Blast",
                "b03",
                5);

        System.out.println(MatchPattern.all(match));
    }

}