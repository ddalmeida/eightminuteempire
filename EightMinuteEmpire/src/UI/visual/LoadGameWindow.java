package UI.visual;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import logic.game.Game;

public final class LoadGameWindow extends JFrame {

    JLabel fileNameLabel;
    JTextField fileNameTextField;
    JButton confirm;
    JButton cancel;
    JPanel main;
    JPanel buttons;
    int x;
    int y;
    Game game;
    JFrame window = this;
    boolean success;

    public LoadGameWindow(int x, int y, String title, Game game) {
        super(title);
        setLocation(x, y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1024, 600);
        buildComponents(game);
        setComponents();
        addListeners();
        this.x = x;
        this.y = y;
        this.game = game;
        success = true;
    }

    public void buildComponents(Game game) {
        fileNameLabel = new JLabel("File Name?");
        fileNameTextField = new JTextField();
        confirm = new JButton("Confirm");
        confirm.setMinimumSize(new Dimension(80, 27));
        confirm.setPreferredSize(new Dimension(80, 27));
        confirm.setMaximumSize(new Dimension(80, 27));
        cancel = new JButton("Cancel");
        cancel.setMinimumSize(new Dimension(80, 27));
        cancel.setPreferredSize(new Dimension(80, 27));
        cancel.setMaximumSize(new Dimension(80, 27));
        main = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
            }
        };
        buttons = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
            }
        };
    }

    public void setComponents() {
        Container cp = getContentPane();

        cp.setLayout(new BorderLayout());
        cp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        cp.add(main, BorderLayout.CENTER);
        cp.add(buttons, BorderLayout.SOUTH);

        main.setLayout(new GridLayout(1, 2, 10, 10));
        main.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        main.add(fileNameLabel);
        main.add(fileNameTextField);

        buttons.setLayout(new GridLayout(2, 1, 10, 10));
        buttons.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        main.add(confirm);
        main.add(cancel);
    }

    public void addListeners() {
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                success = true;
                if (fileNameTextField.getText().equals("")) {
                } else {
                    try {
                        FileInputStream fis = new FileInputStream(fileNameTextField.getText() + ".sav");
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        game = (Game) ois.readObject();
                    } catch (Exception ex) {
                        success = false;
                    }
                    if (success == true) {
                        window.setVisible(false);
                        window = new GameWindow(x, y, "Eight Minute Empire", game);
                        window.setVisible(true);
                    }
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                window = new MenuWindow(x, y, "Eight Minute Empire", game);
                window.setVisible(true);
            }
        });
    }
}