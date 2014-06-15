package UI.visual;

import javax.swing.JFrame;
import logic.game.Game;
import logic.game.Model;
import logic.map.Board;

public class mainVisual {

      static Game game;
      static JFrame window;

      public static void main(String[] args) {
            Board board = new Board();
            Game game = new Game(board);
            Model model = new Model(game);

            window = new MenuWindow(50, 50, "Eight Minute Empire", model);
            window.setVisible(true);
      }
}
