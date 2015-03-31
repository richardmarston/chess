package net.richardmarston.controller;

import net.richardmarston.engine.ChessEngine;
import net.richardmarston.model.*;
import net.richardmarston.repository.UserRepository;
import net.richardmarston.service.ChessService;
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
 * @startuml
 * controller.ChessController -right> view.Messages
 * controller.ChessController -right> "view.View Templates (JSP)"
 * controller.ChessController -right-* model.Board
 * controller.ChessController -right-* model.Move
 * controller.ChessController -right-* engine.ChessEngine
 * class controller.ChessController {
 *     +requestChessForm()
 *     +processMoveForm()
 * }
 * set namespaceSeparator none
 * controller.ChessController -down-|> springframework.web.servlet.DispatcherServlet
 * class springframework.web.servlet.DispatcherServlet {
 * }
 * set namespaceSeparator .
 * hide springframework.web.servlet.DispatcherServlet members
 * hide controller.ChessController fields
 * @enduml
 * Created by rich on 25/03/15.
 */
/* set namespaceSeparator '*'*/
/* set namespaceSeparator '.'*/

@Controller
@RequestMapping()
public class ChessController {

    static Logger logger = Logger.getLogger(ChessController.class);

    private GameService gameService;
    private ChessService chessService;
    private ChessEngine engine;
    private Board board;

    @Autowired
    public ChessController(ChessService service) {
        gameService = new GameService();
        chessService = service;
        engine = new ChessEngine();
        board = new Board();
        board.setState(engine.getCurrentBoard());
    }

    public ChessController(ChessService service, GameService game, ChessEngine ce) {
        gameService = game;
        engine = ce;
        board = new Board();
        board.setState(engine.getCurrentBoard());
        chessService = service;
    }

        @RequestMapping(method = RequestMethod.GET, value="/login")
    public String requestLoginForm(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("message", Messages.getLoginMessage());
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, value="/login")
    public String submitLoginForm(ModelMap model, @ModelAttribute("user") User user, BindingResult result, SessionStatus status) {
        if(chessService.findByName(user.getName()) != null){
            logger.info("FOUND USER");
        }
        else {
            logger.info("USER NOT FOUND, ADDING");
            chessService.saveUser(user);
        }
        model.addAttribute("user", user);
        model.addAttribute("message", Messages.getLoginMessage());
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value="/game")
    public String requestChessForm (ModelMap model) {
        Move move = new Move();
        move.setCommandFromUser("");
        move.setRequestToUser("Enter your move!");
        model.addAttribute("board", getBoard());
        model.addAttribute("move", move);
        return "chess"; // will map to chess.jsp
    }

    @RequestMapping(method = RequestMethod.POST, value="/game")
    public String processCreationForm(ModelMap model, @ModelAttribute("move") Move move, BindingResult result, SessionStatus status) {
        ChessEngine.MoveResult chessResult = engine.validateMove(move);
        String message = Messages.getMessage(chessResult);
        move.setRequestToUser(message);
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