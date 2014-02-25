import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by hsbacot on 2/24/14.
 */
public class Move {
    private HashSet<String> dictionary = new HashSet();
    public ArrayList<String> words;
    public ArrayList<String> oldWords;
    public ArrayList<String> wordsPerTurn;
    private String word;
    private int row;
    private int col;
    private String direction;
    private Space[][] spaces;
    private HashMap<Character, Integer> scoreGuide = new HashMap<Character, Integer>();
    private Board board;

    public Move(String word, int row, int col, String direction, Board board){
        this.word = word;
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.spaces = board.spaces;
        this.board = board;

        populateDictionary();
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

    public int placeWord() {
        // score returned to account for multipliers
        int wordVal = 0;

        if (this.direction.equals("down")) {
            //This checks that downward words aren't off the board
            // Checks that all words are greater than 1 character in length
            if (this.word.length() + this.row < 15 && checkWord(this.word) && this.word.length() > 1) {
                //paint
                char[] letterArr = this.word.toCharArray();
                for(int i = 0; i < this.word.length(); i++){
                    placeLetter(letterArr[i], this.row + i, this.col);
                    wordVal += this.scoreGuide.get(letterArr[i]) * this.spaces[this.row + i][this.col].getLetterMultiplier();
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
            if (this.word.length() + this.row < 15 && checkWord(this.word) && this.word.length() > 1) {
                //paint
                char[] letterArr = this.word.toCharArray();
                for(int i = 0; i < this.word.length(); i++){
                    placeLetter(letterArr[i], this.row, this.col + i);
                    wordVal += this.scoreGuide.get(letterArr[i]);
                }
            }
            else {
                //error. redo move?
            }
        }

        // do word multiplier if exists
        return wordVal;
    }

    public boolean checkWord(String word){
        if(this.dictionary.contains(word)) {
            return true;
        } else {
            return false;
        }
    }

    public void detectWords() {
//      String str = new String();

        // USE STRING BUILDER

        ArrayList<Character> wordDetected = new ArrayList<Character>();

        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                char letter = this.board.spaces[i][j].getLetter();
                if (letter != '\u0000') {
                    wordDetected.add(letter);
                }
                else {
                    String holder = new String();
                    holder = wordDetected.toString();
                    words.add(holder);
                    wordDetected.clear();
                    wordDetected.add('\u0000');
                }
                if (i == 15) {
                    words.add(wordDetected.toString());
                    wordDetected.clear();
                    wordDetected.add('\u0000');
                }
            }
        }

        wordDetected.clear();
        wordDetected.add('\u0000');

        //Flipped i and j in order to check vertical words
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                char letter = this.board.spaces[j][i].getLetter();
                if (letter != '\u0000') {
                    wordDetected.add(letter);
                }
                else {
                    words.add(word.toString());
                    wordDetected.clear();
                    wordDetected.add('\u0000');
                }
                if (j == 15) {
                    words.add(word.toString());
                    wordDetected.clear();
                    wordDetected.add('\u0000');
                }
            }
        }
    }

    // Add words to dictionary hashset
    public void populateDictionary() {
        // Load words file by line
        try {
            BufferedReader in = new BufferedReader(new FileReader("words"));
            String line = null;
            while((line = in.readLine()) != null) {
                this.dictionary.add(in.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print whole board
    public void render() {
        for(int row = 0; row < 15; row++) {
            for(int col = 0; col < 15; col++) {
                System.out.print(" ");
                System.out.print(this.spaces[row][col].getSpaceValue());
                System.out.print(" ");
            }
            System.out.println();
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
}
