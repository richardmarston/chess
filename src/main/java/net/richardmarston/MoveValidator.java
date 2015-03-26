package net.richardmarston;

import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * Created by rich on 25/03/15.
 */
public class MoveValidator {
    static Logger logger = Logger.getLogger(MoveValidator.class);

    ChessEngineComms comms;

    public MoveValidator(ChessEngineComms cec) {
        comms=cec;
    }

    public void validate(Move move, BindingResult result) {
        logger.debug("Move attempted: "+ move.getCommand());
        comms.sendCommand(move.getCommand());
        logger.debug("Result was: "+comms.getResultOfLastCommand());
        if (comms.getResultOfLastCommand().contains("Invalid")) {
            result.addError(new ObjectError("Move", "Invalid move requested."));
        }
    }
}
