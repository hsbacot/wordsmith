import java.util.ArrayList;

/**
 * Created by hsbacot on 2/12/14.
 */
public class Player {

    public String name = new String(); //name
    public ArrayList<Character> hand = new ArrayList();//tiles arraylist
    public int score = 0;//score

    Player(String name){
        this.name = name;
    }

    Player(){}

    public int getScore() {
        return score;
    }

    public String tilePrint() {
        String tiles = "";

        for (int i = 0; i < this.hand.size(); i++) {
            tiles = tiles + (this.hand.get(i).toString());
        }
        return tiles;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addToScore(int score) {
        this.setScore(this.getScore() + score);
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

    public void addTileToHand(Character tile){
        this.hand.add(tile);
    }
}
