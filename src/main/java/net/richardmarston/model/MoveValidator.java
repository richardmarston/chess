package net.richardmarston.model;

import net.richardmarston.engine.ChessEngine;
import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * Created by rich on 25/03/15.
 */
public class MoveValidator {
    static Logger logger = Logger.getLogger(MoveValidator.class);

    ChessEngine engine;

    public MoveValidator(ChessEngine cec) {
        engine=cec;
    }

    public void validate(Move move, BindingResult result) {
        logger.debug("Move attempted: " + move.getCommand());
        engine.sendCommand(move.getCommand());
        logger.debug("Result was: [" + engine.getResultOfLastCommand() + "]");
        if (engine.getResultOfLastCommand().contains("Invalid")) {
            result.addError(new ObjectError("Move", "Invalid move requested."));
        }
    }
}
