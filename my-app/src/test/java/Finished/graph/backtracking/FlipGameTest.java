package Finished.graph.backtracking;

import Finished.graph.backtracking.FlipGame;
import org.junit.Test;

import java.util.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/4/17.
 */
public class FlipGameTest {
    FlipGame game = new FlipGame();
    @Test
    public void generatePossibleNextMovesEdgeCases() throws Exception {
        //edge cases
        List<String> testResult1 = game.generatePossibleNextMoves(null);
        assertTrue(testResult1.isEmpty());
        List<String> testResult2 = game.generatePossibleNextMoves("+-");
        assertTrue(testResult2.isEmpty());
        List<String> testResult3 = game.generatePossibleNextMoves("--");
        assertTrue(testResult3.isEmpty());
    }

    @Test
    public void generatePossibleNextMovesNormalCases() throws Exception {
        //normal cases: "++---++++-"
        //results: "-----++++-", "++-----++-", "++---+--+-", "++---++---"
        String input = "++---++++-";
        Set<String> expectedResult = new HashSet<String>();
        expectedResult.add("-----++++-");
        expectedResult.add("++-----++-");
        expectedResult.add("++---+--+-");
        expectedResult.add("++---++---");
        List<String> listResult2 = game.generatePossibleNextMoves(input);
        Set<String> testResult2 = new HashSet<String>();
        testResult2.addAll(listResult2);
        assertTrue(testResult2.equals(expectedResult));
    }

}