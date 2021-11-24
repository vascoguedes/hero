import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    private static Screen screen;
    Arena arena = new Arena(40, 20);

    public Game() {
        try {
            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean processKey(KeyStroke key) {
        return arena.processKey(key);
    }

    public void run() {

        boolean go = true;
        TextGraphics graphics = screen.newTextGraphics();

        while (go) {
            try {
                screen.clear();
                arena.draw(graphics);
                screen.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                KeyStroke key = screen.readInput();
                go = processKey(key);
                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                    go = false;
                    screen.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
