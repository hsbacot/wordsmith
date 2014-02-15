/**
 * Created by hsbacot on 2/12/14.
 */
public class Board {
    Space[][] spaces = new Space[15][15];

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
            if (word.length() + row < 15) {
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
            if (word.length() + col < 15) {
                //paint
            }
            else {
                //error. redo move?
            }
        }

    }
}
