import java.util.Random;

public class Monster extends Element{

    public Monster(int x, int y){
        super(x,y);
    }
    public Position move(){
        Random random = new Random();
        return new Position((int)(position.getX()+(random.nextInt(2)) * (Math.pow((-1),
                (random.nextInt(2))))),(int)(position.getY()+(random.nextInt(2)) * (Math.pow((-1),(random.nextInt(2))))));
    }
}