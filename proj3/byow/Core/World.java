package byow.Core;


import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


import java.util.*;

/** Step 1: form random number of rooms at random positions.
 *  Step 2: The way to randomly form hallway through generate random positions and
 *  connecting the hallway positions and random positions.
 *  Step 3: draw the wall.
 *
 *
 *  Room corner case: two random room may overlap, so use a boolean 2D array to check if
 *  there exist a room.
 *
 *  Hallway corner case: two neighbour hallway will create hallway, so I create a method
 *  isValidPosition to check if it is valid. The hallway point is already checked when
 *  generating room.
 *
 *  Hallway corner case: when creating a single room, this world generator may not handle
 *  turns and hallways. So I randomly create positions in world.
 *
 *  Hallway corner case: when generating random position, make sure that four corners are
 *  valid Position.
 *
 *  Wall corner case: we need to consider the wall in the boundary.
 *
 */

/** generate World according to the seed */
public class World {

    private final int width = 80;
    private final int height = 50;
    private final TETile[][] world;
    private final boolean[][] openWorld; // store room and it's wall
    private final Random RANDOM; // create random things.
    // store all valid positions.
    private final ArrayList<Position> allValidPosition = new ArrayList<>();
    private Position player;
    private final ArrayList<Position> hallways;
    private final ArrayList<Position> portal;
    private Position end;


    /** create whole world with random room and Hallway. */
    public World(Long seed) {
        openWorld = new boolean[width][height];
        ArrayList<Room> rooms = new ArrayList<>(); // store a list of room

        this.RANDOM = new Random(seed);

        // initialize the tile rendering engine with a window of size width x and height y.

        // initialize the tile
        world = new TETile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // create number of rooms.
        int numOfRoom = RandomUtils.uniform(RANDOM, 10, 15);
        // System.out.println("the number of generated room is " + numOfRoom);
        Room room = new Room(seed);
        hallways = new ArrayList<>();
        while (rooms.size() < numOfRoom) {
            Room r = room.roomGenerator(hallways);
            if (!contain(r)) {
                rooms.add(r);
                hallways.add(r.getHallway());
                allValidPosition.add(r.getHallway());
                open(r);
            }
        }

        // add rooms into the world.
        for (Room r : rooms) {
            addRoom(r);
        }

        // generate hallway, but does not applied to corner case.
        for (int i = 0; i < hallways.size() - 1; i++) {

            Position start = hallways.get(i);
            Position goal = hallways.get(i + 1);

            // create random number of random position connecting start and goal.
            int numOfPosition = RandomUtils.uniform(RANDOM, 0, 2);

            // System.out.println("number of random points between i and i+1 is" + numOfPosition);
            ArrayList<Position> positions = formRandomPositions(numOfPosition, start, goal);
            for (int n = 0; n < positions.size() - 1; n++) {
                formHallway(positions.get(n), positions.get(n + 1));
            }
        }

        // generate hallway, corner case(hallways size == 1).
        if (hallways.size() == 1) {
            Position start = hallways.get(0);

            // create random number of random position.
            int numOfPosition = RandomUtils.uniform(RANDOM, 1, 3);

            ArrayList<Position> positions = formRandomPositions(numOfPosition, start, start);
            for (int n = 0; n < positions.size() - 1; n++) {
                formHallway(positions.get(n), positions.get(n + 1));
            }
        }

        // generate wall.
        formWall();

        // form flower.
        formFlower();

        // form player.
        formPlayer();

        // form portal.
        portal = new ArrayList<>();
        portal.add(formPortal());
        portal.add(formPortal());
        portal.add(formPortal());
        portal.add(formPortal());

        // form end.
        formEnd();

    }

    /** return TETile[][]. */
    public TETile[][] getWorld() {
        return world;
    }

    /** add rooms into world */
    private void addRoom(Room r) {
        int left = r.getUpperLeft().getX();
        int up = r.getUpperLeft().getY();
        int right = r.getLowerRight().getX();
        int down = r.getLowerRight().getY();

        for (int x = left; x <= right; x++) {
            for (int y = up; y >= down; y--) {
                world[x][y] = Tileset.FLOOR;
            }
        }
    }

    /** helper method
     * if room r1 overlap other room return true; else return false.  */
    private boolean contain(Room r1) {
        // upper left position.
        int r1ux = r1.getUpperLeft().getX() - 1;
        int r1uy = r1.getUpperLeft().getY() + 1;
        // lower right position.
        int r1lx = r1.getLowerRight().getX() + 1;
        int r1ly = r1.getLowerRight().getY() - 1;

        for (int x = r1ux; x <= r1lx; x++) {
            for (int y = r1uy; y >= r1ly; y--) {
                if (openWorld[x][y]) {
                    // System.out.println("true");
                    return true;
                }
            }
        }
        // System.out.println("false");
        return false;
    }

    /** helper method
     * used to check if two room overlap. if room is valid input true to it's position */
    private void open(Room r) {
        int left = r.getUpperLeft().getX() - 1;
        int right = r.getLowerRight().getX() + 1;
        int up = r.getUpperLeft().getY() + 1;
        int down = r.getLowerRight().getY() - 1;

        for (int x = left; x <= right; x++) {
            for (int y = up; y >= down; y--) {
                openWorld[x][y] = true;
            }
        }
    }

    /** connect two hallway point. p1 is the start position and p2 is the goal.*/
    private void formHallway(Position p1, Position p2) {

        // System.out.println(p1.getX() + " " + p1.getY());
        // System.out.println(p2.getX() + " " + p2.getY());

        boolean down = RANDOM.nextBoolean(); // randomly choose a path.

        int startX = Math.min(p1.getX(), p2.getX());
        int startY = Math.min(p1.getY(), p2.getY());

        int goalX = Math.max(p1.getX(), p2.getX());
        int goalY = Math.max(p1.getY(), p2.getY());

        if (down) {
            if ((p1.getX() < p2.getX() && p1.getY() < p2.getY())
                    || (p2.getX() < p1.getX() && p2.getY() < p1.getY())) {
                // from lower left to lower right.
                for (int x = startX; x <= goalX; x++) {
                    world[x][startY] = Tileset.FLOOR;
                }
                // from lower right to upper right
                for (int y = startY; y <= goalY; y++) {
                    world[goalX][y] = Tileset.FLOOR;
                }
            } else {
                // from lower left to lower right.
                for (int x = startX; x <= goalX; x++) {
                    world[x][startY] = Tileset.FLOOR;
                }
                // from lower left to upper left.
                for (int y = startY; y <= goalY; y++) {
                    world[startX][y] = Tileset.FLOOR;
                }
            }
        } else {
            if ((p1.getX() < p2.getX() && p1.getY() < p2.getY())
                    || (p2.getX() < p1.getX() && p2.getY() < p1.getY())) {
                // from lower left to upper left.
                for (int y = startY; y <= goalY; y++) {
                    world[startX][y] = Tileset.FLOOR;
                }
                // from upper left to upper right.
                for (int x = startX; x <= goalX; x++) {
                    world[x][goalY] = Tileset.FLOOR;
                }
            } else {
                // from lower right to upper right.
                for (int y = startY; y <= goalY; y++) {
                    world[goalX][y] = Tileset.FLOOR;
                }
                // from upper left to upper right.
                for (int x = startX; x <= goalX; x++) {
                    world[x][goalY] = Tileset.FLOOR;
                }
            }
        }

    }

    /** helper method
     * return an ArrayList of random valid Position between two Position */
    private ArrayList<Position> formRandomPositions(int numOfPosition,
                                                    Position start, Position goal) {
        ArrayList<Position> positions = new ArrayList<>();

        positions.add(start);
        // System.out.println("start: " + start.getX() + " " + start.getY());

        int count = 0; // avoid infinite loop;
        int n = 0;
        while (n < numOfPosition) {
            count += 1;
            // System.out.println("number of loop in formRandomPositions is" + count);
            int x = RandomUtils.uniform(RANDOM, 2, width - 3);
            int y = RandomUtils.uniform(RANDOM, 2, height - 3);
            Position p = new Position(x, y);

            Position l = positions.get(positions.size() - 1); // get the last position.

            Position p1 = new Position(p.getX(), l.getY());
            Position p2 = new Position(l.getX(), p.getY());

            if (isValidPosition(p) && isValidPosition(p1) && isValidPosition(p2)) {
                // System.out.println("x: " + x + " "+ "y: " + y);
                n += 1;
                positions.add(p);
                allValidPosition.add(p);
                allValidPosition.add(p1);
                allValidPosition.add(p2);
            }

            // avoid infinite loop;
            if (count == 400) {
                break;
            }

        }

        positions.add(goal);
        // System.out.println("goal: " + goal.getX() + " " + goal.getY());
        return positions;
    }

    /** helper method
     * search all valid position to check is the new Position p is valid */
    private boolean isValidPosition(Position p) {
        int x2 = p.getX();
        int y2 = p.getY();

        if (openWorld[x2][y2]) {
            return false;
        }

        for (Position p1 : allValidPosition) {
            int x1 = p1.getX();
            int y1 = p1.getY();

            if (Math.abs(x1 - x2) == 1 || Math.abs(y1 - y2) == 1) {
                return false;
            }
        }

        return true;
    }

    /** add walls to the given world */
    private void formWall() {
        // corner case 1
        for (int i = 0; i < width; i++) {
            if (world[i][1] == Tileset.FLOOR) {
                world[i][0] = Tileset.WALL;
                world[i + 1][0] = Tileset.WALL;
                world[i - 1][0] = Tileset.WALL;
            }

            if (world[i][height - 2] == Tileset.FLOOR) {
                world[i][height - 1] = Tileset.WALL;
                world[i + 1][height - 1] = Tileset.WALL;
                world[i - 1][height - 1] = Tileset.WALL;
            }
        }

        //corner case 2
        for (int i = 0; i < height; i++) {
            if (world[1][i] == Tileset.FLOOR) {
                world[0][i] = Tileset.WALL;
                world[0][i + 1] = Tileset.WALL;
                world[0][i - 1] = Tileset.WALL;
            }

            if (world[width - 2][i] == Tileset.FLOOR) {
                world[width - 1][i] = Tileset.WALL;
                world[width - 1][i + 1] = Tileset.WALL;
                world[width - 1][i - 1] = Tileset.WALL;
            }
        }

        // add other walls
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                if (world[x][y] == Tileset.NOTHING) {
                    if (world[x - 1][y - 1] == Tileset.FLOOR
                            || world[x + 1][y + 1] == Tileset.FLOOR
                            || world[x][y - 1] == Tileset.FLOOR
                            || world[x + 1][y - 1] == Tileset.FLOOR
                            || world[x - 1][y] == Tileset.FLOOR
                            || world[x + 1][y] == Tileset.FLOOR
                            || world[x - 1][y + 1] == Tileset.FLOOR
                            || world[x][y + 1] == Tileset.FLOOR) {
                        world[x][y] = Tileset.WALL;
                    }
                }
            }
        }
    }

    /** form the hallway position in the room. */
    private void formFlower() {
        for (Position p : hallways) {
            int x = p.getX();
            int y = p.getY();

            world[x][y] = Tileset.FLOWER;
        }
    }

    /** generate the player position */
    private Position formPlayer() {
        Boolean stop = false;
        for (int x = 0; x < width && !stop; x++) {
            for (int y = 0; y < height && !stop; y++) {
                if (world[x][y] == Tileset.FLOOR) {
                    stop = true;
                    world[x][y] = Tileset.AVATAR;
                    player = new Position(x, y);
                }
            }
        }
        return player;
    }

    /** generate portal */
    private Position formPortal() {
        int xPos = RandomUtils.uniform(RANDOM, 1, 77);
        int yPos = RandomUtils.uniform(RANDOM, 2, 48);

        end = new Position(xPos, yPos);

        if (world[xPos][yPos] == Tileset.FLOOR) {
            world[xPos][yPos] = Tileset.LOCKED_DOOR;
            return end;
        } else {
            return formPortal();
        }

    }

    /** form end */
    private Position formEnd() {
        int xPos = RandomUtils.uniform(RANDOM, 1, 77);
        int yPos = RandomUtils.uniform(RANDOM, 2, 48);

        Position end = new Position(xPos, yPos);

        if (world[xPos][yPos] == Tileset.FLOOR) {
            world[xPos][yPos] = Tileset.GRASS;
            return end;
        } else {
            return formEnd();
        }
    }

    /** get the player position */
    public Position getPlayer() {
        return player;
    }

    /** get hallways */
    public ArrayList<Position> getHallways() {
        return hallways;
    }

    /** get Portal */
    public ArrayList<Position> getPortal() {
        return portal;
    }

    /** get end */
    public Position getEnd() {
        return end;
    }

}

