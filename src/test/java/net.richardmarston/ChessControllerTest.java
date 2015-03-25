package net.richardmarston;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.support.SessionStatus;

import java.beans.PropertyEditor;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by rich on 25/03/15.
 */
public class ChessControllerTest {
    static Logger logger = Logger.getLogger(ChessEngineComms.class);

    private ChessController controller;
    private MoveValidator mockMoveValidator;
    private BindingResult mockBindingResult;
    private SessionStatus mockSessionStatus;
    private GameService mockGameService;

    @Before
    public void setup() {
        mockMoveValidator = mock(MoveValidator.class);
        mockBindingResult = mock(BindingResult.class);
        mockSessionStatus = mock(SessionStatus.class);
        mockGameService = mock(GameService.class);
        controller = new ChessController(mockMoveValidator, mockGameService);

    }

    @Test
    public void testRequestChessFormReturnsChess() {
        ModelMap map = new ModelMap();
        assertTrue(new String("chess").equals(controller.requestChessForm(map)));
    }

    @Test
    public void processCreationFormCallsValidate() {
        Move move = new Move();
        String s = controller.processCreationForm(new Move(), mockBindingResult, mockSessionStatus );
        verify(mockMoveValidator).validate(any(Move.class), any(BindingResult.class));
    }

    @Test
    public void processCreationFormCallsSaveMoveIfValidationPasses() {
        Move move = new Move();
        when(mockBindingResult.hasErrors()).thenReturn(false);
        assertFalse(mockBindingResult.hasErrors());
        String s = controller.processCreationForm(new Move(), mockBindingResult, mockSessionStatus );
        verify(mockMoveValidator).validate(any(Move.class), any(BindingResult.class));
        verify(mockGameService).saveMove(any(Move.class));
        verify(mockSessionStatus).setComplete();
    }

    @Test
    public void processCreationFormDoesNotCallSaveMoveIfValidationFails() {
        Move move = new Move();
        when(mockBindingResult.hasErrors()).thenReturn(true);
        assertTrue(mockBindingResult.hasErrors());
        String s = controller.processCreationForm(new Move(), mockBindingResult, mockSessionStatus );
        verify(mockMoveValidator).validate(any(Move.class), any(BindingResult.class));
        verify(mockGameService, times(0)).saveMove(any(Move.class));
    }
}
