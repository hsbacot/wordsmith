/**
 * Created by hsbacot on 2/12/14.
 */
public class Space {
    // Defaults
    public char letter;
    public String multiplier;



    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public String getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public String getSpaceValue() {
        if (this.letter != '\u0000') {
            String returnLetter = Character.toString(this.letter);
            return returnLetter;
        }
        else {
            if (this.multiplier != null) {
//                Define multiplier symbol later
                return this.multiplier;
            }
            else {
                return "_";
            }
        }
    }
}
