
import net.richardmarston.GnuChessComms;
import org.junit.*;
import org.junit.Test;

import java.lang.System;

import static org.junit.Assert.*;

public class GnuChessCommsTest {

    @Test
    public void testCommsCanStartGnuChess() {
        GnuChessComms comms = null;
        try {
            comms = new GnuChessComms();
            comms.waitForResponse(GnuChessComms.TIMEOUT);
            assertTrue("Chess".equals(comms.getResultOfLastCommand()));
        }
        finally {
            comms.close();
        }
    }

    @Test
    public void testCommsCanDetectInvalidCommand() {
        GnuChessComms comms = null;
        try {
            comms = new GnuChessComms();
            comms.waitForResponse(GnuChessComms.TIMEOUT);
            System.out.println("[" + comms.getResultOfLastCommand() + "]");
            comms.sendCommand("q1");
            comms.waitForResponse(GnuChessComms.TIMEOUT);
            System.out.println("[" + comms.getResultOfLastCommand() + "]");
            assertTrue("Invalid move: q1".equals(comms.getResultOfLastCommand()));
        }
        finally {
            comms.close();
        }
    }
}