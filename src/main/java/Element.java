import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Element {

    protected Position position;

    public Element(int x, int y){
        position = new Position(x,y);
    }
    public void draw(TextGraphics graphics,String ForegroundColor,String Char) {
        graphics.setForegroundColor(TextColor.Factory.fromString(ForegroundColor));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), Char);
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
}