package UI.visual;

import java.awt.Checkbox;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JTextField;
import logic.game.Model;

public final class SetPlayersWindow extends JFrame implements Observer {

      private ArrayList<JLabel> playerNameLabel = new ArrayList<>();
      private ArrayList<JTextField> playerNameTextField = new ArrayList<>();
      private ArrayList<JCheckBox> playerAI = new ArrayList<>();
      private JButton confirmPlayers;
      private int x;
      private int y;
      private Model model;
      private JFrame window = this;
      private int number;
      private boolean allSet;

      public SetPlayersWindow(int x, int y, String title, Model model, int number) {
            super(title);
            this.x = x;
            this.y = y;
            this.model = model;
            this.number = number;
            setLocation(x, y);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setSize(800, 600);
            buildComponents();
            setComponents();
            addListeners();
            model.addObserver(this);
            allSet = false;
      }

      public void buildComponents() {
            for (int i = 0; i < number; i++) {
                  playerNameLabel.add(new JLabel("Player " + (i + 1)));
                  playerNameTextField.add(new JTextField());
                  playerAI.add(new JCheckBox("AI?"));
            }

            confirmPlayers = new JButton("Confirm");
            confirmPlayers.setMinimumSize(new Dimension(80, 27));
            confirmPlayers.setPreferredSize(new Dimension(80, 27));
            confirmPlayers.setMaximumSize(new Dimension(80, 27));
      }

      public void setComponents() {
            Container cp = getContentPane();

            cp.setLayout(new GridLayout(number + 1, 3, 10, 10));
            cp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

            for (int i = 0; i < number; i++) {
                  cp.add(playerNameLabel.get(i));
                  cp.add(playerNameTextField.get(i));
                  cp.add(playerAI.get(i));
            }

            cp.add(confirmPlayers);
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

                        if (allSet == true) {
                              for (int i = 0; i < number; i++) {
                                    model.addPlayer(playerNameTextField.get(i).getText());
                              }

                              window.dispose();
                              window = new GameWindow(x, y, "Eight Minute Empire", model);
                              window.setVisible(true);
                        }
                  }
            });
      }

      @Override
      public void update(Observable o, Object arg) {
          
      }
}
