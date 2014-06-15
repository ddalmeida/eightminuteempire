package UI.visual;

import javax.swing.JFrame;
import logic.game.Game;

public class mainVisual {

    static Game game;
    static JFrame window;

    public static void main(String[] args) {
        window = new MenuWindow(50, 50, "Eight Minute Empire", game, window);
        window.setVisible(true);
    }
}