
import net.richardmarston.ChessEngineComms;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.System;


public class ChessEngineCommsTest {

    @Test
    public void testCommsCanStartGnuChess() {
        ChessEngineComms comms = null;
        try {
            comms = new ChessEngineComms();
            comms.waitForResponse(ChessEngineComms.TIMEOUT);
            assertTrue("Chess".equals(comms.getResultOfLastCommand()));
        }
        finally {
            comms.close();
        }
    }

    @Test
    public void testCommsCanDetectInvalidCommand() {
        ChessEngineComms comms = null;
        try {
            comms = new ChessEngineComms();
            comms.waitForResponse(ChessEngineComms.TIMEOUT);
            System.out.println("[" + comms.getResultOfLastCommand() + "]");
            comms.sendCommand("q1");
            comms.waitForResponse(ChessEngineComms.TIMEOUT);
            System.out.println("[" + comms.getResultOfLastCommand() + "]");
            assertTrue("Invalid move: q1".equals(comms.getResultOfLastCommand()));
        }
        finally {
            comms.close();
        }
    }
}