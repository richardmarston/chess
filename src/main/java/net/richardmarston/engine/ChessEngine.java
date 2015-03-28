package net.richardmarston.engine;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

import net.richardmarston.model.Move;
import org.apache.log4j.Logger;

/**
 * @startuml
 * engine.ChessEngine --* engine.EngineIO
 * class engine.ChessEngine {
 *     -Thread readThread
 *     -ArrayList<String> currentBoard
 *     +validateMove()
 *     +close()
 * }
 * @enduml
 * Created by rich on 23/03/15.
 */
public class ChessEngine {

    private EngineIO engineIO;
    private ArrayList<String> currentBoard;
    private Thread readThread;

    static Logger logger = Logger.getLogger(ChessEngine.class);

    public enum MoveResult {
        Invalid,
        BlackMate,
        WhiteMate,
        UpdateBoard,
        OK
    }

    public ChessEngine() {
        engineIO = new EngineIO();
        readThread = new Thread(engineIO);
        readThread.start();
        engineIO.getCurrentBoard();
        waitForReply(); // this should be "Chess"
        waitForReply(); // this should be the board update
    }

    public ChessEngine(Boolean dontstart){

    }

    private MoveResult waitForReply() {
        StatusMessage nextReply;
        MoveResult moveResult = MoveResult.OK;
        while((nextReply = engineIO.getNextReply()) == null) {
            try {
                sleep(100);
            }
            catch(InterruptedException ie) {
                // oh no
            }
        }
        logger.info("Result was: [" + nextReply.getTextLines() + "]");
        MoveResult result;
        if ((result = parseResponse(nextReply)) != MoveResult.UpdateBoard){
            moveResult = result;
        }
        else {
            logger.info("UPDATING BOARD");
            currentBoard = nextReply.getTextLines();
        }
        logger.info("moveResult: "+ moveResult);
        return moveResult;
    }

    /* For mocking & testing */
    public ChessEngine(EngineIO eio) {
        this();
        engineIO = eio;
    }

    public ArrayList<String> getCurrentBoard() {
        return currentBoard;
    }

    public Boolean gameOver(MoveResult moveResult) {
        return moveResult == MoveResult.BlackMate || moveResult == MoveResult.WhiteMate;
    }

    public MoveResult validateMove(Move move) {
        logger.info("Move attempted: " + move.getCommandFromUser());
        engineIO.sendCommand(move.getCommandFromUser());
        MoveResult validMove = waitForReply(); // this will be the reply to sendCommand, either OK or Invalid
        engineIO.getCurrentBoard();
        MoveResult boardUpdateReply = waitForReply(); // this will be the board or an indication that someone has won.
        if (gameOver(boardUpdateReply)) {
            waitForReply(); // when someone has won we have to wait again for the board.
            return boardUpdateReply;
        }
        else {
            return validMove;
        }
    }

    private static MoveResult parseResponse(StatusMessage response)
    {
        if (response.OK()) {
            return MoveResult.OK;
        }
        else if (response.boardUpdate()) {
            return MoveResult.UpdateBoard;
        }
        else if (response.invalidMove()) {
            return MoveResult.Invalid;
        }
        else if (response.whiteMate()) {
            return MoveResult.WhiteMate;
        }
        else if (response.blackMate()) {
            return MoveResult.BlackMate;
        }
        else {
            logger.error("Could not parse response: "+response.getTextLines());
            return MoveResult.Invalid;
        }
    }

    public void close() {
        engineIO.close();
        readThread.interrupt();
    }
}