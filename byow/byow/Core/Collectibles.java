package byow.Core;
import byow.TileEngine.Tileset;

import java.util.Random;

import static byow.Core.RandomUtils.uniform;

public class Collectibles {
    private int x;
    private int y;
    private WorldGenerator world;

    public Collectibles(Random r, WorldGenerator world) {
        int tempx = uniform(r, 0, 90);
        int tempy = uniform(r, 0, 43);
        while (world.getTiles()[tempx][tempy] != Tileset.FLOOR) {
            tempx = uniform(r, 0, 90);
            tempy = uniform(r, 0, 43);
        }
        this.x = tempx;
        this.y = tempy;
        this.world = world;
        world.createCollectibles(x, y);
    }
}
