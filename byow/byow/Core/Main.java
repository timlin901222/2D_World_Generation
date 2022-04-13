package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byow.Core.Engine class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    private static Engine engine;
    private static TERenderer ter;
    public static TERenderer getTer() {
        return ter;
    }
    public static void main(String[] args) {
        ter = new TERenderer();
        ter.initialize(90, 43);
        engine = new Engine();
        if (args.length > 2) {
            System.out.println("Can only have two arguments - the flag and input string");
            System.exit(0);
        } else if (args.length == 2 && args[0].equals("-s")) {
            loadMainArgs(engine, args[1], "");

            // DO NOT CHANGE THESE LINES YET ;)
        } else if (args.length == 2 && args[0].equals("-p")) {
            System.out.println("Coming soon.");
            
            // DO NOT CHANGE THESE LINES YET ;)
        } else {
            engine.interactWithKeyboard();
        }
    }
    public static void loadMainArgs(Engine engine, String saveData, String name) {
        TETile[][] worldTiles = engine.interactWithInputString(saveData);
        if (engine.getQuitState()) {
            System.exit(0);
        }
        ter.renderFrame(worldTiles);
        engine.setName(name);
        engine.draw();
        System.out.println(engine.getName());
        System.out.println(engine.toString());
        String coordinate = engine.findCoordinate();

        engine.interactWithKeyboard();

    }

}
