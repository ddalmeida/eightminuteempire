package UI.visual;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JTextField;
import logic.game.Game;

public final class SetPlayersWindow extends JFrame {

    List<JLabel> playerNameLabel;
    List<JTextField> playerNameTextField;
    JButton confirmPlayers;
    int x;
    int y;
    Game game;
    JFrame window;
    int number;
    boolean allSet;

    public SetPlayersWindow(int x, int y, String title, Game game, JFrame window, int number) {
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
        this.window = window;
        this.number = number;
        allSet = false;
    }

    public void buildComponents(Game game) {
        for (int i = 0; i < number; i++) {
            playerNameLabel.add(new JLabel("Player " + (i + 1)));
            playerNameTextField.add(new JTextField());
        }

        confirmPlayers = new JButton("Confirm");
        confirmPlayers.setMinimumSize(new Dimension(80, 27));
        confirmPlayers.setPreferredSize(new Dimension(80, 27));
        confirmPlayers.setMaximumSize(new Dimension(80, 27));
    }

    public void setComponents() {
        Container cp = getContentPane();

        cp.setLayout(new GridLayout(2, number, 10, 10));
        cp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        for (int i = 0; i < number; i++) {
            cp.add(playerNameLabel.get(i));
            cp.add(playerNameTextField.get(i));
        }
    }

    public void addListeners() {
        confirmPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < number; i++) {
                    if (playerNameTextField.get(i).getText().equals("")) {
                        allSet = false;
                    } else {
                        allSet = true;
                    }
                }

                if (allSet) {
                    for (int i = 0; i < number; i++) {
                        game.addPlayer(playerNameTextField.get(i).getText());
                    }

                    window.dispose();
                    window = new GameWindow(x, y, "Name the Players", game, window);
                    window.setVisible(true);
                }
            }
        });
    }
}