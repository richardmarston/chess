package net.richardmarston.engine;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

import net.richardmarston.model.Move;
import org.apache.log4j.Logger;

/**
 * Created by rich on 23/03/15.
 */
public class ChessEngine {

    private EngineIO engineIO;
    private ArrayList<String> currentBoard;
    private MoveResult moveResult;
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
        moveResult = MoveResult.OK;
        readThread = new Thread(engineIO);
        readThread.start();
        engineIO.getCurrentBoard();
        waitForReply(); // this should be "Chess"
        waitForReply(); // this should be the board update
    }

    public ChessEngine(Boolean dontstart){

    }

    public void waitForReply() {
        StatusMessage nextReply;
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
    }

    /* For mocking & testing */
    public ChessEngine(EngineIO eio) {
        this();
        engineIO = eio;
    }

    public ArrayList<String> getCurrentBoard() {
        return currentBoard;
    }

    public void checkForReply() {
        StatusMessage nextReply;
        while((nextReply = engineIO.getNextReply()) != null){
            logger.info("Result was: [" + nextReply.getTextLines() + "]");
            parseResponse(nextReply);
        }
        logger.info("nextReply: "+nextReply + " moveResult: "+ moveResult);
    }

    public MoveResult validate(Move move) {
        logger.info("Move attempted: " + move.getCommand());
        engineIO.sendCommand(move.getCommand());
        waitForReply(); // this will be the reply to sendCommand, either OK or Invalid
        engineIO.getCurrentBoard();
        waitForReply(); // this will be the board or an indication that someone has won.
        if (moveResult == MoveResult.BlackMate || moveResult == MoveResult.WhiteMate){
            waitForReply(); // when someone has won we have to wait again for the board.
        }
        return moveResult;
    }

    public static MoveResult parseResponse(StatusMessage response)
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