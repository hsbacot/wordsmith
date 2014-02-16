import java.io.*;
import java.util.HashSet;

/**
 * Created by hsbacot on 2/12/14.
 */
public class Game {

    public static void main(String[] args) {
        Board testBoard = new Board();


        testBoard.placeWord("G", 5,5, "down");
        testBoard.placeWord("he", 3,8, "down");
        testBoard.render();
    }

    // Add words to dictionary hashset
    public static HashSet populateDictionary(HashSet dictionary) {
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
