package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* size of the world */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;

    private TETile[][] world;
    private long seed;
    private int playerXpos;
    private int playerYpos;
    private ArrayList<String> allSteps;
    private boolean language = true;
    private int numOfFlower;
    private int goal;
    private boolean strorkey = false;
    private HashMap<Integer, String> question;
    private HashMap<String, String> answer;
    private Random RANDOM = new Random(seed);
    private boolean makemovement = false;
    private ArrayList<Position> portal;
    private boolean goportal;
    private World origin;
    private int numOfEncounterWall;



    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        drawMainMenu();
        allSteps = new ArrayList<>();
        strorkey = true;
        question = new HashMap<>();
        answer = new HashMap<>();
        formRandomQuestion();
        formQuestionToAnswer();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());

                if (c == 'n') {
                    // System.out.println("来过n"); // debug use;
                    makemovement = false;
                    KeyBoardCaseN();
                    break;
                }

                if (c == 'l') {
                    // System.out.println("来过l"); // debug use;
                    makemovement = true;
                    KeyBoardCaseL();
                    break;
                }

                if (c == 'q') {
                    // System.out.println("来过q"); // debug use;
                    KeyBoardCaseQ();
                    break;
                }

                if (c == 'c') {
                    // System.out.println("来过c"); // debug use;
                    KeyBoardCaseC();
                    drawMainMenu();
                    interactWithKeyboard();
                    break;
                }
            }
        }
    }

    /** draw main menu (new, save, quit) */
    private void drawMainMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(new Color(255, 255, 255));

        if (language) {
            StdDraw.text(WIDTH / 2.0, HEIGHT * 4.0 / 5.0, "New Game (n / N)");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 3.0 / 5.0, "Load Game (l / L)");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 2.0 / 5.0, "Quit (q / Q)");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 5.0, "Change Language (c / C)");
        } else {
            StdDraw.text(WIDTH / 2.0, HEIGHT * 4.0 / 5.0, "新游戏 (n / N)");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 3.0 / 5.0, "加载游戏 (l / L)");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 2.0 / 5.0, "退出游戏 (q / Q)");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 5.0, "更换语言 (c / C)");
        }


        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }


    /** create a new game */
    private void KeyBoardCaseN() {

        numOfFlower = 0;
        String s = "n";
        drawEnterSeedMenu(s);

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());
                // System.out.println("character c is: " + c); // debug use;
                s += c;

                drawEnterSeedMenu(s);

                if (s.charAt(s.length() - 1) == 's') {
                    String refined = removeChar(s);
                    // System.out.println("refined is: " + refined); // debug use;
                    seed = getSeed(stringToList(refined));
                    // System.out.println("seed is: " + seed); // debug use;
                    break;
                }

                if (s.charAt(s.length() - 1) == 'b') {
                    s = "b";
                    break;
                }
                // System.out.println("String s is: " + s); // debug use;
            }
        }
        if (!s.equals("b")) {
            origin = new World(seed);
            goal = origin.getHallways().size();
            playerXpos = origin.getPlayer().getX();
            playerYpos = origin.getPlayer().getY();
            world = origin.getWorld();
            portal = origin.getPortal();
            ter.initialize(WIDTH, HEIGHT + 2);
            ter.renderFrame(world);
            playGame();
        } else {
            interactWithKeyboard();
        }
    }

    /** form the question */
    private void formRandomQuestion() {
        question.put(0, "2^2^3 = 2^5");
        question.put(1, "The slope of a vertical line is undefined.");
        question.put(2, "Two lines with positive slopes can be perpendicular.");
        question.put(3, "5 > 10 or 5 < 7.");
        question.put(4, "The set of ordered pairs {(6,4),(2,-5),(-2,4),(6,-4)} is a function.");
        question.put(5, "The additive inverse of -10 is 10.");
        question.put(6, "The product of two positive numbers is NOT positive.");
        question.put(7, "x + 2 = 7 is called an inequality.");
        question.put(8, "The associative property is used to write 4x + 8y = 4(x + 2y).");
        question.put(9, "The absolute value of a real negative number is negative.");
        question.put(10, "Suppose T: V→V is linear with characteristic polynomial. " +
                "Then the Jordan normal form of T has 3×3 Jordan block with eigenvalue 2.");
        question.put(11, "30% of x is equal to 0.03x");
        question.put(12, "x is at most equal to 9 is written mathematically as x < 9.");
        question.put(13, "3^20 + 3^20 + 3^20 = 3^21");
        question.put(14, "1.5 × 10^-5 is the scientific notation of the number 0.0000015.");
        question.put(15, "0/1000 = 0.");
    }

    /** form the answer */
    private void formQuestionToAnswer() {
        answer.put("2^2^3 = 2^5", "false");
        answer.put("The slope of a vertical line is undefined.", "true");
        answer.put("Two lines with positive slopes can be perpendicular.", "false");
        answer.put("5 > 10 or 5 < 7.", "true");
        answer.put("The set of ordered pairs {(6,4),(2,-5),(-2,4),(6,-4)} is a function.", "false");
        answer.put("The additive inverse of -10 is 10.", "true");
        answer.put("The product of two positive numbers is NOT positive.", "false");
        answer.put("x + 2 = 7 is called an inequality.", "false");
        answer.put("The associative property is used to write 4x + 8y = 4(x + 2y).", "false");
        answer.put("The absolute value of a real negative number is negative.", "false");
        answer.put("Suppose T: V→V is linear with characteristic polynomial. " +
                "Then the Jordan normal form of T has 3×3 Jordan block with eigenvalue 2.", "false");
        answer.put("30% of x is equal to 0.03x", "false");
        answer.put("x is at most equal to 9 is written mathematically as x < 9.", "false");
        answer.put("3^20 + 3^20 + 3^20 = 3^21", "true");
        answer.put("1.5 × 10^-5 is the scientific notation of the number 0.0000015.", "false");
        answer.put("0/1000 = 0.", "true");
    }

    /** draw a sub user interface to put seed */
    private void drawEnterSeedMenu(String s) {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(new Color(255, 255, 255));

        if (language) {
            StdDraw.text(WIDTH / 2.0, HEIGHT * 2.0 / 3.0, "Please enter your seed and press 's' at the end");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 2.0, s);
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 3.0, "Back (b / B)");
        } else {
            StdDraw.text(WIDTH / 2.0, HEIGHT * 2.0 / 3.0, "请输入你的种子然后按下s");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 2.0, s);
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 3.0, "返回 (b / B)");
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }


    /** play game. */
    private void playGame() {
        makemovement = false;
        String s = "";
        // revise true GameOver.
        while (true) {
            mousePointer();
            if (StdDraw.hasNextKeyTyped()) {
                String c = Character.toString(StdDraw.nextKeyTyped());
                s += c;
                makeMovement(stringToList(c), allSteps);

                // quit and save game.
                if (s.length() >= 2) {
                    if (s.charAt(s.length() - 2) == ':') {
                        if ((s.charAt(s.length() - 1) == 'q') || (s.charAt(s.length() - 1) == 'Q'))
                        saveSeed();
                        saveSteps(allSteps);
                        interactWithKeyboard();
                        break;
                    }
                }

                // cheat code.
                if (s.length() >= 4) {
                    if (s.charAt(s.length() - 4) == 'm' && s.charAt(s.length() - 3) == 'o'
                            && s.charAt(s.length() - 2) == 'r' && s.charAt(s.length() - 1) == 'e') {
                        numOfFlower += 1;
                    }
                }

                if (numOfEncounterWall >= 3) {
                    saveSeed();
                    saveSteps(allSteps);
                    numOfEncounterWall = 0;
                    interactWithKeyboard();
                    break;
                }

                // game over.
                if (numOfFlower == goal) {
                    win();
                    break;
                }
            }
        }
    }


    /** return to the main menu when win the game */
    private void win() {
        winMenu();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());

                if (c == 'r') {
                    break;
                }
            }
        }
        interactWithKeyboard();
    }


    /** draw the win menu */
    private void winMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(new Color(255, 255, 255));

        if (language) {
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 3.0, "You Win ! ");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 2.0 / 3.0, "Return (r / R)");
        } else {
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 3.0, "你赢啦 ！");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 2.0 / 3.0, "返回 (r / R)");
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }


    /** show the type of Tile */
    private void mousePointer() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();

        StdDraw.enableDoubleBuffering();

        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return;
        }

        ter.renderFrame(world);
        StdDraw.setPenColor(Color.white);

        if (language) {
            StdDraw.text(10, HEIGHT + 1, "number of flower: " + numOfFlower);
        } else {
            StdDraw.text(10, HEIGHT + 1, "你收集了多少花：" + numOfFlower);
        }

        if (world[x][y] == Tileset.AVATAR) {
            if (language) {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "this is player");
            } else {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "这个是你");
            }
        }

        if (world[x][y] == Tileset.WALL) {
            if (language) {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "this is wall");
            } else {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "这个是墙");
            }
        }

        if (world[x][y] == Tileset.FLOOR) {
            if (language) {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "this is FLOOR");
            } else {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "这个是地板");
            }
        }

        if (world[x][y] == Tileset.FLOWER) {
            if (language) {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "this is FLOWER");
            } else {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "这个是花");
            }
        }

        if (world[x][y] == Tileset.NOTHING) {
            if (language) {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "nothing is here");
            } else {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "啥也没有");
            }
        }

        if (world[x][y] == Tileset.LOCKED_DOOR) {
            if (language) {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "this is portal");
            } else {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "这是传送门");
            }
        }

        if (world[x][y] == Tileset.GRASS) {
            if (language) {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "this is a trap");
            } else {
                StdDraw.text(WIDTH - 20, HEIGHT + 1, "走进来试试，嘿嘿");
            }
        }

        StdDraw.show();
    }





    /** load recent game and play. */
    private void KeyBoardCaseL() {
        seed = loadSeed();
        origin = new World(seed);
        goal = origin.getHallways().size();
        playerXpos = origin.getPlayer().getX();
        playerYpos = origin.getPlayer().getY();
        world = origin.getWorld();
        portal = origin.getPortal();
        numOfFlower = 0;
        ArrayList<String> steps = loadSteps();
        makeMovement(steps, allSteps);
        ter.initialize(WIDTH, HEIGHT + 2);
        ter.renderFrame(world);
        playGame();
    }



    /** quit */
    private void KeyBoardCaseQ() {
        System.exit(0);
    }


    /** change language */
    private void KeyBoardCaseC() {
        language = !language;
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        ArrayList<String> output = stringToList(input);

        TETile[][] nt = stringInput(output);
        ter.initialize(80, 50);
        ter.renderFrame(nt);
        return  nt;
    }




    /** get the seed from the string, if no seed exist then give a default seed */
    private Long getSeed(ArrayList<String> input) {
        // get the position of
        String output = "";
        for (String s : input) {
            output += s;
        }
        if (input.get(0).equals("n") && input.get(1).equals("s")) {
            seed = 0;
        } else {
            String S = output.substring(output.indexOf("n") + 1, output.indexOf("s"));
            seed = Long.parseLong(S);
        }

        return seed;
    }


    /** helper method for interactWithInputString */
    private TETile[][] stringInput(ArrayList<String> input) {
        allSteps = new ArrayList<>();

        if (input.get(0).equals("n")) {
            seed = getSeed(input);
            World origin = new World(seed);
            playerXpos = origin.getPlayer().getX();
            playerYpos = origin.getPlayer().getY();
            world = origin.getWorld();

            // new movement.
            ArrayList<String> movement = getMovement(input);
            //for (int i = 0; i < movement.size(); i++) {
                //System.out.println(movement.get(i));
            //}
            makeMovement(movement, allSteps);


            if (input.get(input.size() - 2).equals(":")
                    && input.get(input.size() - 1).equals("q")) {
                saveSeed();
                saveSteps(allSteps);
                return emptyWorld();
            }
            return world;
        } else if (input.get(0).equals("l")) {
            // load the world before.
            seed = loadSeed();
            World origin = new World(seed);
            playerXpos = origin.getPlayer().getX();
            playerYpos = origin.getPlayer().getY();
            world = origin.getWorld();
            ArrayList<String> steps = loadSteps();
            //for (int i = 0; i < steps.size(); i++) {
            //    System.out.println(steps.get(i));
            //}
            makeMovement(steps, allSteps);

            // new movement.
            ArrayList<String> movement = getMovement(input);
            makeMovement(movement, allSteps);

            if (input.size() > 2) {
                if (input.get(input.size() - 2).equals(":")
                        && input.get(input.size() - 1).equals("q")) {
                    saveSeed();
                    saveSteps(allSteps);

                    //for (int i = 0; i < allSteps.size(); i++) {
                        //System.out.println(allSteps.get(i));
                    //}
                }
            }

            return world;
        } else if (input.get(0).equals("q")) {
            return emptyWorld();
        } else if (input.get(0).equals("c")) {
            language = !language;
            return world;
        } else {
            return emptyWorld();
        }
    }


    /** remove all character in a given string */
    private String removeChar(String input) {
        String output = "n";
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= 48 && input.charAt(i) <= 57) {
                output += input.charAt(i);
            }
        }
        output += "s";
        return output;
    }

    /** convert a string to list */
    private ArrayList<String> stringToList(String input) {
        String output = input.toLowerCase();
        ArrayList<String> str = new ArrayList<>();
        for (int i = 0; i < output.length(); i++) {
            str.add(Character.toString(output.charAt(i)));
        }
        return str;
    }

    /** convert a list to string */
    private String listToString(ArrayList<String> input) {
        String s = "";
        for (String str : input) {
            s += str;
        }
        return s;
    }



    /** search and return an ArrayList containing all movement */
    private ArrayList<String> getMovement(ArrayList<String> input) {
        // get the start position of the movement.
        int start = 1;
        for (int i = 0; i < input.size() && !input.get(i).equals("s"); i++) {
            start += 1;
        }

        if (input.get(0).equals("l")) {
            start = 1;
        }

        // System.out.println("where is the first movement: " + start); // debug use;

        // get length of movement.
        int numOfMovement = 0;
        for (int i = start; i < input.size() && !input.get(i).equals(":"); i++) {
            numOfMovement += 1;
        }
        // System.out.println("number of movement " + numOfMovement); // debug use;

        // add into an ArrayList.
        ArrayList<String> move = new ArrayList<>();
        for (int i = 0; i < numOfMovement; i++) {
            move.add(input.get(start + i));
            // System.out.println(move.get(i)); // debug use;
        }

        return move;
    }



    /** make movement, and all valid steps into allSteps inorder to save*/
    private void makeMovement(ArrayList<String> movement, ArrayList<String> allSteps) {
        // System.out.println("来过");
        for (int i = 0; i < movement.size(); i++) {
            if (movement.get(i).equals("w")) {
                if (isFlower(playerXpos, playerYpos + 1)) {
                    numOfFlower += 1;
                    if (strorkey && !makemovement) {
                        handleEncounterFlower();
                    }
                }
                if (isPortal(playerXpos, playerYpos + 1)) {
                    handleEncounterPortal(playerXpos, playerYpos, playerXpos, playerYpos + 1);
                } else if (isGrass(playerXpos, playerYpos + 1)) {
                    handleEncounterGrass(playerXpos, playerYpos);
                } else if (isValidMovement(playerXpos, playerYpos + 1)) {
                    if (!goportal) {
                        world[playerXpos][playerYpos] = Tileset.FLOOR;
                        playerYpos += 1;
                        world[playerXpos][playerYpos] = Tileset.AVATAR;
                    } else {
                        goportal = false;
                        world[playerXpos][playerYpos] = Tileset.LOCKED_DOOR;
                        playerYpos += 1;
                        world[playerXpos][playerYpos] = Tileset.AVATAR;
                    }
                    allSteps.add("w");
                } else if (strorkey) {
                    numOfEncounterWall += 1;
                    handleEncounterWall();
                }
            }

            if (movement.get(i).equals("s")) {
                if (isFlower(playerXpos, playerYpos - 1)) {
                    numOfFlower += 1;
                    if (strorkey && !makemovement) {
                        handleEncounterFlower();
                    }
                }
                if (isPortal(playerXpos, playerYpos - 1)) {
                    handleEncounterPortal(playerXpos, playerYpos, playerXpos, playerYpos - 1);
                } else if (isGrass(playerXpos, playerYpos - 1)) {
                    handleEncounterGrass(playerXpos, playerYpos);
                } else if (isValidMovement(playerXpos, playerYpos - 1)) {
                    if (!goportal) {
                        world[playerXpos][playerYpos] = Tileset.FLOOR;
                        playerYpos -= 1;
                        world[playerXpos][playerYpos] = Tileset.AVATAR;
                    } else {
                        goportal = false;
                        world[playerXpos][playerYpos] = Tileset.LOCKED_DOOR;
                        playerYpos -= 1;
                        world[playerXpos][playerYpos] = Tileset.AVATAR;
                    }
                    allSteps.add("s");
                } else if (strorkey)  {
                    numOfEncounterWall += 1;
                    handleEncounterWall();
                }
            }

            if (movement.get(i).equals("a")) {
                if (isFlower(playerXpos - 1, playerYpos)) {
                    numOfFlower += 1;
                    if (strorkey && !makemovement) {
                        handleEncounterFlower();
                    }
                }
                if (isPortal(playerXpos - 1, playerYpos)) {
                    handleEncounterPortal(playerXpos, playerYpos, playerXpos - 1, playerYpos);
                } else if (isGrass(playerXpos - 1, playerYpos)) {
                    handleEncounterGrass(playerXpos, playerYpos);
                } else if (isValidMovement(playerXpos - 1, playerYpos)) {
                    if (!goportal) {
                        world[playerXpos][playerYpos] = Tileset.FLOOR;
                        playerXpos -= 1;
                        world[playerXpos][playerYpos] = Tileset.AVATAR;
                    } else {
                        goportal = false;
                        world[playerXpos][playerYpos] = Tileset.LOCKED_DOOR;
                        playerXpos -= 1;
                        world[playerXpos][playerYpos] = Tileset.AVATAR;
                    }
                    allSteps.add("a");
                } else if (strorkey)  {
                    numOfEncounterWall += 1;
                    handleEncounterWall();
                }
            }

            if (movement.get(i).equals("d")) {
                if (isFlower(playerXpos + 1, playerYpos)) {
                    numOfFlower += 1;
                    if (strorkey && !makemovement) {
                        handleEncounterFlower();
                    }
                }
                if (isPortal(playerXpos + 1, playerYpos)) {
                    handleEncounterPortal(playerXpos, playerYpos, playerXpos + 1, playerYpos);
                } else if (isGrass(playerXpos + 1, playerYpos)) {
                    handleEncounterGrass(playerXpos, playerYpos);
                } else if (isValidMovement(playerXpos + 1, playerYpos)) {
                    if (!goportal) {
                        world[playerXpos][playerYpos] = Tileset.FLOOR;
                        playerXpos += 1;
                        world[playerXpos][playerYpos] = Tileset.AVATAR;
                    } else {
                        goportal = false;
                        world[playerXpos][playerYpos] = Tileset.LOCKED_DOOR;
                        playerXpos += 1;
                        world[playerXpos][playerYpos] = Tileset.AVATAR;
                    }
                    allSteps.add("d");
                } else if (strorkey)  {
                    numOfEncounterWall += 1;
                    handleEncounterWall();
                }
            }
        }
    }


    /** check is movement valid or not. */
    private boolean isValidMovement(int x, int y) {
        if (world[x][y] != Tileset.WALL) {
            return true;
        }
        return false;
    }



    /** when encounter wall */
    private void handleEncounterWall() {
        drawEncounterWallMenu();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());

                if (c == 'b') {
                    StdDraw.clear();
                    break;
                }
            }
        }
        ter.initialize(WIDTH, HEIGHT + 2);
        ter.renderFrame(world);
    }

    /** draw a sub user interface when encounter a wall */
    private void drawEncounterWallMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(new Color(255, 255, 255));

        if (language) {
            StdDraw.text(WIDTH / 2.0, HEIGHT * 2.0 / 3.0, "You can't walk through wall");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 3.0, "Back (b / B)");
        } else {
            StdDraw.text(WIDTH / 2.0, HEIGHT * 2.0 / 3.0, "你不能走进墙里");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 3.0, "返回 (b / B)");
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    /** when encounter flower */
    private void handleEncounterFlower() {
        int n = RANDOM.nextInt(15);
        String q = question.get(n);
        String s = "";

        drawEncounterFlowerMenu(q, s);

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());
                s += c;
                ArrayList<String> list = stringToList(s);
                if (s.equals(answer.get(q))) {
                    StdDraw.clear();
                    break;
                }
                if (list.get(list.size() - 1).equals("c")) {
                    s = "";
                }
                drawEncounterFlowerMenu(q, s);
            }
        }
        ter.initialize(WIDTH, HEIGHT + 2);
        ter.renderFrame(world);
    }

    /** draw a sub user interface when encounter a flower */
    private void drawEncounterFlowerMenu(String question, String answer) {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(new Color(255, 255, 255));

        if (language) {
            StdDraw.text(WIDTH / 2.0, HEIGHT * 3.0 / 4.0, "You need to answer the " +
                    "following question in order to do win the game");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 2.0 / 4.0, "Press c to clean your answer");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 4.0, "Question： " + question);
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 5.0, "Answer： " + answer);
        } else {
            StdDraw.text(WIDTH / 2.0, HEIGHT * 3.0 / 4.0, "给爷回答问题，脑瘫玩意儿");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 2.0 / 4.0, "按c重新写答案nt");
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 4.0, "自己看： " + question);
            StdDraw.text(WIDTH / 2.0, HEIGHT * 1.0 / 5.0, "答案： " + answer);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    /** return if the step has a flower */
    private boolean isFlower(int x, int y) {
        return (world[x][y] == Tileset.FLOWER);
    }

    /** return if the step is a portal */
    private boolean isPortal(int x, int y) {
        return (world[x][y] == Tileset.LOCKED_DOOR);
    }

    /** when encounter portal */
    private void handleEncounterPortal(int x, int y, int nextX, int nextY) {
        goportal = true;
        for (int i = 0; i < portal.size(); i++) {
            if (portal.get(i).getX() == nextX && portal.get(i).getY() == nextY) {
                if (i + 1 < portal.size()) {
                    playerXpos = portal.get(i + 1).getX();
                    playerYpos = portal.get(i + 1).getY();


                    world[x][y] = Tileset.FLOOR;
                    world[playerXpos][playerYpos] = Tileset.AVATAR;
                } else {
                    playerXpos = portal.get(0).getX();
                    playerYpos = portal.get(0).getY();

                    world[x][y] = Tileset.FLOOR;
                    world[playerXpos][playerYpos] = Tileset.AVATAR;
                }
            }
        }
    }

    /** return if the step is grass */
    private boolean isGrass(int x, int y) {
        return (world[x][y] == Tileset.GRASS);
    }

    /** when encounter grass */
    private void handleEncounterGrass(int x, int y) {
        world[x][y] = Tileset.FLOOR;
        playerXpos = origin.getPlayer().getX();
        playerYpos = origin.getPlayer().getY();
        numOfFlower = 0;
        world[playerXpos][playerYpos] = Tileset.AVATAR;
    }

    /** return an empty world */
    private TETile[][] emptyWorld() {
        TETile[][] emptyworld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                emptyworld[x][y] = Tileset.NOTHING;
            }
        }
        return emptyworld;
    }

    /** save the seed of the map */
    private void saveSeed() {
        File file = new File("seed.txt");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(seed);
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IOException");
            System.exit(0);
        }

    }

    /** save all of the steps */
    private void saveSteps(ArrayList<String> allSteps) {
        File file = new File("steps.txt");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listToString(allSteps));
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IOException");
            System.exit(0);
        }
    }

    /** load the seed */
    private long loadSeed() {
        File file = new File("seed.txt");

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            seed = (long) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IOException");
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.exit(0);
        }

        return seed;
    }

    /** load the setps */
    private ArrayList<String> loadSteps() {
        File file = new File("steps.txt");

        String step = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            step = (String) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IOException");
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.exit(0);
        }

        // System.out.println("load steps is: " + step); // debug use.
        return stringToList(step);
    }


    public static void main(String[] args) {
        Engine a = new Engine();
        a.interactWithInputString("n0sawwwwwwwww:q");
        a.interactWithInputString("lddd");
        // a.interactWithInputString("ls:q");
        // a.interactWithInputString(("lww"));
    }
}


