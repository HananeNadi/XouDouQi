/*
package com.ensah.Interface;

public class ContinueLocal {
}

 */
package com.ensah.Interface;

import com.ensah.board.BoardLocal;
import com.ensah.board.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ContinueLocal extends JPanel{
    private BufferedImage background;
    private final JButton newButton,ContinueButton;
    private final JButton homeButton,infoButton,exitButton;


    public ContinueLocal() {

        loadBackgroundImage();

        setLayout(null); // Use null layout for custom positioning of components

        // Create  buttons with images
        newButton = createButton("/continue/new.png", 190, 330, 141, 53);
        ContinueButton = createButton("/continue/continue.png", 190, 400, 141, 53);
        homeButton = createButton("/home.png",30, 370, 32, 32);
        infoButton =createButton("/info.png",30, 410, 32, 32);
        exitButton =createButton("/exit.png",30, 450, 32, 32);

        // Add buttons to the panel
        add(newButton);
        add(ContinueButton);
        add(homeButton);
        add(infoButton);
        add(exitButton);

        // Set the preferred size of the panel to match the background image size
        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
    }

    private void loadBackgroundImage() {
        try {
            background = ImageIO.read(Objects.requireNonNull(Menu.class.getResourceAsStream("/continue/continue_ground.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String imagePath, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource(imagePath))));
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));

        button.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e){
                if (button == newButton) {
                    new Display();

                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(newButton);
                    currentFrame.dispose();
                }
                if (button == ContinueButton) {
                    JFrame frame = new JFrame();
                    frame.setSize(850, 612);
                    frame.setResizable(false);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    BoardLocal boardLocal = new BoardLocal(new Player("1", false), new Player("2", true), "src\\main\\java\\com\\ensah\\utils\\loadBoard.txt");
                    boardLocal.addAnimals();
                    frame.add(boardLocal);
                    boardLocal.actions(frame);
                    frame.setTitle("Xou Dou Qi game");
                    frame.setVisible(true);
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(ContinueButton);
                    currentFrame.dispose();

                }
                if (button == homeButton) {

                    JFrame frame = new JFrame("Xou DOU qi");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().add(new Menu());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(homeButton);
                    currentFrame.dispose();

                }
                if (button == infoButton) {
                    JFrame frame = new JFrame("Game rules");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().add(new Rules());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(infoButton);
                    currentFrame.dispose();
                }
                if (button == exitButton) {
                    System.exit(0);

                }

            }


            @Override
            public void mouseEntered(MouseEvent e) {

                if (button == newButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/new.png"))));


                }
                if (button == ContinueButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/continue.png"))));

                }
                if (button == homeButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/home.png"))));
                }
                if (button == infoButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/info.png"))));
                }
                if (button == exitButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/exit.png"))));


                }

            }


            public void mouseExited(MouseEvent e) {
                if (button == newButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/continue/new.png"))));
                }
                if (button == ContinueButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/continue/continue.png"))));
                }
                if (button == homeButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/home.png"))));
                }

                if (button == infoButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/info.png"))));
                }
                if (button == exitButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/exit.png"))));


                }
            }
        });



        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }
}
