package net.richardmarston;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

/**
 * Created by rich on 25/03/15.
 */
public class MoveValidator {
    static Logger logger = Logger.getLogger(MoveValidator.class);

    ChessEngineComms comms;

    public MoveValidator(ChessEngineComms cec) {
        comms=cec;
    }

    public String validate(Move move, BindingResult result) {
        logger.info("Move attempted: "+ move.getCommand());
        comms.sendCommand(move.getCommand());
        comms.waitForResponse(ChessEngineComms.TIMEOUT);
        logger.info("Result was: "+comms.getResultOfLastCommand());
        return comms.getResultOfLastCommand();
    }
}
