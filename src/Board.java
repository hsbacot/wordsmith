import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by hsbacot on 2/12/14.
 */
public class Board {
    private HashSet dictionary = new HashSet();
//    private ArrayList<Charset> letterBag = new ArrayList();

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
    }



    public void setSpaces() {
        for(int row = 0; row < 15; row++) {
            for(int col = 0; col < 15; col++) {
                this.spaces[row][col] = new Space();
            }
        }
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
