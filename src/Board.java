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
    private ArrayList<Charset> letterBag = new ArrayList();
    private HashMap<Character, Integer> scoreGuide = new HashMap<Character, Integer>();

    Space[][] spaces = new Space[15][15];

    // initialize hashset to hold words from words file
//    HashSet dictionary = new HashSet();
    public Board() {
        populateDictionary();
        setSpaces();
        setLetterBag();
        setScoreGuide();
    }



    public void setSpaces() {
        for(int row = 0; row < 15; row++) {
            for(int col = 0; col < 15; col++) {
                this.spaces[row][col] = new Space();
            }
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
    public void placeLetter(char letter, int row, int col) {
        this.spaces[row][col].setLetter(letter);
    }

    public void placeWord(String word, int row, int col, String direction) {
        if (direction.equals("down")) {
            //This checks that downward words aren't off the board
            // Checks that all words are greater than 1 character in length
            if (word.length() + row < 15 && checkWord(word) && word.length() > 1) {
                //paint
                char[] letterArr = word.toCharArray();
                for(int i = 0; i < word.length(); i++){
                    placeLetter(letterArr[i], row + i, col);
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
            if (word.length() + row < 15 && checkWord(word) && word.length() > 1) {
                //paint
                char[] letterArr = word.toCharArray();
                for(int i = 0; i < word.length(); i++){
                    placeLetter(letterArr[i], row, col + i);
                }
            }
            else {
                //error. redo move?
            }
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
                this.dictionary.add(in.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
