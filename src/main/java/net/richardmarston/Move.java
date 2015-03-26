package net.richardmarston;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by rich on 25/03/15.
 */
public class Move {

    private String command;
    ArrayList<ArrayList<String>> board;

    public ArrayList<ArrayList<String>> getBoard() {
        return board;
    }

    public Move() {

        board = new ArrayList<ArrayList<String>>();
        IntStream.range(0, 7).forEach(
	        nbr -> board.add(new ArrayList<String>())
        );

        board.forEach(list->{
            list.add(new String("a"));
            list.add(new String("b"));
            list.add(new String("a"));
            list.add(new String("c"));
            list.add(new String("a"));
            list.add(new String("a"));
            list.add(new String("a"));
            list.add(new String("a"));
        });
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
