package UI.visual;

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
import logic.game.Model;

public final class MenuWindow extends JFrame {

    private JButton newGame;
    private JButton loadGame;
    private int x;
    private int y;
   private Model model;
    private JFrame window = this;

    public MenuWindow(int x, int y, String title, Model model) {
        super(title);
        setLocation(x, y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        buildComponents();
        setComponents();
        addListeners();
        this.x = x;
        this.y = y;
        this.model = model;
    }

    public void buildComponents() {
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
                window = new NewGameWindow(x, y, "New Game", model);
                window.setVisible(true);
            }
        });
        
        loadGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                window = new LoadGameWindow(x, y, "Load Game", model);
                window.setVisible(true);
            }
        });
    }
}