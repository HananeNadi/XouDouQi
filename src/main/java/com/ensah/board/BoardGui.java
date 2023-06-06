package com.ensah.board;

import com.ensah.animals.*;
import com.ensah.utils.LoadSaveBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public abstract class BoardGui extends JPanel {
    public static ArrayList<Animal> animals = new ArrayList<>();
    static Animal selectedAnimal;
    public static Player player1;
    public static Player player2;
    public Player playerWin;
    static ArrayList<Position> possibleMoves = new ArrayList<>();
    static final int carree=64;


    public BoardGui(Player player1, Player player2){
        this.setPreferredSize(new Dimension(7*64,9*64));
        BoardLocal.player1 = player1;
        BoardLocal.player2 = player2;

    }

    public void paintComponent(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        Image ground;
        try {
            ground = ImageIO.read(new File("src\\main\\java\\com\\ensah\\media\\ground.png")).getScaledInstance(7*carree, 9*carree, BufferedImage.SCALE_SMOOTH);
            g.drawImage(ground, 0, 0, this);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }


        Image river;
        try {
            river = ImageIO.read(new File("src\\main\\java\\com\\ensah\\media\\river1.png")).getScaledInstance(carree*2, carree*3, BufferedImage.SCALE_SMOOTH);
            g.drawImage(river, carree, 3*carree, this);
            g.drawImage(river, 4*carree, 3*carree, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Drawing board squares
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 7; x++) {
                if((x==1||x==2||x==4||x==5)&&(y==3||y==4||y==5)) {
                    g2D.setColor(new Color(35, 147, 200));
                    g2D.fillRect(x * carree, y * carree, carree, carree);
                }
                if (((x==2||x==4)&&(y==0||y==8))||((y==1||y==7)&&x==3)) {
                    g2D.setColor(new Color(182, 109, 109));
                    g2D.fillRect(x * carree, y * carree, carree, carree);
                }
                if (x==3&&(y==0||y==8)) {
                    g2D.setColor(new Color(84, 84, 84, 100));
                    g2D.fillRect(x * carree, y * carree, carree, carree);

                }

            }
        }
        g2D.setColor(Color.BLACK);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 7; x++) {
                int xPos = x * carree;
                int yPos = y * carree;
                g2D.drawRect(xPos, yPos, carree, carree);
            }
        }

        // Highlighting possible moves
        if (selectedAnimal != null) {
            g2D.setColor(new Color(68, 180, 57, 140));
            for (Position p : this.getMovedAnimalPossibleMoves(selectedAnimal)) {
                int r = p.getI();
                int c = p.getJ();
                g2D.fillRect(r * carree, c * carree, carree, carree);
            }
        }



        // Drawing animals
        for (Animal animal : animals) {
            g2D.drawImage(animal.getImage(), animal.getPosition().getI()*carree, animal.getPosition().getJ()*carree, this);
        }


    }

    public void addAnimals(){
        try {
            String [][] newBoard = LoadSaveBoard.loadGame("src\\main\\java\\com\\ensah\\utils\\newBoard.txt");
            for (int i=0;i<9;i++){
                for (int j=0;j<7;j++){
                    // Player 1_left
                    if (newBoard[i][j].equals("R")){animals.add(new Rat(player1,new Position(j,i),true));}
                    if (newBoard[i][j].equals("C")){animals.add(new Cat(player1,new Position(j,i),true));}
                    if (newBoard[i][j].equals("W")){animals.add(new Wolf(player1,new Position(j,i),true));}
                    if (newBoard[i][j].equals("D")){animals.add(new Dog(player1,new Position(j,i),true));}
                    if (newBoard[i][j].equals("P")){animals.add(new Leopard(player1,new Position(j,i),true));}
                    if (newBoard[i][j].equals("T")){animals.add(new Tiger(player1,new Position(j,i),true));}
                    if (newBoard[i][j].equals("L")){animals.add(new Lion(player1,new Position(j,i),true));}
                    if (newBoard[i][j].equals("E")){animals.add(new Elephant(player1,new Position(j,i),true));}
                    // Player2_right
                    if (newBoard[i][j].equals("r")){animals.add(new Rat(new Position(j,i),true,player2));}
                    if (newBoard[i][j].equals("c")){animals.add(new Cat(new Position(j,i),true,player2));}
                    if (newBoard[i][j].equals("w")){animals.add(new Wolf(new Position(j,i),true,player2));}
                    if (newBoard[i][j].equals("d")){animals.add(new Dog(new Position(j,i),true,player2));}
                    if (newBoard[i][j].equals("p")){animals.add(new Leopard(new Position(j,i),true,player2));}
                    if (newBoard[i][j].equals("t")){animals.add(new Tiger(new Position(j,i),true,player2));}
                    if (newBoard[i][j].equals("l")){animals.add(new Lion(new Position(j,i),true,player2));}
                    if (newBoard[i][j].equals("e")){animals.add(new Elephant(new Position(j,i),true,player2));}

                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
            // add animals init position if error
        }
    }

    public Animal getMovedAnimal(int x, int y){
        Position pA = new Position(x/carree, y/carree);
        for (Animal animal:animals){
            if(animal.getPosition().equals(pA)){
                return animal;
            }
        }
        return null;
    }

    public ArrayList<Position> getMovedAnimalPossibleMoves(Animal animal){
        possibleMoves.clear();
        if (animal.getPlayer().isTurn()) {
            possibleMoves.addAll(animal.checkMovements(animal));
        }
        return possibleMoves;
    }

    public void changePlayer() {
        if (player1.isTurn()) {
            player1.setTurn(false);
            player2.setTurn(true);
        } else {
            player2.setTurn(false);
            player1.setTurn(true);

        }
    }

    public Player endGame(){
        if(selectedAnimal.getPosition().equals(new Position(3,8)) && selectedAnimal.getPlayer().getUsername().equals("1")){
            System.out.println("CheckMate P1 wins");
            return player1;
        }
        if(selectedAnimal.getPosition().equals(new Position(3,0)) && selectedAnimal.getPlayer().getUsername().equals("2")){
            System.out.println("CheckMate P2 wins");
            return player2;
        }
        return null;
    }

    public abstract void actions(JFrame frame);

}