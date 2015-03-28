package net.richardmarston.controller;

import net.richardmarston.model.BoardSamples;
import net.richardmarston.engine.ChessEngine;
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
    private BindingResult mockBindingResult;
    private SessionStatus mockSessionStatus;
    private GameService mockGameService;
    private ChessEngine mockChessEngine;

    @Before
    public void setup() {
        mockBindingResult = mock(BindingResult.class);
        mockSessionStatus = mock(SessionStatus.class);
        mockGameService = mock(GameService.class);
        mockChessEngine = mock(ChessEngine.class);
        when(mockChessEngine.getCurrentBoard()).thenReturn(BoardSamples.getStartingBoard());
        controller = new ChessController(mockGameService, mockChessEngine);
    }

    @Test
    public void testRequestChessFormReturnsChess() {
        ModelMap map = new ModelMap();
        assertTrue(new String("chess").equals(controller.requestChessForm(map)));
    }

    @Test
    public void processCreationFormCallsValidate() {
        Board board = new Board();
        Move move = new Move();
        when(mockChessEngine.validateMove(any(Move.class))).thenReturn(ChessEngine.MoveResult.OK);
        String s = controller.processCreationForm(new ModelMap(), new Move(), mockBindingResult, mockSessionStatus );
        verify(mockChessEngine).validateMove(any(Move.class));
    }

    @Test
    public void processCreationFormCallsSaveMoveIfValidationPasses() {
        Board board = new Board();
        when(mockChessEngine.validateMove(any(Move.class))).thenReturn(ChessEngine.MoveResult.OK);
        String s = controller.processCreationForm(new ModelMap(), new Move(), mockBindingResult, mockSessionStatus );
        verify(mockChessEngine).validateMove(any(Move.class));
        verify(mockGameService).saveMove(any(Move.class));
        verify(mockSessionStatus).setComplete();
    }

    @Test
    public void processCreationFormDoesNotCallSaveMoveIfValidationFails() {
        Board board = new Board();
        when(mockChessEngine.validateMove(any(Move.class))).thenReturn(ChessEngine.MoveResult.Invalid);
        String s = controller.processCreationForm(new ModelMap(), new Move(), mockBindingResult, mockSessionStatus );
        verify(mockChessEngine).validateMove(any(Move.class));
        verify(mockGameService, times(0)).saveMove(any(Move.class));
    }
}
