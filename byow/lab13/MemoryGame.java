package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import static edu.princeton.cs.algs4.StdDraw.*;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver = false;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }
        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String s = "";
        for (int i = 0; i < n; i++) {
            s = s + Character.toString(CHARACTERS[this.rand.nextInt(20)%10]);
        }
        return s;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.textLeft(0, 0, "Round: " + round);
        if (playerTurn) {
            StdDraw.text(0, 7, "Type!");
        }
        else {
            StdDraw.text(0, 7, "Watch!");
        }
        int msgNum = (int) (Math.random() * 7);
        StdDraw.textRight(0, 15, ENCOURAGEMENT[msgNum]);
        StdDraw.text(7, 7, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        char[] letterarray = letters.toCharArray();
        for (int i = 0; i < letterarray.length; i++) {
            drawFrame(Character.toString(letterarray[i]));
            pause(1000);
            drawFrame("");
            pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String s ="";
        for (int i = 0; i < n; i++) {
            s = s + nextKeyTyped();
        }
        return s;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;

        //TODO: Establish Engine loop
        while (!gameOver) {
            String word = generateRandomString(round);
            flashSequence(word);
            while (true) {
                if (!hasNextKeyTyped()){
                    continue;
                }
                playerTurn = true;
                if (solicitNCharsInput(round).equals(word)) {
                    drawFrame("Game Over! You made it to round: " + round);
                    gameOver = true;
                }
                else {
                    round++;
                }
            }

        }
    }

}
