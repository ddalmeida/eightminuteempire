package UI.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class GameWindow extends JFrame {

    JPanel mainLeft;
    JPanel mainBottom;
    JPanel mainPanel;
    JButton useCard;
    JButton endTurn;
    List<JLabel> players;

    public GameWindow(int x, int y, String title) {     // RECEBE Game
        super(title);
        setLocation(x, y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(656, 450);
        buildComponents();                              // RECEBE Game
        setComponents();
        addListeners();
    }

    public void buildComponents() {
        useCard = new JButton("Use Card");
        useCard.setSize(80, 27);

        endTurn = new JButton("End Turn");
        endTurn.setSize(80, 27);

        // INICIALIZA Game.getPlayers().size() LABELS

        mainPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
            }
        };
        mainPanel.setBackground(Color.red);

        mainBottom = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
            }
        };
        mainBottom.setBackground(Color.blue);
        mainBottom.setSize(526, 107);

        mainLeft = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
            }
        };
        mainLeft.setBackground(Color.green);
        mainLeft.setSize(120, 427);
    }

    public void setComponents() {
        Container cp = getContentPane();

        cp.setLayout(new BorderLayout());
        cp.add(mainPanel, BorderLayout.CENTER);
        cp.add(mainBottom, BorderLayout.PAGE_END);
        cp.add(mainLeft, BorderLayout.LINE_START);
        // DISPOR LABELS PROGRESSIVAMENTE, DEPENDE DO NUMPLAYERS
    }

    public void addListeners() {
    }
}