package net.richardmarston;

import net.richardmarston.model.ChessEngineComms;
import net.richardmarston.model.Move;
import net.richardmarston.model.MoveValidator;
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
    public void validatorSendsCommandAndReturnsReply() {
        Move move = new Move();
        String expectedResponse = "test command";
        move.setCommand(expectedResponse);
        when(mockChessEngineComms.getResultOfLastCommand()).thenReturn(expectedResponse);
        moveValidator.validate(move, mockBindingResult);
        verify(mockChessEngineComms).sendCommand(anyString());
    }
}
