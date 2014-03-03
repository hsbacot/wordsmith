import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.*;

/**
 * Created by hsbacot on 2/24/14.
 */
public class Move {
    public HashSet<String> dictionary = new HashSet();
    public ArrayList<String> words = new ArrayList<String>();
    public ArrayList<String> oldWords = new ArrayList<String>();
    public ArrayList<String> wordsPerTurn;
    private String word;
    private int row;
    private int col;
    private String direction;
    private Space[][] spaces;
    private HashMap<Character, Integer> scoreGuide = new HashMap<Character, Integer>();



    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board board;
    //added spacesPlayed to keep track of spaces whose bonuses should be set to 1 by removeBonuses
    private ArrayList<Space> spacesPlayed = new ArrayList<Space>();
    // Added to store space in move to track collision and neighbors
    ArrayList<ArrayList<Integer>> moveSpaces = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> neighborSpaces = new ArrayList<ArrayList<Integer>>();
    private ArrayList<String> collisionWords = new ArrayList<String>();
    private boolean collision = false;
    ArrayList<ArrayList<Integer>> spacesWithTiles = new ArrayList<ArrayList<Integer>>();

    public Move(String word, int row, int col, String direction, Board board){
        this.word = word;
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.spaces = board.spaces;
        this.board = board;

        setScoreGuide();
        populateDictionary();
    }

    public boolean moveMatchesHand() {
        char[] letterArr = this.word.toCharArray();
        ArrayList<Character> wordAR = new ArrayList<Character>();
        HashMap<Character, Integer> playLetterCount = new HashMap<Character, Integer>();

        for (int i = 0; i < word.length(); i++) {
            wordAR.add(word.charAt(i));
        }

//        ArrayList<Character> charPlayed = new ArrayList<Character>();



//        wordAR = this.board.currentPlayer.getHand();
//        for (int i = 0; i < charPlayed.size(); i++) {
//            if ( wordAR.contains(charPlayed.get(i))) {
//                int ind = wordAR.indexOf(charPlayed.get(i));
//                wordAR.remove(ind);
//            }
//        }

//        this.board.currentPlayer.setHand(wordAR);

     return true;
    }

    public void depleteHand() {
        ArrayList<Character> handAR = this.board.currentPlayer.getHand();
        char[] charPlayed = this.word.toCharArray();
        for (int i = 0; i < charPlayed.length; i++) {
            if ( handAR.contains(charPlayed[i])) {
                int ind = handAR.indexOf(charPlayed[i]);
                handAR.remove(ind);
            }
        }

        this.board.currentPlayer.setHand(handAR);
    }

    public int amountInArrayList(char letter, ArrayList<Character> list) {
        int counter = 0;

        for (int i = 0; i < list.size(); i++){
            Character listletter = list.get(i);
            if (Character.getNumericValue(listletter) == Character.getNumericValue(letter)) {
                counter++;
            }
        }

        return counter;
    }

    public int[][] neighborPositions() {
        int[][] positions = {{0,0},{0,0},{0,0},{0,0}};
        // top
        positions[0][0] = this.row - 1;
        positions[0][1] = this.col;

        // bottom
        positions[1][0] = this.row + 1;
        positions[1][1] = this.col;

        // right
        positions[0][0] = this.row;
        positions[0][1] = this.col + 1;

        // left
        positions[0][0] = this.row ;
        positions[0][1] = this.col - 1;
        return positions;
    }

    public void placeLetter(char letter, int rowPlace, int colPlace) {
        this.spaces[rowPlace][colPlace].setLetter(letter);
    }

    public boolean legalMove() {
        // does the user have the tiles needed to play the word?
        // iterate through each space and check hand for letter
        // if the letter is in hand, keep going
        // if not check the coordinate on the board for the letter
        // in the coordinate on the board has the letter needed, keep going
        // else return false
        // get the list of neighbor coordinates
        //
        if (moveMatchesHand() && collision()) {

            return true;
        } else {
            return false;
        }
    }

    public boolean isCollisionValid() {
        boolean verdict = true;
        getCollisionWords();
        // points adder
        if (!hasNeighbor()) {
            verdict = false;
        }
        int secondaryPoints = 0;
        // check the validity of each secondary word
        for (int i = 0; i < this.collisionWords.size(); i++) {
            // return false if all words aren't real
            if (!checkWord(this.collisionWords.get(i))) {
                verdict = false;
            }
            secondaryPoints =+ scoreSecondaryWord(this.collisionWords.get(i));
        }
        // adds secondary points to players score
        this.board.currentPlayer.setScore(this.board.currentPlayer.score + secondaryPoints);
        return verdict;
    }

    // does the user have the tiles needed to play the word?
    // does the board have the needed letter in place if the user doesn't have it?
    // iterate through each space and check hand for letter
    // if the letter is in hand, keep going
    // if not check the coordinate on the board for the letter
    // in the coordinate on the board has the letter needed, keep going
    // else return false
    public boolean collision() {
        int wordLength = this.word.length();
        boolean verdict = true;

        // adds coordinates of word to be played to a list
        if (this.direction.equals("down")) {
            for(int i = 0; i < wordLength; i++) {
                ArrayList<Integer> currentCoords = new ArrayList<Integer>();
                // row is first coordinate
                // col is second
                Integer spaceRow  = new Integer(this.row + i);
                Integer spaceCol = new Integer(this.col);
                currentCoords.add(spaceRow);
                currentCoords.add(spaceCol);

                this.moveSpaces.add(currentCoords);
            }
        }
        else {
            for(int i = 0; i < wordLength; i++) {
                ArrayList<Integer> currentCoords = new ArrayList<Integer>();
                // row is first coordinate
                // col is second
                Integer spaceRow  = new Integer(this.row);
                Integer spaceCol = new Integer(this.col + i);
                currentCoords.add(spaceRow);
                currentCoords.add(spaceCol);

                this.moveSpaces.add(currentCoords);
            }
        }

        // checking if board has the character needed to play the word
        // if not, check to see if the player has the character as a tile
        for (int i = 0; i < this.moveSpaces.size(); i++) {
            int spaceRow = this.moveSpaces.get(i).get(0);
            int spaceCol = this.moveSpaces.get(i).get(1);
            char[] wordCharacters = this.word.toCharArray();
            Character letterInWord = wordCharacters[i];

            Character letterAtSpace = this.spaces[spaceRow][spaceCol].getLetter();
            if (letterAtSpace == letterInWord) {
//                System.out.println("Valid play, letter needed is in space");
                verdict = true;
                this.collision = true;
                // set object to null but don't remove to keep indexing
                // in row means collision
                this.moveSpaces.get(i).set(0, 20);
            } else if (this.board.currentPlayer.getHand().contains(letterInWord)) {
//                System.out.println("Valid play, letter needed is in player hand");
                verdict = true;
            } else {
                System.out.println("Invalid Play");
                verdict = false;
            }
        }
        // populates neighbor space list
        addNeighborSpaces();

        if (verdict){
            return true;
        } else {
            return false;
        }
    }

    public void getCollisionWords() {
        // checks for characters on board spaces
        for (int i = 0; i < this.neighborSpaces.size(); i++) {
            int spaceRow = this.neighborSpaces.get(i).get(0);
            int spaceCol = this.neighborSpaces.get(i).get(1);
            // if the space has a letter and is not empty
            if (this.spaces[spaceRow][spaceCol].getLetter() != '\u0000') {
                // characters of word formed
                ArrayList<Character> colWordChars = new ArrayList<Character>();
                this.spacesWithTiles.add(this.neighborSpaces.get(i));
                // if tile is above or below word
                // checks if row column is different th
                int neighborRow = this.neighborSpaces.get(i).get(0);
                int neighborCol = this.neighborSpaces.get(i).get(1);
                // add letter value of neighbor to colWordsChars
                colWordChars.add(this.spaces[neighborRow][neighborCol].getLetter());
                // do if word is to the left or right of neighbor
                if (!isNeighborVertical(neighborCol)) {
                    // build left & right words
                    // build to the left
                    int leftCol = neighborCol - 1;
                    int rightCol = neighborCol + 1;
                    while (this.spaces[neighborRow][leftCol].getLetter() != '\u0000') {
                        colWordChars.add(0, this.spaces[neighborRow][leftCol].getLetter());
                        leftCol--;
                    }
                    // build to the right
                    while (this.spaces[neighborRow][rightCol].getLetter() != '\u0000') {
                        colWordChars.add(this.spaces[neighborRow][rightCol].getLetter());
                        rightCol++;
                    }
                    String collisionWord = characterListToString(colWordChars);
                    this.collisionWords.add(collisionWord);




                } else {
                    // build top and bottom words
                    // build to the top
                    int topRow = neighborRow - 1;
                    int bottomRow = neighborRow + 1;
                    // build to the top
                    while (this.spaces[topRow][neighborCol].getLetter() != '\u0000') {
                        colWordChars.add(0, this.spaces[topRow][neighborCol].getLetter());
                        topRow--;
                    }
                    // build to the bottom
                    while (this.spaces[bottomRow][neighborCol].getLetter() != '\u0000') {
                        colWordChars.add(this.spaces[bottomRow][neighborCol].getLetter());
                        bottomRow++;
                    }
                    String collisionWord = characterListToString(colWordChars);
                    this.collisionWords.add(collisionWord);
                }
            }
        }

    }

    public boolean hasNeighbor() {
        boolean verdict = false;
        if (this.spacesWithTiles.size() > 0 || this.collision) {
            verdict = true;
        }
        if (this.direction.equals("down") && this.row <= 7 && (this.row + this.word.length() >= 7) && this.col == 7){
            verdict = true;
        } else if (!this.direction.equals("down") && this.col <= 7 && (this.col + this.word.length() >= 7) && this.row == 7) {
            verdict = true;
        }

        return verdict;
    }

    // helper to detect neighbor relativity
    public boolean isNeighborVertical(int neighborCol) {
        for (int i = 0; i < this.moveSpaces.size(); i++) {
            if (this.moveSpaces.get(i).get(1).equals(neighborCol)) {
                return true;
            }
        }
        return false;
    }

    // helper method to turn character lists to stings
    public String characterListToString(ArrayList<Character> charList) {
        StringBuilder charWord = new StringBuilder(charList.size());
        for(Character ch: charList)
        {
            charWord.append(ch);
        }
        return charWord.toString();
    }

    // must be run after collision(), where moveSpace is populated
    public void addNeighborSpaces() {
        for (int i = 0; i < this.moveSpaces.size(); i++) {
            // get row and column of each moveSpaces element
            // if element is not null
            if (this.moveSpaces.get(i).get(0).equals(20)) {
                break;
            }
            int spaceRow = this.moveSpaces.get(i).get(0);
            int spaceCol = this.moveSpaces.get(i).get(1);

            if (this.direction.equals("down")) {
                ArrayList<Integer> leftCoords = new ArrayList<Integer>();
                ArrayList<Integer> rightCoords = new ArrayList<Integer>();
                // position to the left
                leftCoords.add(spaceRow);
                leftCoords.add(spaceCol- 1);
                this.neighborSpaces.add(leftCoords);

                // position to the right
                rightCoords.add(spaceRow);
                rightCoords.add(spaceCol + 1);
                this.neighborSpaces.add(rightCoords);

                // position top
                if (i == 0) {
                    ArrayList<Integer> topCoords = new ArrayList<Integer>();
                    topCoords.add(spaceRow - 1);
                    topCoords.add(spaceCol);
                    this.neighborSpaces.add(topCoords);
                }
                // position bottom
                if (i == this.moveSpaces.size() - 1) {
                    ArrayList<Integer> bottomCoords = new ArrayList<Integer>();
                    bottomCoords.add(spaceRow + 1);
                    bottomCoords.add(spaceCol);
                    this.neighborSpaces.add(bottomCoords);
                }
            } else {
                // if the direction is right
                ArrayList<Integer> topCoords = new ArrayList<Integer>();
                ArrayList<Integer> bottomCoords = new ArrayList<Integer>();
                // position to the top
                topCoords.add(spaceRow - 1);
                topCoords.add(spaceCol);
                this.neighborSpaces.add(topCoords);

                // position to the bottom
                bottomCoords.add(spaceRow + 1);
                bottomCoords.add(spaceCol);
                this.neighborSpaces.add(bottomCoords);

                // position to the left
                if (i == 0) {
                    ArrayList<Integer> leftCoords = new ArrayList<Integer>();
                    leftCoords.add(spaceRow);
                    leftCoords.add(spaceCol - 1);
                    this.neighborSpaces.add(leftCoords);
                }
                // position to the right
                if (i == this.moveSpaces.size() - 1) {
                    ArrayList<Integer> rightCoords = new ArrayList<Integer>();
                    rightCoords.add(spaceRow);
                    rightCoords.add(spaceCol + 1);
                    this.neighborSpaces.add(rightCoords);
                }
            }
        }
    }

    public int placeWord() {
        // score returned to account for multipliers
        int wordVal = 0;
        int wordMultiplier = 1;

        if (this.direction.equals("down")) {
            //This checks that downward words aren't off the board
            // Checks that all words are greater than 1 character in length

//            System.out.println("word length reach: " + this.word.length() + " " + this.row);
//            System.out.println("check word: " + checkWord(this.word));
//            System.out.println("word length greater than 1: " + (this.word.length() > 1));
//            System.out.println("word.length" + this.word.length());

            if (this.word.length() + this.row < 15 && checkWord(this.word) && this.word.length() > 1) {
                //paint
                char[] letterArr = this.word.toCharArray();
                for(int i = 0; i < this.word.length(); i++){
                    placeLetter(letterArr[i], this.row + i, this.col);
                    wordVal += this.scoreGuide.get(letterArr[i]) * this.spaces[this.row + i][this.col].getLetterMultiplier();
                    wordMultiplier *= this.spaces[this.row + i][this.col].getWordMultiplier();

                    spacesPlayed.add(this.board.spaces[row+i][col]);
                }
            }
            else {
                //error. redo move?
            }
        }
        else {
            //rightward word check
            //This checks that downward words aren't off the board
            // Checks that all words are greater than 1 character in length
//            System.out.println("word length reach: " + this.word.length() + " " + this.col);
//            System.out.println("check word: " + checkWord(this.word));
//            System.out.println("word length greater than 1: " + (this.word.length() > 1));
//            System.out.println("word.length" + this.word.length());

            if ((this.word.length() + this.col) < 15 && checkWord(this.word) && this.word.length() > 1) {
                //paint
                char[] letterArr = this.word.toCharArray();
                for(int i = 0; i < this.word.length(); i++){
                    placeLetter(letterArr[i], this.row, this.col + i);
                    wordVal += this.scoreGuide.get(letterArr[i]) * this.spaces[this.row][this.col + i].getLetterMultiplier();
                    wordMultiplier *= this.spaces[this.row][this.col + i].getWordMultiplier();

                    spacesPlayed.add(this.board.spaces[row][col+i]);
                }
            }
            else {
                //error. redo move?
            }
        }

        wordVal *= wordMultiplier;

        // do word multiplier if exists
        return wordVal;
    }

    public int scoreSecondaryWord(String word) {
        int score = 0;
        char[] wordCharArr = word.toCharArray();
        for(int i = 0; i < word.length(); i++) {
            score += this.scoreGuide.get(wordCharArr[i]);
        }

        return score;
    }

    public void removeBonuses() {
        for(int i = 0; i < spacesPlayed.size(); i++) {
            spacesPlayed.get(i).setLetterMultiplier(1);
            spacesPlayed.get(i).setWordMultiplier(1);
        }
    }

    public boolean checkWord(String word){
        if(this.dictionary.contains(word)) {
            return true;
        } else {
            return false;
        }
    }


    // Add words to dictionary hashset
    public void populateDictionary() {
        // Load words file by line
        try {
            BufferedReader in = new BufferedReader(new FileReader("words"));
            String line = null;
            while((line = in.readLine()) != null) {
                this.dictionary.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("problem reading the dictionary " + e);
            e.printStackTrace();
        }
    }

    //print whole board
    public void render() {
        this.board.greeting();

        System.out.println("Tiles: " + this.board.currentPlayer.tilePrint());

        System.out.print("     ");


        for(int col = 1; col < 16; col++){
            if((col)< 9) {
                System.out.print("_" + col + "_" );
            }
            else {
                System.out.print("_" + col );
            }
        }
        System.out.print("\n");

        for(int row = 0; row < 15; row++) {
            if((row+1)< 10) {
                System.out.print("  " + (row+1) + " ");
            }
            else {
                System.out.print(" " + (row+1) + " ");
            }
            for(int col = 0; col < 15; col++) {
                System.out.print("|");
                System.out.print(this.spaces[row][col].getSpaceValue().toUpperCase());
//                System.out.print("_");
            }
            System.out.println("|");
        }
    }

    public void setScoreGuide() {
        this.scoreGuide.put('a', 1);
        this.scoreGuide.put('b', 3);
        this.scoreGuide.put('c', 3);
        this.scoreGuide.put('d', 2);
        this.scoreGuide.put('e', 1);
        this.scoreGuide.put('f', 4);
        this.scoreGuide.put('g', 2);
        this.scoreGuide.put('h', 4);
        this.scoreGuide.put('i', 1);
        this.scoreGuide.put('j', 8);
        this.scoreGuide.put('k', 5);
        this.scoreGuide.put('l', 1);
        this.scoreGuide.put('m', 3);
        this.scoreGuide.put('n', 1);
        this.scoreGuide.put('o', 1);
        this.scoreGuide.put('p', 3);
        this.scoreGuide.put('q', 10);
        this.scoreGuide.put('r', 1);
        this.scoreGuide.put('s', 1);
        this.scoreGuide.put('t', 1);
        this.scoreGuide.put('u', 1);
        this.scoreGuide.put('v', 4);
        this.scoreGuide.put('w', 4);
        this.scoreGuide.put('x', 8);
        this.scoreGuide.put('y', 4);
        this.scoreGuide.put('z', 10);
    }

    public void clearScreen() {
        char c = '\n';
        int length = 25;
        char[] chars = new char[length];
        Arrays.fill(chars, c);
        System.out.print(String.valueOf(chars));
    }
}
