package byow.Core;

import java.util.*;


/** generate random room with random location;
 * Room will store the upper left position and lower right position */
public class Room {

    private Long seed;
    private Room room;
    private Position upperLeft;
    private Position lowerRight;
    private Random RANDOM;
    private Position hallway;

    /** build a room */
    public Room(Position upperLeft, Position lowerRight, Position hallway) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
        this.hallway = hallway;
    }

    /** rceive from World */
    public Room(Long seed) {
        this.seed = seed;
        this.RANDOM = new Random(seed);
    }


    /** generate a valid room */
    public Room roomGenerator(ArrayList<Position> hallways) {
        int width = RandomUtils.uniform(RANDOM, 3, 15);
        int height = RandomUtils.uniform(RANDOM, 3, 10);
        int xPos = RandomUtils.uniform(RANDOM, 1, 77);
        int yPos = RandomUtils.uniform(RANDOM, 2, 48);

        upperLeft = new Position(xPos, yPos);
        lowerRight = new Position(xPos + width - 1, yPos - height + 1);

        // if room is out of boundary or there does not exist a valid hallway,
        // then generate another room.
        if (xPos + width < 80 && yPos - height > 0) {

            // create a valid hallway.
            hallway = formHallway(hallways);

            // if does not exist a hallway return a new room.
            if (hallway.getX() == -1) {
                return roomGenerator(hallways);
            }

            room = new Room(upperLeft, lowerRight, hallway);
        } else {
            return roomGenerator(hallways);
        }
        return room;
    }

    int num = 0;
    /** generate a valid hallway, if no such hallway exist then generate an error */
    private Position formHallway(ArrayList<Position> hallways) {
        int hallwayX = RandomUtils.uniform(RANDOM, upperLeft.getX(), lowerRight.getX());
        int hallwayY = RandomUtils.uniform(RANDOM, lowerRight.getY(), upperLeft.getY());
        hallway = new Position(hallwayX, hallwayY);
        if (checkValidHallway(hallways, hallway)) {
            return hallway;
        } else if (num < upperLeft.getY() - lowerRight.getY()
                + lowerRight.getX() - upperLeft.getX() + 2) {
            num += 1;
            // System.out.println(num);
            return formHallway(hallways);
        } else {
            // avoid infinite recursion.
            return new Position(-1, -1);
        }
    }



    /** Helper method return true if does not exist two neighbours. */
    private boolean checkValidHallway(ArrayList<Position> hallways, Position h) {
        for (int i = 0; i < hallways.size(); i++) {
            int x1 = hallways.get(i).getX();
            int x2 = h.getX();
            int y1 = hallways.get(i).getY();
            int y2 = h.getY();
            if (Math.abs(x1 - x2) == 1 || Math.abs(y1 - y2) == 1) {
                return false;
            }
        }
        return true;
    }

    /** return the position of the upper left */
    public Position getUpperLeft() {
        return upperLeft;
    }


    /** return the position of the lower right */
    public Position getLowerRight() {
        return lowerRight;
    }


    /** return the hallway position */
    public Position getHallway() {
        return hallway;
    }




}

