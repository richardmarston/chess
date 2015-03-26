package net.richardmarston.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by rich on 25/03/15.
 */
public class Board {

    ArrayList<ArrayList<String>> board;

    public ArrayList<ArrayList<String>> getBoardAsStrings() {
        return board;
    }

    public Board() {

        board = new ArrayList<ArrayList<String>>();
        IntStream.range(0, 8).forEach(
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

}
