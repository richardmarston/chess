package net.richardmarston;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Created by rich on 25/03/15.
 */

@Controller
@RequestMapping("/example")
public class ChessController{

    private GameService gameService;

    private MoveValidator moveValidator;

    @Autowired
    public ChessController(MoveValidator validator, GameService service) {
        moveValidator = validator;
        gameService = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String requestChessForm(ModelMap model) {

        Move move = new Move();
        model.addAttribute("board", move.getBoard());
        move.setCommand("changeme");
        model.put("move", move);
        return "chess"; // will map to chess.jsp
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processCreationForm(@ModelAttribute("move") Move move, BindingResult result, SessionStatus status) {
        moveValidator.validate(move, result);
        if (result.hasErrors()) {
            return "chessInvalidMove";
        } else {
            this.gameService.saveMove(move);
            status.setComplete();
            return "chess";
        }
    }
}