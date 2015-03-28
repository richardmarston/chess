package net.richardmarston.engine;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedDeque;

import static java.lang.Thread.sleep;

/**
 * @startuml
 * class engine.EngineIO {
 *     BufferedReader reader
 *     BufferedWriter writer
 *     Process engineProcess
 *     ConcurrentLinkedDeque<engine.StatusMessage>
 * }
 * @enduml
 * Created by rich on 27/03/15.
 */
public class EngineIO implements Runnable {

    static Logger logger = Logger.getLogger(EngineIO.class);

    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    private Process engineProcess = null;
    private ConcurrentLinkedDeque<StatusMessage> replies;
    private Boolean processComplete = false;

    public EngineIO(){
        replies = new ConcurrentLinkedDeque<StatusMessage>();
        logger.debug("Starting new chess engine process.");
        ProcessBuilder pb = new ProcessBuilder()
                .command("/usr/local/bin/gnuchess", "--xboard")
                .redirectErrorStream(true);
        try
        {
            engineProcess = pb.start();
            InputStream readInputStream = engineProcess.getInputStream();
            OutputStream outputStream = engineProcess.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(readInputStream));
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        }
        catch (java.io.IOException ioe) {
            System.out.print(ioe.getMessage());
        }
    }

    public void getCurrentBoard() {
        sendCommand("show board", 11);
    }

    public void sendCommand(String command) {
        sendCommand(command, 1);
    }

    public void sendCommand(String command, Integer responseLines) {
        logger.info("Sending command: " + command);
        try {
            writer.write(command + "\n");
            writer.flush();
            writer.flush();
        }
        catch (java.io.IOException ioe) {
            System.out.print(ioe.getMessage());
        }
    }

    private void readLinesFromEngine(StatusMessage message, Integer expectedLines) {
        String nextLine;
        try {
            while (!Thread.currentThread().isInterrupted()  && !processComplete) {
                if ((nextLine = reader.readLine()) != null) {
                    // successful read!
                    message.addTextLine(nextLine);
                    logger.info(nextLine);
                    if(0 >= --expectedLines) {
                        return;
                    }
                }
            }
        }
        catch (IOException ioe) {
            System.out.print(ioe.getMessage());
        }
    }

    public void close() {
        logger.debug("Shutting down engine.");
        try {
            engineProcess.destroy();
        }
        catch (NullPointerException npe) {
            logger.debug("OH NO, IT'S A NULL POINTER!");
        }
    }

    private synchronized void addToDeque(StatusMessage message) {
        replies.addLast(message);
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted() && !processComplete) {
            StatusMessage message = new StatusMessage();
            readLinesFromEngine(message, 1);
            if (message != null && message.isBeginningOfBoardUpdate()) {
                readLinesFromEngine(message, 10);
            }
            addToDeque(message);
        }
        logger.info("Read thread is exiting because - isInterrupted: ("+
                Thread.currentThread().isInterrupted()+
                ") processComplete: ("+processComplete+")");
    }

    public synchronized StatusMessage getNextReply() {
        StatusMessage reply;
        try {
            reply = replies.removeFirst();
        }
        catch (NoSuchElementException nsee) {
            reply = null;
        }
        return reply;
    }
}
