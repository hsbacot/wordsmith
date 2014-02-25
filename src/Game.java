import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by hsbacot on 2/12/14.
 */
public class Game {

    public static void main(String[] args) {
        Board testBoard = new Board();
        String word, direction;
        int col, row;
        Scanner scanner = new Scanner(System.in);


        System.out.println("What word would you like to play?");
        word = scanner.next();

        System.out.println("What row would you like to play in?");
        row = scanner.nextInt();

        System.out.println("What column would you like to play in?");
        col = scanner.nextInt();

        System.out.println("In what direction would you like to play? (down/right)");
        direction = scanner.next();

//        testBoard.placeWord("he", 5,5, direction);
//        testBoard.placeWord(word, row, col, direction);
        Move newMove = new Move(word, row, col, direction, testBoard);
        // make sure all board is valid, if not throw error
        newMove.detectWords();
        newMove.oldWords = newMove.words;
        newMove.placeWord();
        newMove.detectWords();

        // detect if more words are created on play

        // score words
        newMove.render();
    }

    // Add words to dictionary hashset
    public static HashSet<String> populateDictionary(HashSet<String> dictionary) {
        // Load words file by line
        try {
            BufferedReader in = new BufferedReader(new FileReader("words"));
            String line = null;
            while((line = in.readLine()) != null) {
                dictionary.add(in.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }


}
