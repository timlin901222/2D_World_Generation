package byow.Core;

import byow.TileEngine.Tileset;

import java.util.Random;

import static byow.Core.RandomUtils.uniform;

public class PlayerController {
    private int x;
    private int y;
    private WorldGenerator world;
    private int score;

    public PlayerController(Random r, WorldGenerator world) {
        score = 0;
        int tempx = uniform(r, 0, 90);
        int tempy = uniform(r, 0, 43);
        while (world.getTiles()[tempx][tempy] != Tileset.FLOOR) {
            tempx = uniform(r, 0, 90);
            tempy = uniform(r, 0, 43);
        }
        this.x = tempx;
        this.y = tempy;
        this.world = world;
        world.movePlayer(x, y, "CREATE");
    }
    public boolean win() {
        return score == 10;
    }
    public void move(char c) {
        if (c == 'w') {
            if (!world.findTile(x, y + 1).equals("Wall")) {
                if (world.findTile(x, y + 1).equals("Water")) {
                    score++;
                }
                world.movePlayer(x, y, "UP");
                y++;
            }
        } else if (c == 'a') {
            if (!world.findTile(x - 1, y).equals("Wall")) {
                if (world.findTile(x - 1, y).equals("Water")) {
                    score++;
                }
                world.movePlayer(x, y, "LEFT");
                x--;
            }
        } else if (c == 's') {
            if (!world.findTile(x, y - 1).equals("Wall")) {
                if (world.findTile(x, y - 1).equals("Water")) {
                    score++;
                }
                world.movePlayer(x, y, "DOWN");
                y--;
            }
        } else if (c == 'd') {
            if (!world.findTile(x + 1, y).equals("Wall")) {
                if (world.findTile(x + 1, y).equals("Water")) {
                    score++;
                }
                world.movePlayer(x, y, "RIGHT");
                x++;
            }
        }

    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
