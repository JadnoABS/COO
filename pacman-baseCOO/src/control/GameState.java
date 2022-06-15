package control;

import elements.Element;
import utils.Stage;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private Stage stage;
    private ArrayList<Element> elemArray;


    public GameState(Stage stage, ArrayList<Element> elemArray) {
        this.stage = stage;
        this.elemArray = elemArray;
    }

    public ArrayList<Element> getElemArray() {
        return elemArray;
    }

    public void setElemArray(ArrayList<Element> elemArray) {
        this.elemArray = elemArray;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
