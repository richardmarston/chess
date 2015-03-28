package net.richardmarston.controller;

import net.richardmarston.engine.ChessEngine;
import net.richardmarston.model.*;
import net.richardmarston.view.Messages;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;

/**
 * Created by rich on 25/03/15.
 */

@Controller
@RequestMapping("/example")
public class ChessController {

    static Logger logger = Logger.getLogger(ChessController.class);

    private GameService gameService;
    private ChessEngine engine;
    private Board board;

    @Autowired
    public ChessController(GameService service, ChessEngine ce) {
        gameService = service;
        engine = ce;
        board = new Board();
        board.setState(engine.getCurrentBoard());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String requestChessForm(ModelMap model) {

        Move move = new Move();
        move.setCommand("");
        move.setMessage("Enter your move!");
        model.addAttribute("board", getBoard());
        model.addAttribute("move", move);
        return "chess"; // will map to chess.jsp
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processCreationForm(ModelMap model, @ModelAttribute("move") Move move, BindingResult result, SessionStatus status) {
        ChessEngine.MoveResult chessResult = engine.validate(move);
        String message = Messages.getMessage(chessResult);
        move.setMessage(message);
        if (chessResult != ChessEngine.MoveResult.Invalid) {
            this.gameService.saveMove(move);
        }
        board.setState(engine.getCurrentBoard());
        model.addAttribute("board", getBoard());
        status.setComplete();
        return "chess";
    }

    private ArrayList<ArrayList<String>> getBoard() {
        return board.getBoardAsStrings();
    }
}