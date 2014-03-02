/**
 * Created by grangerabuhoff on 3/1/14.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
public class word_test {

    public static void main(String[] args) {
    Board testBoard = new Board();
    String word = "ten";
    int row = 7;
    int col = 7;
    String direction = "down";
    Move newMove = new Move(word, row, col, direction, testBoard);
    int score = newMove.placeWord();

    newMove.render();
    }
}
