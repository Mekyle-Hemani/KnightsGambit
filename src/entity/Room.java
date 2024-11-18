package entity;
public class Room {
    int x, y, width, height;
    public Room(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    // Check if this room overlaps with another room
    public boolean overlaps(Room other){
        return (this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y);
    }
    // Get center coordinates of the room
    public int getCenterX() {
        return x + width / 2;
    }
    public int getCenterY() {
        return y + height / 2;
    }
}