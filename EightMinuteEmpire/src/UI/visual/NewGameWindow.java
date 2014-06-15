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
import logic.game.Model;

public final class NewGameWindow extends JFrame {

      JLabel numberPlayersLabel;
      JTextField numberPlayersTextField;
      JButton confirmNumberPlayers;
      int x;
      int y;
      Model model;
      JFrame window = this;
      int number;
      boolean validPlayers;

      public NewGameWindow(int x, int y, String title, Model model) {
            super(title);
            this.x = x;
            this.y = y;
            this.model = model;
            validPlayers = false;
            number = 0;
            setLocation(x, y);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setSize(800, 600);
            buildComponents();
            setComponents();
            addListeners();
      }

      public void buildComponents() {
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
                        validPlayers = false;
                        try {
                              number = Integer.parseInt(numberPlayersTextField.getText());
                        } catch (NumberFormatException ex) {
                              number = 0;
                        }
                        if (number <= 1 || number >= 10) {
                              validPlayers = false;
                        } else {
                              validPlayers = true;
                        }

                        if (validPlayers == true) {
                              window.dispose();
                              window = new SetPlayersWindow(x, y, "Name the Players", model, number);
                              window.setVisible(true);
                        }
                  }
            });
      }
}
