package mapDevelopmentFunctions;

import main.GamePanel;

public class Collision {
    static GamePanel gp;

    public Collision(GamePanel gp) {
        Collision.gp = gp;
    }

    //Sees if the players next movement is into a wall or not
    public static boolean CheckCollision(int Xpos, int Ypos, int direction) {
        //Depending on the direction given...
        switch (direction) {
            //Change the given coordinates by one position
            case 0 -> Ypos -= 1; //Up
            case 1 -> Xpos -= 1; //Left
            case 2 -> Xpos += 1; //Right
            case 3 -> Ypos += 1; //Down
        }
        //If the player wants to leave the screen in any direction
        if (((Xpos < 0) || (Xpos >= (gp.screenWidth/gp.tileSize))) || (Ypos >= (gp.screenHeight/gp.tileSize))){
            return false; //Don't allow the requested movement
        }
        //Otherwise...
        //Check if the position that the player wants to go to is a ground tile from the tileLocations array.
        //The value of a ground tile is 1. It checks if the requested movement (From the direction integer) is into a wall or not
        //It then returns if the player can go forward (true) or not (false)
        return Character.getNumericValue((Integer.toString(GamePanel.tileLocations.get(Ypos * (gp.screenWidth / gp.tileSize) + Xpos))).charAt(1)) != 1;
    }
}