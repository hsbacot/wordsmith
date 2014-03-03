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

        System.out.println("How many players?");
        numOfPlayers = scanner.nextInt();
        testBoard.createPlayers(numOfPlayers);
        for (int i = 0; i < numOfPlayers; i++) {
            testBoard.giveTiles(testBoard.playerList.get(i));
        }
//
//        Move fakeMove = new Move("", 16, 16, "", testBoard);
//        fakeMove.render();


        while (testBoard.tilesLeft()) {

            testBoard.giveTiles(testBoard.currentPlayer);

            for (int i = 0; i < testBoard.playerList.size(); i++) {
                Player player = new Player();
                player = testBoard.playerList.get(i);
                System.out.print(player.getName());
                if (player == testBoard.currentPlayer)  {
                    System.out.print("(Your turn)");
                }
                System.out.print("  ");
            }
            System.out.print("\n");


            System.out.println("Tiles: " + testBoard.currentPlayer.tilePrint());

            Move displayMove = new Move("", 16, 16, "", testBoard);


            displayMove.clearScreen();
            displayMove.render();
            System.out.print("word: ");
            word = scanner.next().toLowerCase();

            displayMove.clearScreen();
            displayMove.render();
            System.out.print("word: " + word + " row: ");
            row = (scanner.nextInt()-1);

            displayMove.clearScreen();
            displayMove.render();
            System.out.print("word: " + word + " row: " + row + " col: ");
            col = (scanner.nextInt()-1);

            displayMove.clearScreen();
            displayMove.render();
            System.out.print("word: " + word + " row: " + row + " col: " + col + " down or right: ");
            direction = scanner.next();

            Move newMove = new Move(word, row, col, direction, testBoard);

            // score words
            if (newMove.legalMove()){
                Board oldBoard = newMove.getBoard();
                int oldScore = testBoard.currentPlayer.getScore();
                int score = newMove.placeWord();
                // collisions must be ran after word is placed
                // check validity of collisions
                if (!newMove.isCollisionValid()) {
                    newMove.setBoard(oldBoard);
                    score = oldScore;
                }
                Player player = testBoard.getCurrentPlayer();
                player.addToScore(score);
                //newMove.detectWords();
                newMove.clearScreen();
                newMove.render();
                newMove.depleteHand(); //only do if legal move and do after everything else.
                // Also Need to update score
                System.out.println("counter " + counter);
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
