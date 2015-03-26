package net.richardmarston.model;

import org.apache.log4j.Logger;

/**
 * Created by rich on 25/03/15.
 */
public class GameService {
    static Logger logger = Logger.getLogger(GameService.class);
    public void saveMove(Move move) {
        logger.debug("Move save attempted.");
        // haven't written any DAO yet.
    }
}
