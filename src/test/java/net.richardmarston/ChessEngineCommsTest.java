package net.richardmarston;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.System;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static java.lang.Thread.sleep;


public class ChessEngineCommsTest {

    static Logger logger = Logger.getLogger(ChessEngineCommsTest.class);

    @Test
    public void commsCanStartGnuChess() {
        ChessEngineComms comms = null;
        try {
            comms = new ChessEngineComms();
            assertTrue("Chess".equals(comms.getResultOfLastCommand()));
        }
        finally {
            comms.close();
        }
    }

    @Test
    public void commsCanDetectInvalidCommand() {
        ChessEngineComms comms = null;
        try {
            comms = new ChessEngineComms();
            comms.sendCommand("q1");
            assertTrue("Invalid move: q1".equals(comms.getResultOfLastCommand()));
        }
        finally {
            comms.close();
        }
    }

    @Test
    public void newGameShowsNewBoard() {
        ChessEngineComms comms = null;
        ArrayList<String> expectedState = new ArrayList<String>(Arrays.asList(
                "",
                "white  KQkq",
                "r n b q k b n r ",
                "p p p p p p p p ",
                ". . . . . . . . ",
                ". . . . . . . . ",
                ". . . . . . . . ",
                ". . . . . . . . ",
                "P P P P P P P P ",
                "R N B Q K B N R "
                ));
        try {
            comms = new ChessEngineComms();
            ArrayList<String> gameState = comms.getCurrentBoard();
            Iterator<String> iterator = gameState.iterator();
            expectedState.forEach(line -> {
                String next = iterator.next();
                assertTrue(next.equals(line));
            });
        }
        finally {
            comms.close();
        }
    }
}