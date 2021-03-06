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


            Move displayMove = new Move("", 16, 16, "", testBoard);

            displayMove.clearScreen();


            System.out.print("\n");


            System.out.println("Tiles: " + testBoard.currentPlayer.tilePrint());


            displayMove.render();
            System.out.print("word: ");
            word = scanner.next().toLowerCase();

            displayMove.clearScreen();
            displayMove.render();
            System.out.print("word: " + word + "   row: ");
            row = (scanner.nextInt()-1);

            displayMove.clearScreen();
            displayMove.render();
            System.out.print("word: " + word + "   row: " + row + "   col: ");
            col = (scanner.nextInt()-1);

            displayMove.clearScreen();
            displayMove.render();
            System.out.print("word: " + word + "   row: " + row + "   col: " + col + "   down or right: ");
            direction = scanner.next();

            Move newMove = new Move(word, row, col, direction, testBoard);

            // score words
            if (newMove.legalMove()){
                ArrayList<Space> oldSpaces = new ArrayList<Space>();
                for (int i = 0; i < newMove.moveSpaces.size(); i++) {
                    int spaceRow = newMove.moveSpaces.get(i).get(0);
                    int spaceCol = newMove.moveSpaces.get(i).get(1);
                    Space holder = newMove.board.spaces[spaceRow][spaceCol];
                    Space space = new Space();
                    space.setLetter(holder.getLetter());
                    space.setLetterMultiplier(holder.getLetterMultiplier());
                    space.setWordMultiplier(holder.getWordMultiplier());
                    oldSpaces.add(space);
                }
                int oldScore = testBoard.currentPlayer.getScore();
                int score = newMove.placeWord();
                // collisions must be ran after word is placed
                // check validity of collisions
                if (!newMove.isCollisionValid()) {
                    // reset space values
                    for (int i = 0; i < newMove.moveSpaces.size(); i++) {
                        int spaceRow = newMove.moveSpaces.get(i).get(0);
                        int spaceCol = newMove.moveSpaces.get(i).get(1);
                        Space space = newMove.board.spaces[spaceRow][spaceCol];
                        Space oldSpace = oldSpaces.get(i);
                        space.setLetter(oldSpace.getLetter());
                        space.setLetterMultiplier(oldSpace.getLetterMultiplier());
                        space.setWordMultiplier(oldSpace.getWordMultiplier());
                    }
                    score = 0;
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
//            testBoard.showScore();
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
