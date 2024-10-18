package Game;

import java.util.Scanner;

import Entitys.Entity;
import Entitys.Player;
import Entitys.Tree;
import Utils.Constants;
import Utils.Point;
import Weapons.Fireball;
import Weapons.MagicRing;
import Weapons.Sword;

public class GameConsole {
    public Board board;

    public GameConsole(Board board) {
        this.board = board;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        putPlayersOnBoard();
        
        while (board.playersLeft() > 1) {
            printBoard();

            Player player = board.playerTurn();
            System.out.println(player.getName() + ", this is your turn, Player " + player.getPlayerNumber() + " Enter move (U/D/L/R): ");
            char move = scanner.next().toUpperCase().charAt(0);
            while((move != 'U' && move != 'D' && move != 'L' && move != 'R') || (!board.movePlayer(player, move))){
                System.out.println("Invalid move! Try again.");
                move = scanner.next().toUpperCase().charAt(0);
            }
            
            board.nextTurn();
        }
        printBoard();
        System.out.println("");
        System.out.println("Game Over! " + board.getPlayers().get(0).getName() + " is the winner!");

    }


    private void printBoard() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Entity entity = board.getEntity(new Point(x, y));
                if (entity == null) {
                    System.out.printf(" %-3s", "."); // Empty space with fixed width
                } else if (entity instanceof Tree) {
                    System.out.printf(" %-3s", Constants.TREE); // Tree
                } else if (entity instanceof Sword) {
                    System.out.printf(" %-3s", Constants.SWORD); // Sword
                } else if (entity instanceof Fireball) {
                    System.out.printf(" %-3s", Constants.FIRE_BALL); // Fireball
                } else if (entity instanceof MagicRing) {
                    System.out.printf(" %-3s", Constants.MAGIC_RING); // Magic Ring
                } else if (entity instanceof Player) {
                    // Print the player's number using fixed-width formatting
                    Player player = (Player) entity;
                    System.out.printf(" %-3d", player.getPlayerNumber()); // Player's number, left-aligned with width 3
                }
            }
            System.out.println(); // Move to the next row after printing a row of entities
        }
    }

    public void putPlayersOnBoard(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of players: ");
        int numOfPlayers = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.println(String.format("Enter player %d name: ", i + 1));
            String playerName = scanner.nextLine();
            Player player = new Player(playerName, i+1, board.getRandomEmptyPosition());
            board.addPlayer(player);
        }
    }

    
}
