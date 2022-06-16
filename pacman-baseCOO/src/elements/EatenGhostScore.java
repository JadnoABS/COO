package elements;

import utils.Consts;
import utils.Position;

import java.awt.*;

public class EatenGhostScore extends Element {

    private Integer score;
    private Position pos;
    private long startTime;
    private long elapsedTime;

    public EatenGhostScore(int score, Position pos) {
        super(null);
        this.score = score;
        this.pos = pos;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void autoDraw(Graphics g) {
        g.drawString(score.toString(), (int)Math.round(pos.getY() * Consts.CELL_SIZE),(int)Math.round(pos.getX() * Consts.CELL_SIZE));
        elapsedTime = System.currentTimeMillis() - startTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
