import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by hsbacot on 2/12/14.
 */
public class Board {
    private HashSet dictionary = new HashSet();
    private ArrayList<Character> letterBag = new ArrayList<Character>();
    public ArrayList<Player> playerList = new ArrayList<Player>();
    public Player currentPlayer = new Player();

    Space[][] spaces = new Space[15][15];

    // initialize hashset to hold words from words file
//    HashSet dictionary = new HashSet();
    public Board() {
//        populateDictionary();
        setSpaces();
        setLetterMultipliers();
        setWordMultipliers();
        setLetterBag();
//        setScoreGuide();
        Collections.shuffle(this.letterBag);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void nextPlayer() {
        int ind = playerList.indexOf(currentPlayer);
        if (ind + 1 < playerList.size()) {
            ind = ind +1;
        }
        else {
            ind = 0;
        }
        this.setCurrentPlayer(playerList.get(ind));
    }

    public void showScore() {
        for(int i = 0; i < playerList.size(); i++) {
            System.out.println(playerList.get(i).getName() + " " + playerList.get(i).getScore());
        }
    }

    public void setSpaces() {
        for(int row = 0; row < 15; row++) {
            for(int col = 0; col < 15; col++) {
                this.spaces[row][col] = new Space();
            }
        }
    }

    void createPlayers(int players) {
        String playerName;
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < players; i++){
            System.out.println("What is player " + (i+1) + "'s name?");
            playerName = scanner.next();
            Player player = new Player(playerName);
            playerList.add(player);
        }
        this.setCurrentPlayer(playerList.get(0));
    }

    public void setAllLetterMultipliers(int row, int col, int multiplier) {
        this.spaces[row][col].setLetterMultiplier(multiplier);
        this.spaces[14 - row][col].setLetterMultiplier(multiplier);
        this.spaces[row][14 - col].setLetterMultiplier(multiplier);
        this.spaces[14 - row][14 - col].setLetterMultiplier(multiplier);
        this.spaces[col][row].setLetterMultiplier(multiplier);
        this.spaces[14 - col][row].setLetterMultiplier(multiplier);
        this.spaces[col][14 - row].setLetterMultiplier(multiplier);
        this.spaces[14 - col][14 - row].setLetterMultiplier(multiplier);
    }

    public void setAllWordMultipliers(int row, int col, int multiplier) {
        this.spaces[row][col].setWordMultiplier(multiplier);
        this.spaces[14 - row][col].setWordMultiplier(multiplier);
        this.spaces[row][14 - col].setWordMultiplier(multiplier);
        this.spaces[14 - row][14 - col].setWordMultiplier(multiplier);
        this.spaces[col][row].setWordMultiplier(multiplier);
        this.spaces[14 - col][row].setWordMultiplier(multiplier);
        this.spaces[col][14 - row].setWordMultiplier(multiplier);
        this.spaces[14 - col][14 - row].setWordMultiplier(multiplier);
    }

    public void setLetterMultipliers() {
        // doubles
        setAllLetterMultipliers(0, 3, 2);
        setAllLetterMultipliers(2, 6, 2);
        setAllLetterMultipliers(6, 6, 2);
        setAllLetterMultipliers(7, 3, 2);

        // triples
        setAllLetterMultipliers(5, 1, 3);
        setAllLetterMultipliers(5, 5, 3);
    }

    public void setWordMultipliers() {
        // doubles
        setAllWordMultipliers(1, 1, 2);
        setAllWordMultipliers(2, 2, 2);
        setAllWordMultipliers(3, 3, 2);
        setAllWordMultipliers(4, 4, 2);
        setAllWordMultipliers(7, 7, 2);

        // triples
        setAllWordMultipliers(0, 0, 3);
        setAllWordMultipliers(0, 7, 3);
    }

    public void setLetterBag() {
        setMultipleLetters('E', 12);
        setMultipleLetters('A', 9);
        setMultipleLetters('I', 9);
        setMultipleLetters('O', 8);
        setMultipleLetters('N', 6);
        setMultipleLetters('R', 6);
        setMultipleLetters('T', 6);
        setMultipleLetters('L', 4);
        setMultipleLetters('S', 4);
        setMultipleLetters('U', 4);
        setMultipleLetters('D', 4);
        setMultipleLetters('G', 3);
        setMultipleLetters('B', 2);
        setMultipleLetters('C', 2);
        setMultipleLetters('M', 2);
        setMultipleLetters('P', 2);
        setMultipleLetters('F', 2);
        setMultipleLetters('H', 2);
        setMultipleLetters('V', 2);
        setMultipleLetters('W', 2);
        setMultipleLetters('Y', 2);
        setMultipleLetters('K', 1);
        setMultipleLetters('J', 1);
        setMultipleLetters('X', 1);
        setMultipleLetters('Q', 1);
        setMultipleLetters('Z', 1);
    }

    public void setMultipleLetters(Character letter, int times) {
        for (int i = 0; i < times; i++){
            this.letterBag.add(letter);
        }
    }
    public boolean isOccupied(int row, int column){
       if(spaces[row][column].getLetter() == '\u0000') {
           return false;
       }
            return true;
    };


    // render the information stored in each space
    public String[] spaceInfo() {
        String[] info = new String[]{};

        return info;
    }

    public void giveTiles(Player player){
        int diff;
        Character tile;
        diff = 7 - player.getHand().size();
        for (int i = 0; i < diff; i++) {
           if (this.letterBag.size() > 0) {
            tile = this.letterBag.remove(0);
            player.addTileToHand(tile);
           } else {
               // out of tiles
               System.out.println("The bag is empty");
           }
        }
    }

    //print whole board
//    public void render() {
//        for(int row = 0; row < 15; row++) {
//            for(int col = 0; col < 15; col++) {
//                System.out.print(" ");
//                System.out.print(this.spaces[row][col].getSpaceValue());
//                System.out.print(" ");
//            }
//            System.out.println();
//        }
//    }
//    public void placeLetter(char letter, int row, int col) {
//        this.spaces[row][col].setLetter(letter);
//    }

//    public int placeWord(String word, int row, int col, String direction) {
//        // score returned to account for multipliers
//        int wordVal = 0;
//
//        if (direction.equals("down")) {
//            //This checks that downward words aren't off the board
//            // Checks that all words are greater than 1 character in length
//            if (word.length() + row < 15 && checkWord(word) && word.length() > 1) {
//                //paint
//                char[] letterArr = word.toCharArray();
//                for(int i = 0; i < word.length(); i++){
//                    placeLetter(letterArr[i], row + i, col);
//                    wordVal += this.scoreGuide.get(letterArr[i]) * this.spaces[row + i][col].getLetterMultiplier();
//                }
//            }
//            else {
//                //error. redo move?
//            }
//        }
//        else {
//            //rightward word check
//            //This checks that downward words aren't off the board
//            // Checks that all words are greater than 1 character in length
//            if (word.length() + row < 15 && checkWord(word) && word.length() > 1) {
//                //paint
//                char[] letterArr = word.toCharArray();
//                for(int i = 0; i < word.length(); i++){
//                    placeLetter(letterArr[i], row, col + i);
//                    wordVal += this.scoreGuide.get(letterArr[i]);
//                }
//            }
//            else {
//                //error. redo move?
//            }
//        }
//
//        // do word multiplier if exists
//        return wordVal;
//    }

//    public int scoreWord(String word) {
//        int wordVal = 0;
//
//        char[] letterArr = word.toCharArray();
//        for(int i = 0; i < word.length(); i++) {
//            wordVal += this.scoreGuide.get(letterArr[i]);
//        }
//
//        return wordVal;
//    }

//    public boolean checkWord(String word){
//        if(this.dictionary.contains(word)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    // Add words to dictionary hashset
//    public void populateDictionary() {
//        // Load words file by line
//        try {
//            BufferedReader in = new BufferedReader(new FileReader("words"));
//            String line = null;
//            while((line = in.readLine()) != null) {
//                this.dictionary.add(in.readLine());
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
