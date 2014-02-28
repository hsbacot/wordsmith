import java.util.ArrayList;

/**
 * Created by hsbacot on 2/12/14.
 */
public class Player {

    public String name = new String(); //name
    public ArrayList hand = new ArrayList();//tiles arraylist
    public int score = 0;//score

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getHand() {
        return hand;
    }

    public void setHand(ArrayList hand) {
        this.hand = hand;
    }
}
