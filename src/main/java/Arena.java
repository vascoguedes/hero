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
import java.util.List;
import java.util.Random;


public class Arena {
    private int width, height;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;
    private int score = 0;
    Hero hero = new Hero(10, 10);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }


    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) +
                    1, random.nextInt(height - 2) + 1));
        return coins;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
            if(monsters.get(i).getPosition().equals(hero.getPosition())){
                monsters.remove(i);
                i = i-1;
            }
        }
        return monsters;
    }

    private boolean canHeroMove(Position position) {
        return position.getX() < this.width - 1 && position.getX() >= 1 && position.getY() < this.height - 1 && position.getY() >= 1;
    }

    private void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
            retrieveCoins(position);
            moveMonsters();
        }
    }

    public boolean checkIfAlive(){
        for (Monster monster : monsters){
            if(monster.getPosition().equals(hero.getPosition())){
                return false;
            }
        }
        return true;
    }

    private boolean verifyMonsterCollisions(Monster m,Position position){
        for (Monster monster : monsters){
            if(monster.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    private void  moveMonsters(){
        for (Monster monster : monsters){
            Position nexPos = monster.move();
            for (Wall wall : walls){
                if (wall.getPosition().equals(nexPos)){
                    return;
                }
                if(!verifyMonsterCollisions(monster,nexPos)){
                    return;
                }
            }
            if(0 <= nexPos.getY() && nexPos.getY() <= height-1 && 0 <= nexPos.getX() && nexPos.getX() <= width-1){
                monster.setPosition(nexPos);
            }
        }
    }

    private void retrieveCoins(Position position) {

        for (int j = 0; j < coins.size(); j++) {
            if (coins.get(j).getPosition().equals(position)) {
                coins.remove(j);
                score++;
            }
        }
    }

    public boolean processKey(KeyStroke key) {
        //System.out.println(key);
        if (key.getKeyType() == KeyType.ArrowUp) {
            moveHero(hero.moveUp());
        }
        else if (key.getKeyType() == KeyType.ArrowDown) {
            moveHero(hero.moveDown());
        }
        else if (key.getKeyType() == KeyType.ArrowLeft) {
            moveHero(hero.moveLeft());
        }
        else if (key.getKeyType() == KeyType.ArrowRight) {
            moveHero(hero.moveRight());
        }
        return checkIfAlive();
    }

    public void draw(TextGraphics graphics) {

        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        for (Wall wall : walls)
            wall.draw(graphics,"#00ff26","*");
        for (Coin coin : coins)
            coin.draw(graphics,"#fcfcfc","O");
        for (Monster monster : monsters){
            monster.draw(graphics,"#f50000","R");
        }

        hero.draw(graphics, "#FFFF33","X");
    }

}
