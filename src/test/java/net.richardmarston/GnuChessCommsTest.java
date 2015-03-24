
import net.richardmarston.GnuChessComms;
import org.junit.*;
import org.junit.Test;

import java.lang.System;

import static org.junit.Assert.*;

public class GnuChessCommsTest {

    @Test
    public void testCommsCanStartGnuChess() {
        GnuChessComms comms = new GnuChessComms();
        System.out.println(comms.getResultOfLastCommand());
        assertTrue("Chess".equals(comms.getResultOfLastCommand()));
    }
}