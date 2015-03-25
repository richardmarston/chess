package net.richardmarston;

import java.io.*;

import static java.lang.Thread.sleep;

/**
 * Created by rich on 23/03/15.
 */
public class ChessEngineComms {

    public static final int TIMEOUT = 3000;
    public String getResultOfLastCommand() {
        return resultOfLastCommand;
    }
    String resultOfLastCommand;
    InputStream readInputStream = null;
    BufferedReader reader = null;
    BufferedWriter writer = null;
    Process p = null;

    public ChessEngineComms() {
        ProcessBuilder pb = new ProcessBuilder()
        .command("/usr/local/bin/gnuchess", "--manual", "--xboard")
        .redirectErrorStream(true);
        try
        {
            p = pb.start();
            readInputStream = p.getInputStream();
            OutputStream outputStream = p.getOutputStream();
            reader = new BufferedReader (new InputStreamReader(readInputStream));
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        }
        catch (java.io.IOException ioe) {
            System.out.print(ioe.getMessage());
        }
        finally {
        }
    }

    public void close() {
        p.destroy();
    }

    public void sendCommand(String command) {
        try {
            writer.write(command + "\n");
            writer.flush();
        }
        catch (java.io.IOException ioe) {
            System.out.print(ioe.getMessage());
        }
        contextSwitch();
        waitForResponse(3000);
    }

    public boolean waitForResponse(long timeoutInMilliseconds) {
        try {
            long now, then;
            now = then = System.currentTimeMillis();
            while (now - then < timeoutInMilliseconds) {
                if ((readInputStream.available() > 0) && ((resultOfLastCommand = reader.readLine()) != null)) {
                    // successful read!
                    return true;
                }
                now = System.currentTimeMillis();
            }
            return false;
        }
        catch (IOException ioe) {
            System.out.print(ioe.getMessage());
            return false;
        }
    }

    private void contextSwitch(){
        try {
            sleep(0);
        }
        catch (InterruptedException e) {

        }
    }
}