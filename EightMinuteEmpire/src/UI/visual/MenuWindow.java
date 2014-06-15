package UI.visual;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import logic.game.Game;

public final class MenuWindow extends JFrame {

    JButton newGame;
    JButton loadGame;
    int x;
    int y;
    Game game;
    JFrame window = this;

    public MenuWindow(int x, int y, String title, Game game) {
        super(title);
        setLocation(x, y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        buildComponents(game);
        setComponents();
        addListeners();
        this.x = x;
        this.y = y;
        this.game = game;
    }

    public void buildComponents(Game game) {
        newGame = new JButton("New Game");
        newGame.setMinimumSize(new Dimension(80, 27));
        newGame.setPreferredSize(new Dimension(80, 27));
        newGame.setMaximumSize(new Dimension(80, 27));
        
        loadGame = new JButton("Load Game");
        loadGame.setMinimumSize(new Dimension(80, 27));
        loadGame.setPreferredSize(new Dimension(80, 27));
        loadGame.setMaximumSize(new Dimension(80, 27));
    }

    public void setComponents() {
        Container cp = getContentPane();

        cp.setLayout(new GridLayout(2,1));
        cp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        cp.add(newGame);
        cp.add(loadGame);
    }

    public void addListeners() {
        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                window = new NewGameWindow(x, y, "New Game", game);
                window.setVisible(true);
            }
        });
        
        loadGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                window = new LoadGameWindow(x, y, "Load Game", game);
                window.setVisible(true);
            }
        });
    }
}