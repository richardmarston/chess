package net.richardmarston.engine;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import org.apache.log4j.Logger;

/**
 * Created by rich on 23/03/15.
 */
public class ChessEngine {

    static Logger logger = Logger.getLogger(ChessEngine.class);
    private static final int TIMEOUT = 3000;
    ArrayList<String> resultOfLastCommand;
    InputStream readInputStream = null;
    BufferedReader reader = null;
    BufferedWriter writer = null;
    Process engineProcess = null;

    public String getResultOfLastCommand() {
        return resultOfLastCommand.get(0);
    }

    public ArrayList<String> getCurrentBoard() {
        sendCommand("show board", 11);
        return resultOfLastCommand;
    }

    public ChessEngine() {
        resultOfLastCommand = new ArrayList<String>();
        logger.debug("Starting new chess engine process.");
        ProcessBuilder pb = new ProcessBuilder()
        .command("/usr/local/bin/gnuchess", "--manual", "--xboard")
        .redirectErrorStream(true);
        try
        {
            engineProcess = pb.start();
            readInputStream = engineProcess.getInputStream();
            OutputStream outputStream = engineProcess.getOutputStream();
            reader = new BufferedReader (new InputStreamReader(readInputStream));
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            waitForResponse(1);
        }
        catch (java.io.IOException ioe) {
            System.out.print(ioe.getMessage());
        }
    }

    public void close() {
        logger.debug("Shutting down engine.");
        try {
            engineProcess.destroy();
        }
        catch (NullPointerException npe) {

        }
    }

    public void sendCommand(String command) {
        sendCommand(command, 1);
    }

    public void sendCommand(String command, Integer responseLines) {
        logger.debug("Sending command: " + command);
        try {
            writer.write(command + "\n");
            writer.flush();
            writer.flush();
        }
        catch (java.io.IOException ioe) {
            System.out.print(ioe.getMessage());
        }
        waitForResponse(responseLines);
    }

    private boolean waitForResponse(Integer lines) {
        resultOfLastCommand.clear();
        try {
            String nextLine="uninitialized";
            while (true) {

                if ((nextLine = reader.readLine()) != null) {
                    // successful read!
                    resultOfLastCommand.add(nextLine);
                    if (0 >= --lines) {
                        return true;
                    }
                }
                giveEngineProcessingTime();
            }
        }
        catch (IOException ioe) {
            System.out.print(ioe.getMessage());
            return false;
        }
    }

    private void giveEngineProcessingTime(){
        try {
            sleep(50);
        }
        catch (InterruptedException e) {

        }
    }
}