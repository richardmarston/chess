package net.richardmarston;

import org.apache.log4j.Logger;

/**
 * Created by rich on 25/03/15.
 */
public class GameService {
    static Logger logger = Logger.getLogger(ChessEngineComms.class);
    public void saveMove(Move move) {
        logger.info("MOVE SAVE ATTEMPTED.");
        // haven't written any DAO yet.
    }
}
