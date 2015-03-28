package net.richardmarston;

import net.richardmarston.engine.ChessEngine;
import net.richardmarston.engine.EngineIO;
import net.richardmarston.engine.StatusMessage;
import net.richardmarston.model.Move;
import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

import static org.mockito.Mockito.*;

public class ChessEngineTest {

    static Logger logger = Logger.getLogger(ChessEngineTest.class);

    @Test
    public void canStartGnuChess() {
        ChessEngine engine = null;
        try {
            engine = new ChessEngine();
            //assertTrue("Chess".equals(engine.getResultOfLastCommand()));
        }
        finally {
            engine.close();
        }
    }

    @Test
    public void canDetectInvalidCommand() {
        ChessEngine engine = null;
        try {
            engine = new ChessEngine();
            Move move = new Move();
            move.setCommandFromUser("q1");
            assertTrue(engine.validateMove(move) == ChessEngine.MoveResult.Invalid);
        }
        finally {
            engine.close();
        }
    }

    @Test
    public void newGameShowsNewBoard() {
        ChessEngine engine = null;
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
            engine = new ChessEngine();
            ArrayList<String> gameState = engine.getCurrentBoard();
            Iterator<String> iterator = gameState.iterator();
            expectedState.forEach(line -> {
                String next = iterator.next();
                assertTrue(next.equals(line));
            });
        }
        catch (NullPointerException npe) {
            logger.debug("OH NO, IT'S A NULL POINTER!");
        }
        finally {
            logger.info("CLOSING CHESS ENGINE!");
            engine.close();
        }
    }

    @Test
    public void canParseBoardReplyInvalid() {
        StatusMessage message = mock(StatusMessage.class);
        when(message.invalidMove()).thenReturn(true);
        assertTrue(ChessEngine.parseResponse(message) == ChessEngine.MoveResult.Invalid);
    }

    @Test
    public void canParseBoardReplyOK() {
        StatusMessage message = mock(StatusMessage.class);
        when(message.OK()).thenReturn(true);
        assertTrue(ChessEngine.parseResponse(message) == ChessEngine.MoveResult.OK);
    }

    @Test
    public void canParseBoardReplyBlackMate() {
        StatusMessage message = mock(StatusMessage.class);
        when(message.blackMate()).thenReturn(true);
        assertTrue(ChessEngine.parseResponse(message) == ChessEngine.MoveResult.BlackMate);
    }

    @Test
    public void canParseBoardReplyWhiteMate() {
        StatusMessage message = mock(StatusMessage.class);
        when(message.whiteMate()).thenReturn(true);
        assertTrue(ChessEngine.parseResponse(message) == ChessEngine.MoveResult.WhiteMate);
    }
}