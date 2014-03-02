import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by hsbacot on 2/12/14.
 */
public class Game {


    public static void main(String[] args) {
        Board testBoard = new Board();
        String word, direction;
        int col, row, numOfPlayers;
        Scanner scanner = new Scanner(System.in);
        int counter = 0;

        //Player player1 = new Player(); //placeholder

        System.out.println("How many players?");
        numOfPlayers = scanner.nextInt();
        testBoard.createPlayers(numOfPlayers);
        for (int i = 0; i < numOfPlayers; i++) {
            testBoard.giveTiles(testBoard.playerList.get(i));
        }

        Move fakeMove = new Move("", 16, 16, "", testBoard);
        fakeMove.render();


        while (counter < 10) {

            testBoard.giveTiles(testBoard.currentPlayer);

            System.out.println("\n" + testBoard.currentPlayer.getName());
            System.out.println(testBoard.currentPlayer.tilePrint());

            System.out.println("What word would you like to play?");
            word = scanner.next().toLowerCase();


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
//            newMove.detectWords();
//            newMove.oldWords = newMove.words;

            // detect if more words are created on play

            // score words
            if (newMove.legalMove()){
                int score = newMove.placeWord();
                //following deals with "player1" TODO add currentPlayer
                Player player = testBoard.getCurrentPlayer();
                player.addToScore(score);
                //newMove.detectWords();
                newMove.render();
                newMove.depleteHand(); //only do if legal move and do after everything else.
                // Also Need to update score
            }
            testBoard.showScore();
            testBoard.nextPlayer();

            // game ends after 10 turns currently
            counter++;
        }
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
