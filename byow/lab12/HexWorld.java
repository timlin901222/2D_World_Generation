package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    public static void addHexagon(int col, int row, int sideLength, TETile[][] world) {
        for (int i = 0; i < sideLength; i++) {
            printRow(col, sideLength, i+row, world);

        }
        for (int i = sideLength - 1; i >= 0; i--) {
            printRow(col, sideLength, i+row, world);

        }
    }

    public static void printRow(int col, int sideLength, int row, TETile[][] world) {
        for (int i = 0; i < 3 * sideLength - 2; i++) {
            if (i < sideLength - 1 - row) {
                world[row][col] = Tileset.NOTHING;
            }
            else if (i > 2 * sideLength - 2 + row) {
                world[row][col] = Tileset.NOTHING;
            }
            else {
                world[row][col] = Tileset.FLOWER;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer HexWorld = new TERenderer();
        HexWorld.initialize(50, 50);
        TETile[][] world = new TETile[50][50];
        addHexagon(24, 0, 3, world);
    }
}
