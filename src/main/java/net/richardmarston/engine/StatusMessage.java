package net.richardmarston.engine;

import java.util.ArrayList;

/**
 * Created by rich on 27/03/15.
 */
public class StatusMessage {

    public StatusMessage() {
        textLines = new ArrayList<String>();
    }
    public ArrayList<String> getTextLines() {
        return textLines;
    }

    public void addTextLine(String textLine) {
        textLines.add(textLine);
    }

    ArrayList<String> textLines;

    public Boolean invalidMove() {
        return textLines.get(0).contains("Invalid");
    }

    public Boolean whiteMate() {
        return textLines.get(0).matches(".*White mates.*");
    }

    public Boolean blackMate() {
        return textLines.get(0).matches(".*Black mates.*");
    }

    public Boolean OK() {
        return textLines.get(0).matches("\\d\\..*");
    }

    public Boolean boardUpdate() {
        return isBoardUpdate(textLines);
    }

    public static Boolean isBoardUpdate(ArrayList<String> lines) {
        Boolean result = (lines.get(0).contains("") &&
                        lines.size() > 1 &&
                        (lines.get(1).contains("white  KQkq") ||
                        lines.get(1).contains("black  KQkq")));
        System.out.println("RESULT is " + result);
        return result;
    }
}
