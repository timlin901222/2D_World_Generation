package byow.Core;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static byow.Core.Utils.join;
import static byow.Core.Utils.readContentsAsString;

public class Engine {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File SAVED_FILE = join(CWD, "savedFile.txt");

    /* Feel free to change the width and height. */
    private boolean quitState = false;
    private WorldGenerator worldGenerator;
    private PlayerController playerOne;
    private boolean limitedVision = false;
    private boolean worldCreated = false;
    private boolean isColon = false;
    private String saveData = "";
    private String name = "";
    private int time = 0;
    private int moves = 0;
    private Collectibles[] collectibles = new Collectibles[10];

    public Engine() {
        worldGenerator = new WorldGenerator();
        createLoad();
        this.name = name;
    }

    public boolean getQuitState() {
        return quitState;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public static void createLoad() {

        try {
            SAVED_FILE.createNewFile();
        } catch (IOException e) {
            System.out.println("CANNOT CREATE SAVE.TXT");
        }

    }

    public void display() {
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Arial", Font.PLAIN, 14);
        StdDraw.setFont(font);
        if (!name.equals("")) {
            StdDraw.text(40, 40, "Character Name: " + name);
        }
        StdDraw.textLeft(2, 40, findCoordinate());
        StdDraw.text(80, 40, "Time Left: " + time);

        if (time == 0 && moves > 10) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledRectangle(0, 0, 100, 50);
            StdDraw.setPenColor(Color.white);
            StdDraw.text(45, 20, "YOU LOSE!!!");
        }
        if (playerOne.win()) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledRectangle(0, 0, 100, 50);
            StdDraw.setPenColor(Color.white);
            StdDraw.text(45, 20, "YOU WIN!!!");
        }
    }

    public void generateLoadingScreen() {
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Arial", Font.PLAIN, 14);
        StdDraw.setFont(font);
        StdDraw.text(45, 25, "CS61B: THE GAME");
        StdDraw.text(45, 20, "New Game (N)");
        StdDraw.text(45, 19, "Load Game (L)");
        StdDraw.text(45, 18, "Set Character Name (C)");
        StdDraw.text(45, 17, "Quit (Q)");
        StdDraw.show();
    }

    public String findCoordinate() {
        int X = (int) StdDraw.mouseX();
        int Y = (int) StdDraw.mouseY();
        return worldGenerator.findTile(X, Y);
    }
    /**
     * updates UI in interactWithKeyboard()
     */
    public void addTimer(int timer) {
        time = timer;
    }

    public void draw() {
        display();

        Color colour = new Color(0, 0, 0, moves / 20);
        StdDraw.setPenColor(colour);
        StdDraw.filledRectangle(0, 0, 100, 40);
        StdDraw.show();

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(2, 40, 10, 2);
        StdDraw.filledRectangle(40, 40, 10, 2);
        StdDraw.filledRectangle(80, 40, 10, 2);
    }
    public TETile[][] generateWorld(int xLower, int xUpper,
                                    int yLower, int yUpper, TETile[][] tiles) {
        TETile[][] tempTiles = new TETile[90][43];
        for (int i = 0; i < 90; i++) {
            for (int j = 0; j < 43; j++) {
                if (i <= xUpper && i >= xLower && j <= yUpper && j >= yLower) {
                    tempTiles[i][j] = tiles[i][j];
                } else {
                    tempTiles[i][j] = Tileset.NOTHING;
                }
            }
        }
        return tempTiles;
    }
    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void drawRec(int x) {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(45, x, 10, 1);
        StdDraw.setPenColor(Color.WHITE);
    }
    public void interactWithKeyboard() {
        char c = 0;
        if (!worldCreated) {
            generateLoadingScreen();
        }
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);
                saveData += c;
                if (!worldCreated && c == 'q') {
                    System.exit(0);
                }
                if (c == 'c' && !worldCreated) {
                    StdDraw.text(45, 15, "Name: ");
                    StdDraw.show();

                    while (c != 's' && c != 'S') {
                        if (StdDraw.hasNextKeyTyped()) {
                            c = StdDraw.nextKeyTyped();
                            name += c;
                            drawRec(15);
                            if (c != 's' && c != 'S') {
                                drawRec(15);
                                StdDraw.text(45, 15, "Name: " + name);
                            }
                            StdDraw.show();
                        }
                    }
                    name = name.substring(0, name.length() - 1);
                    StdDraw.text(45, 15, "Name: " + name);
                    StdDraw.text(45, 14, "(name saved)");
                    StdDraw.show();
                    continue;
                } else if (c == 'n') {
                    StdDraw.text(45, 22, "Seed: ");
                    StdDraw.show();
                    while (c != 's') {
                        if (StdDraw.hasNextKeyTyped()) {
                            c = StdDraw.nextKeyTyped();
                            c = Character.toLowerCase(c);
                            saveData += c;
                            drawRec(22);
                            if (saveData.substring(0, 1).equals("c")) {
                                saveData = saveData.substring(1);
                            }
                            StdDraw.text(45, 22, "Seed: " + saveData.substring(1));
                            StdDraw.show();
                        }
                    }
                    StdDraw.clear(StdDraw.BLACK);
                    String[] input = {"-s", saveData};
                    Main.loadMainArgs(this, saveData, name);
                } else if (c == 'l') {
                    String s = readContentsAsString(SAVED_FILE);
                    String[] args = s.split("@");
                    if (args.length == 1) {
                        Main.loadMainArgs(this, args[0], "");
                    } else {
                        Main.loadMainArgs(this, args[0], args[1]);
                    }
                }
                if (worldCreated) {
                    worldCreation(c);
                }
            }
            if (worldCreated) {
                draw();
            }
        }
    }

    public void worldCreation(char c) {
        if (isColon && c == 'q') {
            File s = Utils.join(SAVED_FILE);
            System.out.println(saveData);
            Utils.writeContents(s, saveData.substring(0,
                    saveData.length() - 2) + "@" + name);
            System.exit(0);
        } else {
            isColon = false;
        }
        if (c == ':') {
            isColon = true;
        }
        if (c == 'g') {
            limitedVision = !limitedVision;
        } else {
            playerOne.move(c);
            moves++;
        }
        if (limitedVision) {
            Main.getTer().renderFrame(generateWorld(playerOne.getX() - 2,
                    playerOne.getX() + 2, playerOne.getY() - 2,
                    playerOne.getY() + 2, worldGenerator.getTiles()));
            draw();
        } else {
            Main.getTer().renderFrame(generateWorld(0, 90,
                    0, 43, worldGenerator.getTiles()));
            draw();
        }
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

        String worldGen;
        String moveCommands;
        input = input.toLowerCase();
        if (input.charAt(0) == 'l') {
            String contents = readContentsAsString(SAVED_FILE);
            contents += input.substring(1);
            return interactWithInputString(contents);

        }
        if (input.charAt(0) == 'n') {
            worldCreated = true;
            worldGen = input.substring(1, input.indexOf('s'));
            System.out.println(Long.parseLong(worldGen));
            Random r = new Random(Long.parseLong(worldGen));
            worldGenerator.initializeWorld(r);
            playerOne = new PlayerController(r, worldGenerator);
            for (int i = 0; i < 10; i++) {
                collectibles[i] = new Collectibles(r, worldGenerator);
            }
            CountDown counter = new CountDown();
        }
        if (!input.substring(input.indexOf('s') + 1).equals("")) {
            moveCommands = input.substring(input.indexOf('s') + 1);
            for (int i = 0; i < moveCommands.length(); i++) {
                playerOne.move(moveCommands.charAt(i));
            }
        }
        if (input.contains(":q")) {
            File s = Utils.join(SAVED_FILE);
            //System.out.println(input.substring(0, input.length() - 2));
            Utils.writeContents(s, input.substring(0, input.length() - 2));
            //System.out.println(Utils.readContentsAsString(s));
            quitState = true;

        }
        saveData = input;
        return generateWorld(0, 90,
                0, 43, worldGenerator.getTiles());

    }

    public class CountDown {
        Timer timer;
        public CountDown() {
            timer = new Timer();
            timer.schedule(new App(), 0, 1000);
        }
        class App extends TimerTask {
            int countdown = 60;

            public void run() {
                addTimer(countdown);
                if (countdown != 0) {
                    countdown = countdown - 1;
                }
            }

        }
    }


}
