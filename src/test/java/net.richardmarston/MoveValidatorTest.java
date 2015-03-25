package net.richardmarston;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by rich on 25/03/15.
 */
public class MoveValidatorTest {

    private ChessEngineComms mockChessEngineComms;
    private BindingResult mockBindingResult;
    private MoveValidator moveValidator;

    @Before
    public void setup() {
        mockChessEngineComms = mock(ChessEngineComms.class);
        mockBindingResult = mock(BindingResult.class);
        moveValidator = new MoveValidator(mockChessEngineComms);
    }

    @Test
    public void validatorSendsCommandAwaitsResponseAndReturnsReply() {
        Move move = new Move();
        move.setCommand("test command");
        String expectedResponse = "that command isn't right";
        when(mockChessEngineComms.getResultOfLastCommand()).thenReturn(expectedResponse);
        String actualResponse = moveValidator.validate(move, mockBindingResult);
        verify(mockChessEngineComms).sendCommand(anyString());
        verify(mockChessEngineComms).waitForResponse(anyLong());
        assertTrue(expectedResponse.equals(actualResponse));

    }
}
