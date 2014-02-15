/**
 * Created by hsbacot on 2/12/14.
 */
public class Board {
    Space[][] spaces = new Space[15][15];

    public boolean isOccupied(int row, int column){
       if(spaces[row][column].getLetter() == null) {
           return false;
       }
            return true;
    };


    // Return the information stored in each space
    public String[] spaceInfo() {
        String[] info = new String[]{};

        return info;
    }

}
