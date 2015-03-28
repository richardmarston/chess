package net.richardmarston.view;

import net.richardmarston.engine.ChessEngine;
import org.apache.log4j.Logger;

/**
 * Created by rich on 27/03/15.
 */
public class Messages {
    static Logger logger = Logger.getLogger(Messages.class);
    private static final String blackWins      = "Game complete. Black wins!";
    private static final String whiteWins      = "Game complete. White wins!";
    private static final String invalidMove    = "Invalid move. Please try again!";
    private static final String enterMove      = "Enter your move!";

    public static String getMessage(ChessEngine.MoveResult moveResult) {
        switch (moveResult) {
            case Invalid:
                return invalidMove;
            case BlackMate:
                return blackWins;
            case WhiteMate:
                return whiteWins;
            case OK:
                return enterMove;
            default:
                logger.error("Failed to parse MoveResult: "+moveResult+" into a message.");
                return "";
        }
    }
}
