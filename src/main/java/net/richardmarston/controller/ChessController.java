package net.richardmarston.controller;

import net.richardmarston.engine.ChessEngine;
import net.richardmarston.model.*;
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
    private MoveValidator moveValidator;
    private ChessEngine engine;
    private Board board;

    @Autowired
    public ChessController(MoveValidator validator, GameService service, ChessEngine ce) {
        moveValidator = validator;
        gameService = service;
        engine = ce;
        board = new Board();
        board.setState(engine.getCurrentBoard());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String requestChessForm(ModelMap model) {

        Move move = new Move();
        move.setCommand("changeme");
        move.setMessage("Enter your move!");
        model.addAttribute("board", getBoard());
        model.addAttribute("move", move);
        return "chess"; // will map to chess.jsp
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processCreationForm(ModelMap model, @ModelAttribute("move") Move move, BindingResult result, SessionStatus status) {
        moveValidator.validate(move, result);
        if (result.hasErrors()) {
            move.setMessage("Invalid move. Please try again!");
            model.addAttribute("move", move);
            model.addAttribute("board", getBoard());
            return "chess";
        } else {
            logger.info("Move complete: "+move.getCommand());

            this.gameService.saveMove(move);
            move.setMessage("Enter your move!");
            board.setState(engine.getCurrentBoard());
            model.addAttribute("board", getBoard());
            status.setComplete();
            return "chess";
        }
    }

    private ArrayList<ArrayList<String>> getBoard() {
        return board.getBoardAsStrings();
    }
}