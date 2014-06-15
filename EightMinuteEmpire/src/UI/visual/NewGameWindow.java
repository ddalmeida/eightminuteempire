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
import javax.swing.JLabel;
import javax.swing.JTextField;
import logic.game.Game;

public final class NewGameWindow extends JFrame {

    JLabel numberPlayersLabel;
    JTextField numberPlayersTextField;
    JButton confirmNumberPlayers;
    int x;
    int y;
    Game game;
    JFrame window = this;
    int number;

    public NewGameWindow(int x, int y, String title, Game game) {
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
        numberPlayersLabel = new JLabel("How many players?");
        numberPlayersTextField = new JTextField();
        confirmNumberPlayers = new JButton("Next");
        confirmNumberPlayers.setMinimumSize(new Dimension(80, 27));
        confirmNumberPlayers.setPreferredSize(new Dimension(80, 27));
        confirmNumberPlayers.setMaximumSize(new Dimension(80, 27));
    }

    public void setComponents() {
        Container cp = getContentPane();

        cp.setLayout(new GridLayout(1, 1, 10, 10));
        cp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        cp.add(numberPlayersLabel);
        cp.add(numberPlayersTextField);
        cp.add(confirmNumberPlayers);
    }

    public void addListeners() {
        confirmNumberPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(numberPlayersTextField.getText()) > 1 || Integer.parseInt(numberPlayersTextField.getText()) < 6) {
                    window.dispose();
                    window = new SetPlayersWindow(x, y, "Name the Players", game, number);
                    window.setVisible(true);
                }
            }
        });
    }
}