/**
 * Created by hsbacot on 2/12/14.
 */
public class Game {

    public static void main(String[] args) {
        Board testBoard = new Board();
        testBoard.setSpaces();

        testBoard.placeWord("G", 5,5, "down");
        testBoard.placeWord("HELLO", 0,8, "down");
        testBoard.render();
    }

}
