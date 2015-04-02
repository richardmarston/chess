package net.richardmarston.model;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import static org.junit.Assert.*;

/**
 * Created by rich on 26/03/15.
 */
public class BoardTest {
    static Logger logger = Logger.getLogger(BoardTest.class);
    @Test
    public void boardCanInterpretNewGame(){
        Board board = new Board();

        board.setState(BoardSamples.getStartingBoard());
        ArrayList<ArrayList<String>> expected = new ArrayList<ArrayList<String>>(Arrays.asList(
                new ArrayList<String>(Arrays.asList("r", "n", "b", "q", "k", "b", "n", "r")),

                new ArrayList<String>(Arrays.asList("p", "p", "p", "p", "p", "p", "p", "p")),

                new ArrayList<String>(Arrays.asList(".", ".", ".", ".", ".", ".", ".", ".")),

                new ArrayList<String>(Arrays.asList(".", ".", ".", ".", ".", ".", ".", ".")),

                new ArrayList<String>(Arrays.asList(".", ".", ".", ".", ".", ".", ".", ".")),

                new ArrayList<String>(Arrays.asList(".", ".", ".", ".", ".", ".", ".", ".")),

                new ArrayList<String>(Arrays.asList("P", "P", "P", "P", "P", "P", "P", "P")),

                new ArrayList<String>(Arrays.asList("R", "N", "B", "Q", "K", "B", "N", "R"))

        ));

        Iterator <ArrayList<String>> actualIterator = board.getBoardAsStrings().iterator();
        expected.forEach(expectedList->{
            Iterator <String> actualList = actualIterator.next().iterator();
            expectedList.forEach(expectedString->{
                String actualString = actualList.next();
                assertTrue(expectedString.equals(actualString));
            });
        });
    }

    @Test
    public void boardCanInterpretQueenTopRow(){
        Board board = new Board();

        board.setState(BoardSamples.getQueenTopRowBoard());
        ArrayList<ArrayList<String>> expected = new ArrayList<ArrayList<String>>(Arrays.asList(
                new ArrayList<String>(Arrays.asList("r", "n", "b", "q", "k", "b", "n", "Q")),

                new ArrayList<String>(Arrays.asList(".", ".", ".", "p", "p", "p", "p", ".")),

                new ArrayList<String>(Arrays.asList("p", "p", "p", ".", ".", ".", ".", ".")),

                new ArrayList<String>(Arrays.asList(".", ".", ".", ".", ".", ".", ".", ".")),

                new ArrayList<String>(Arrays.asList(".", ".", ".", ".", ".", ".", ".", ".")),

                new ArrayList<String>(Arrays.asList(".", ".", ".", ".", "P", ".", ".", ".")),

                new ArrayList<String>(Arrays.asList("P", "P", "P", "P", ".", "P", "P", "P")),

                new ArrayList<String>(Arrays.asList("R", "N", "B", ".", "K", "B", "N", "R"))

        ));

        Iterator <ArrayList<String>> actualIterator = board.getBoardAsStrings().iterator();
        expected.forEach(expectedList->{
            Iterator <String> actualList = actualIterator.next().iterator();
            expectedList.forEach(expectedString->{
                String actualString = actualList.next();
                logger.info("checking "+expectedString+" against "+actualString);
                assertTrue(expectedString.equals(actualString));
            });
        });
    }
}
