package net.richardmarston.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rich on 27/03/15.
 */
public class BoardSamples {

        public static ArrayList<String> getStartingBoard () {
            return new ArrayList<String>(Arrays.asList(
                "",
                "white  KQkq",
                "r n b q k b n r ",
                "p p p p p p p p ",
                ". . . . . . . . ",
                ". . . . . . . . ",
                ". . . . . . . . ",
                ". . . . . . . . ",
                "P P P P P P P P ",
                "R N B Q K B N R ",
                ""
            ));
        }

}

