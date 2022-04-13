package byow.Core;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static byow.Core.RandomUtils.uniform;

public class WorldGenerator {
    private static final int PANEL_WIDTH = 90;
    private static final int PANEL_HEIGHT = 43;
    private TETile[][] tiles;

    public TETile[][] getTiles() {
        return tiles;
    }
    public WorldGenerator() {
        tiles = new TETile[90][45]; //y is 43 so that it doesn't go out of bounds
    }

    public void createCollectibles(int x, int y) {
        tiles[x][y] = Tileset.WATER;
    }

    public void movePlayer(int x, int y, String direction) {
        switch (direction) {
            case "UP":
                tiles[x][y + 1] = Tileset.FLOWER;
                tiles[x][y] = Tileset.FLOOR;
                break;
            case "LEFT":
                tiles[x - 1][y] = Tileset.FLOWER;
                tiles[x][y] = Tileset.FLOOR;
                break;

            case "DOWN":
                tiles[x][y - 1] = Tileset.FLOWER;
                tiles[x][y] = Tileset.FLOOR;
                break;

            case "RIGHT":
                tiles[x + 1][y] = Tileset.FLOWER;
                tiles[x][y] = Tileset.FLOOR;
                break;
            default:
                tiles[x][y] = Tileset.FLOWER;
        }
    }

    public String findTile(int x, int y) {
        if (tiles[x][y].equals(Tileset.WALL)) {
            return "Wall";
        } else if (tiles[x][y].equals(Tileset.FLOOR)) {
            return "Floor";
        } else if (tiles[x][y].equals(Tileset.NOTHING)) {
            return "Nothing";
        } else if (tiles[x][y].equals(Tileset.FLOWER)) {
            return "Avatar";
        } else if (tiles[x][y].equals(Tileset.WATER)) {
            return "Water";
        } else {
            return "error";
        }
    }

    public void fillWithNothingTiles() {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public TETile[][] initializeWorld(Random r) {
        fillWithNothingTiles();
        int count = 0;

        int rooms = uniform(r, 4, 10); //20,21...28,29
        //System.out.println(rooms);
        List<int[]> roomarray = new ArrayList<>();
        while (count < rooms) {
            int x = uniform(r, 2, PANEL_WIDTH - 13);
            int y = uniform(r, 2, PANEL_HEIGHT - 13);
            int wr = uniform(r, 4, 9); //2..6,7
            int hr = uniform(r, 4, 9); //2..6,7

            if (generateRoom(x, y, wr, hr, 1)) {
                count++;
                int[] attr = {x, y, wr, hr};
                roomarray.add(attr);
            }
        }

        for (int i = 1; i < roomarray.size(); i++) {
            connectRoom(r, i - 1, i, roomarray);
        }
        generateWall();
        return tiles;
    }

    public boolean generateRoom(int x, int y, int width, int height, int type) {
        for (int i = x - type; i <= x + width - (1 - type); i++) {
            for (int j = y - type; j <= y + height - (1 - type); j++) {
                if (i >= PANEL_WIDTH - 2 || j >= PANEL_HEIGHT - 2
                        || i < 0 || j < 0) { //i cannot be bigger than window size
                    break;
                }
                if (!tiles[i][j].equals(Tileset.NOTHING)) { //fail condition
                    return false;
                }
            }
        }
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (i >= PANEL_WIDTH - 2 || j >= PANEL_HEIGHT - 2 || i < 0 || j < 0) {
                    break;
                }
                tiles[i][j] = Tileset.FLOOR;
            }
        }
        return true;
    }

    public void generateWall() {
        for (int i = 1; i < PANEL_WIDTH - 1; i++) {
            for (int j = 1; j < PANEL_HEIGHT - 1; j++) {
                if (tiles[i][j].equals(Tileset.NOTHING)) {
                    if (tiles[i + 1][j].equals(Tileset.FLOOR)
                            || tiles[i][j + 1].equals(Tileset.FLOOR)
                            || tiles[i - 1][j].equals(Tileset.FLOOR)
                            || tiles[i][j - 1].equals(Tileset.FLOOR)
                            || tiles[i - 1][j - 1].equals(Tileset.FLOOR)
                            || tiles[i + 1][j - 1].equals(Tileset.FLOOR)
                            || tiles[i - 1][j + 1].equals(Tileset.FLOOR)
                            || tiles[i + 1][j + 1].equals(Tileset.FLOOR)) {
                        tiles[i][j] = Tileset.WALL;
                    }
                }
            }
        }
    }

    public void connectRoom(Random r, int room1, int room2, List<int[]> roomarray) {
        int[] r1 = roomarray.get(room1);
        int[] r2 = roomarray.get(room2);
        int xUpperbound;
        int xLowerbound;
        int yUpperbound;
        int yLowerbound;

        if (r1[0] > r2[0]) {
            xUpperbound = r1[0] + r1[2];
        } else {
            xUpperbound = r2[0] + r2[2];
        }
        xLowerbound = Math.min(r1[0], r2[0]);

        if (r1[1] > r2[1]) {
            yUpperbound = r1[1] + r1[3];
        } else {
            yUpperbound = r2[1] + r2[3];
        }
        yLowerbound = Math.min(r1[1], r2[1]);

        // randomly generates a valid path between the bounds
        int xPos = uniform(r, r2[0], r2[0] + r2[2]);
        int yPos = uniform(r, r1[1], r1[1] + r1[3]);

        generateHorizontalPath(xLowerbound, yPos, xUpperbound);
        generateVerticalPath(xPos, yLowerbound, yUpperbound);
    }

    public void generateHorizontalPath(int xLower, int yPos, int xUpper) {
        for (int i = xLower; i < xUpper; i++) {
            tiles[i][yPos] = Tileset.FLOOR;
        }
    }

    public void generateVerticalPath(int xPos, int yLower, int yUpper) {
        for (int j = yLower; j < yUpper; j++) {
            tiles[xPos][j] = Tileset.FLOOR;
        }
    }
}
