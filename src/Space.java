/**
 * Created by hsbacot on 2/12/14.
 */
public class Space {
    // Defaults
    public char letter;
    public int letterMultiplier;
    public int wordMultiplier;

    public int getWordMultiplier() {
        return wordMultiplier;
    }

    public void setWordMultiplier(int wordMultiplier) {
        this.wordMultiplier = wordMultiplier;
    }

    public int getLetterMultiplier() {
        return letterMultiplier;
    }

    public void setLetterMultiplier(int letterMultiplier) {
        this.letterMultiplier = letterMultiplier;
    }



    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    // used to render the space on the board
    public String getSpaceValue() {
        if (this.letter != '\u0000') {
            String returnLetter = Character.toString(this.letter);
            return returnLetter;
        }
        else {
            if (this.letterMultiplier == 2) {
//                Define multiplier symbol later
                return "2L";
            } else if (this.letterMultiplier == 3){
                return "3L";
            } else if (this.wordMultiplier == 2) {
                return "2W";
            } else if (this.wordMultiplier == 3) {
                return "3W";
            }
            else {
                return "__";
            }
        }
    }
}
