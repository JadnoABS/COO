package test;

import org.junit.Test;
import static org.junit.Assert.*;

import elements.*;
import control.GameController;

import java.awt.*;
import java.util.ArrayList;


public class GameTest {

    @Test
    public void testPacman() {

        Pacman pacman = new Pacman("pacman.png");

        assertEquals(0, pacman.getScore());

        pacman.addScore(10000);
        assertEquals(10000, pacman.getScore());

        pacman.setPosition(10, 10);
        assertEquals(10, (int) pacman.getPos().getX());
        assertEquals(10, (int) pacman.getPos().getY());


        assertEquals(1, pacman.getLifes());
        pacman.addLife();
        assertEquals(2, pacman.getLifes());

    }

}