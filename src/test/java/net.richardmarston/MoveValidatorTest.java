package net.richardmarston;

import net.richardmarston.engine.ChessEngine;
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

    private ChessEngine mockChessEngine;
    private BindingResult mockBindingResult;
    private MoveValidator moveValidator;

    @Before
    public void setup() {
        mockChessEngine = mock(ChessEngine.class);
        mockBindingResult = mock(BindingResult.class);
        moveValidator = new MoveValidator(mockChessEngine);
    }

    @Test
    public void validatorSendsCommandAndReturnsReply() {
        Move move = new Move();
        String expectedResponse = "test command";
        move.setCommand(expectedResponse);
        when(mockChessEngine.getResultOfLastCommand()).thenReturn(expectedResponse);
        moveValidator.validate(move, mockBindingResult);
        verify(mockChessEngine).sendCommand(anyString());
    }
}
