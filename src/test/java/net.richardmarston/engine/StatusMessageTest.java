package net.richardmarston.engine;

import net.richardmarston.engine.StatusMessage;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by rich on 27/03/15.
 */
public class StatusMessageTest {

    @Test
    public void canParseOK() {
        StatusMessage message = new StatusMessage();
        message.addTextLine("1. b3");
        assertTrue(message.OK());
    }

    @Test
    public void canParseInvalid() {
        StatusMessage message = new StatusMessage();
        message.addTextLine("Invalid move: b9");
        assertTrue(message.invalidMove());
    }

    @Test
    public void canParseWhiteMate() {
        StatusMessage message = new StatusMessage();
        message.addTextLine("1-0 {White mates}");
        assertTrue(message.whiteMate());
    }

    @Test
    public void canParseBlackMate() {
        StatusMessage message = new StatusMessage();
        message.addTextLine("1-0 {Black mates}");
        assertTrue(message.blackMate());
    }

    @Test
    public void canParseBoardUpdate() {
        StatusMessage message = new StatusMessage();
        message.addTextLine("");
        message.addTextLine("white  KQkq");
        assertTrue(message.boardUpdate());
        message = new StatusMessage();
        message.addTextLine("");
        message.addTextLine("black  KQkq");
        assertTrue(message.boardUpdate());
    }
}
