package net.richardmarston.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @startuml
 * class model.Board {
 *     -ArrayList<ArrayList<String>> board
 *     +setState(ArrayList<String>)
 *     +getBoardAsStrings()
 * }
 * @enduml
 * Created by rich on 25/03/15.
 */
public class Board {

    static Logger logger = Logger.getLogger(Board.class);
    ArrayList<ArrayList<String>> board;

    public ArrayList<ArrayList<String>> getBoardAsStrings() {
        return board;
    }

    public Board() {

        board = new ArrayList<ArrayList<String>>();
        IntStream.range(0, 8).forEach(
	        nbr -> board.add(new ArrayList<String>())
        );
    }

    /*
                "",
                "white  KQkq",
                "r n b q k b n r ",
                "p p p p p p p p ",
                ". . . . . . . . ",
                ". . . . . . . . ",
                ". . . . . . . . ",
                ". . . . . . . . ",
                "P P P P P P P P ",
                "R N B Q K B N R "
     */
    public void setState(ArrayList<String> newBoard) {
        Iterator<ArrayList<String>> iterator = board.iterator();
        List<String> toProcess = newBoard.subList(2,10);
        try {
            // todo: current player needs to be extracted from second field of newBoard
            // todo: castling rights are also held in the KQkq part.
            toProcess.forEach(line -> {
                ArrayList<String> boardRow = iterator.next();
                boardRow.clear();
                IntStream.range(0, 8).forEach(
                        nbr -> {
                            boardRow.add(line.substring((2 * nbr), (2 * nbr)+1));
                        }
                );
            });
        }
        catch (StringIndexOutOfBoundsException sioobe) {
            logger.error("Failed to setState from board arrayList: "+newBoard);
        }
    }
}
