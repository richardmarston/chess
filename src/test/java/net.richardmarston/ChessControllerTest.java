package net.richardmarston;

import net.richardmarston.controller.ChessController;
import net.richardmarston.model.*;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by rich on 25/03/15.
 */
public class ChessControllerTest {
    static Logger logger = Logger.getLogger(ChessControllerTest.class);

    private ChessController controller;
    private MoveValidator mockMoveValidator;
    private BindingResult mockBindingResult;
    private SessionStatus mockSessionStatus;
    private GameService mockGameService;
    private ChessEngineComms mockChessEngineComms;

    @Before
    public void setup() {
        mockMoveValidator = mock(MoveValidator.class);
        mockBindingResult = mock(BindingResult.class);
        mockSessionStatus = mock(SessionStatus.class);
        mockGameService = mock(GameService.class);
        mockChessEngineComms = mock(ChessEngineComms.class);
        controller = new ChessController(mockMoveValidator, mockGameService, mockChessEngineComms);
    }

    @Test
    public void testRequestChessFormReturnsChess() {
        ModelMap map = new ModelMap();
        assertTrue(new String("chess").equals(controller.requestChessForm(map)));
    }

    @Test
    public void processCreationFormCallsValidate() {
        Board board = new Board();
        String s = controller.processCreationForm(new ModelMap(), new Move(), mockBindingResult, mockSessionStatus );
        verify(mockMoveValidator).validate(any(Move.class), any(BindingResult.class));
    }

    @Test
    public void processCreationFormCallsSaveMoveIfValidationPasses() {
        Board board = new Board();
        when(mockBindingResult.hasErrors()).thenReturn(false);
        assertFalse(mockBindingResult.hasErrors());
        String s = controller.processCreationForm(new ModelMap(), new Move(), mockBindingResult, mockSessionStatus );
        verify(mockMoveValidator).validate(any(Move.class), any(BindingResult.class));
        verify(mockGameService).saveMove(any(Move.class));
        verify(mockSessionStatus).setComplete();
    }

    @Test
    public void processCreationFormDoesNotCallSaveMoveIfValidationFails() {
        Board board = new Board();
        when(mockBindingResult.hasErrors()).thenReturn(true);
        assertTrue(mockBindingResult.hasErrors());
        String s = controller.processCreationForm(new ModelMap(), new Move(), mockBindingResult, mockSessionStatus );
        verify(mockMoveValidator).validate(any(Move.class), any(BindingResult.class));
        verify(mockGameService, times(0)).saveMove(any(Move.class));
    }
}
