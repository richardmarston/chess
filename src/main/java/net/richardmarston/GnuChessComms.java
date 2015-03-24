package net.richardmarston;

import java.io.*;

/**
 * Created by rich on 23/03/15.
 */
public class GnuChessComms {

    public String getResultOfLastCommand() {
        return resultOfLastCommand;
    }

    String resultOfLastCommand;
    BufferedReader reader = null;
    BufferedWriter writer = null;

    public GnuChessComms() {
        ProcessBuilder pb = new ProcessBuilder()
        .command("/usr/games/gnuchess", "--manual", "--xboard")
        .redirectErrorStream(true);
        try {
            Process p = pb.start();
            InputStream chessIn = p.getInputStream();
            OutputStream chessOut = p.getOutputStream();
            BufferedReader reader = new BufferedReader (new InputStreamReader(chessIn));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(chessOut));

            while(!(resultOfLastCommand = reader.readLine()).equals("Chess")) {}
        }
        catch (java.io.IOException ioe) {
            System.out.print(ioe.getMessage());
        }
    }
}