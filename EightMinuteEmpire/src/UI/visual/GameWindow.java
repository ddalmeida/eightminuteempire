package UI.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logic.game.Game;

public final class GameWindow extends JFrame {

    JPanel mainLeft;
    JPanel mainBottom;
    JPanel mainPanel;
    JButton useCard;
    JButton endTurn;
    List<JLabel> players;
    List<JPanel> cards;
    BufferedImage image;
    boolean hasColor;
    Game game;
    JFrame window;

    public GameWindow(int x, int y, String title, Game game, JFrame window) {
        super(title);
        setLocation(x, y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1024, 600);
        buildComponents(game);
        setComponents();
        addListeners();
        hasColor = false;
        this.game = game;
        this.window = window;
    }

    public void buildComponents(Game game) {
        useCard = new JButton("Use Card");
        useCard.setPreferredSize(new Dimension(80, 27));

        endTurn = new JButton("End Turn");
        endTurn.setPreferredSize(new Dimension(80, 27));

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
        mainBottom.setBackground(Color.black);
        mainBottom.setPreferredSize(new Dimension(526, 177));

        mainLeft = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
            }
        };
        mainLeft.setBackground(Color.green);
        mainLeft.setPreferredSize(new Dimension(120, 427));

        cards = new ArrayList<>();

        try {
            image = ImageIO.read(new File("C:\\Users\\L\\Desktop\\ISEC\\2º Semestre\\Programação Avançada\\Trabalho Prático\\Projecto\\EightMinuteEmpire\\00_place_army_3.png"));
        } catch (IOException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < 6; i++) {
            JPanel jb = new JPanel() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    if (image != null) {
                        g.drawImage(image, 6, 0, 129, 178, null);
                    } else {
                        g.drawString("Image failed to load", 30, 30);
                    }
                }
            };
            jb.setBackground(Color.black);
            jb.setMinimumSize(new Dimension(128, 177));
            jb.setPreferredSize(new Dimension(128, 177));
            jb.setMaximumSize(new Dimension(128, 177));

            cards.add(jb);
        }
    }

    public void setComponents() {
        Container cp = getContentPane();

        cp.setLayout(new BorderLayout());
        cp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        cp.add(mainPanel, BorderLayout.CENTER);
        cp.add(mainLeft, BorderLayout.WEST);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(mainBottom, BorderLayout.SOUTH);

        mainBottom.setLayout(new GridLayout(1, 6, 10, 10));
        mainBottom.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        for (JPanel jb : cards) {
            mainBottom.add(jb);
        }

        // DISPOR LABELS PROGRESSIVAMENTE, DEPENDE DO NUMPLAYERS
    }

    public void addListeners() {
        for (final JPanel jb : cards) {
            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!hasColor) {
                        jb.setBackground(Color.red);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!hasColor) {
                        jb.setBackground(Color.black);
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (hasColor) {
                        jb.setBackground(Color.black);
                        hasColor = false;
                    } else {
                        jb.setBackground(Color.green);
                        hasColor = true;
                    }
                }
            });
        }
    }
}