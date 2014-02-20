import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * Created by hsbacot on 2/12/14.
 */
public class Board {
    private HashSet dictionary = new HashSet();
    Space[][] spaces = new Space[15][15];

    // initialize hashset to hold words from words file
//    HashSet dictionary = new HashSet();
    public Board() {
        populateDictionary();
        setSpaces();
    }



    public void setSpaces() {
        for(int row = 0; row < 15; row++) {
            for(int col = 0; col < 15; col++) {
                this.spaces[row][col] = new Space();
            }
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
        if (direction == "down") {
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
