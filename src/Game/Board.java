package Game;
import java.util.ArrayList;
import java.util.Random;

import Entitys.Entity;
import Entitys.Player;
import Entitys.Tree;
import Entitys.Weapon;
import Utils.Point;
import Weapons.Fireball;
import Weapons.MagicRing;
import Weapons.Sword;

public class Board {
    private Entity[][] board;
    private ArrayList<Player> players;
    private static int playerTurn = 0;

    public Board() {
        board = new Entity[10][10];
        players = new ArrayList<>();
        generateBoard();
    }

    private void generateBoard() {
        putWeaponsAndTrees();
    }

    private void putWeaponsAndTrees() {
        // Place 3 trees
        for (int i = 0; i < 3; i++) {
            Point p = getRandomEmptyPosition();
            Tree tree = new Tree(p.getX(), p.getY());
            addEntity(tree);
        }

        // Place 2 Fireballs
        for (int i = 0; i < 2; i++) {
            Point p = getRandomEmptyPosition();
            Fireball fireball = new Fireball(p.getX(), p.getY());
            addEntity(fireball);
        }

        // Place 2 Magic Rings
        for (int i = 0; i < 2; i++) {
            Point p = getRandomEmptyPosition();
            MagicRing ring = new MagicRing(p.getX(), p.getY());
            addEntity(ring);
        }

        // Place 2 Swords
        for (int i = 0; i < 2; i++) {
            Point p = getRandomEmptyPosition();
            Sword sword = new Sword(p.getX(), p.getY());
            addEntity(sword);
        }
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }
    public boolean addPlayer(Player p) {
        if(addEntity(p)){
            players.add(p);
            return true;
        }
        return false;
    }

    public boolean addEntity(Entity e) {
        Point p = e.getPosition();
        if (isInBoard(p) && isEmpty(p)) {
            board[p.getX()][p.getY()] = e;
            return true;
        }
        return false;
    }

    public void deleteEntity(Point p) {
        if (isInBoard(p)) {
            if(getEntity(p) instanceof Player){
                players.remove(getEntity(p));
            }
            board[p.getX()][p.getY()] = null;
        }

    }

    public void replaceEntity(Point p1, Point p2) {
        if (isInBoard(p1) && isInBoard(p2)) {
            if(getEntity(p2) instanceof Player){
                players.remove(getEntity(p2));
            }
            board[p2.getX()][p2.getY()] = getEntity(p1);
            board[p2.getX()][p2.getY()].setPosition(p2);
            board[p1.getX()][p1.getY()] = null;
        }
    }

    public Entity getEntity(Point p) {
        if (isInBoard(p)) {
            return board[p.getX()][p.getY()];
        }
        return null;
    }

    public boolean isEmpty(Point p) {
        return isInBoard(p) && board[p.getX()][p.getY()] == null;
    }

    public boolean isThereTree(Point p) {
        return isInBoard(p) && board[p.getX()][p.getY()] instanceof Tree;
    }

    public boolean isInBoard(Point p) {
        return p.getX() >= 0 && p.getX() < 10 && p.getY() >= 0 && p.getY() < 10;
    }

    public Point getRandomEmptyPosition() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(10); 
            y = random.nextInt(10);
        } while (!isEmpty(new Point(x, y))); 
        return new Point(x, y);
    }

    public void giveAllWeapons(Player p1, Player p2){
        for (Weapon w : p1.getWeapons()) {
            p2.addWeapon(w);
        }
    }

    public int playersLeft(){
        return players.size();
    }

    public void nextTurn(){
        playerTurn = (playerTurn + 1) % players.size();
    }

    public Player playerTurn(){
        return players.get(playerTurn);
    }

    public boolean movePlayer(Player player, char direction) {
        Random rnd = new Random();

        Point oldPosition = player.getPosition();
        Point newPosition = calculateNewPosition(oldPosition, direction);
        Entity newPositioEntity = getEntity(newPosition);

        // Check if the new position is valid
        if (!isInBoard(newPosition) || isThereTree(newPosition)) {
            return false;
        }

        else if (newPositioEntity instanceof Weapon){
            player.addWeapon((Weapon) newPositioEntity);
            replaceEntity(oldPosition, newPosition);
        }

        else if(newPositioEntity instanceof Player){ 
            Player player2 = (Player) newPositioEntity;
            if(player.getWeapon() != null && player.getWeapon().stronger((player2.getWeapon()))){
                player.setPosition(newPosition);
                giveAllWeapons(player2, player);
                replaceEntity(oldPosition, newPosition);
            }
            else if(((Player) newPositioEntity).getWeapon() != null && ((Player) newPositioEntity).getWeapon().stronger(player.getWeapon())){
                giveAllWeapons(player, player2);
                deleteEntity(oldPosition);  
                playerTurn--;          
            }
            else{
                System.out.println("Weapons are equal, choosing a winner randomly.");
                boolean random = rnd.nextBoolean();
                if(random){
                    giveAllWeapons(player2, player);
                    replaceEntity(oldPosition, newPosition);
                    System.out.println("The attacker won!");
                }
                else{
                    giveAllWeapons(player, player2);
                    deleteEntity(oldPosition);      
                    System.out.println("The attacker lost!");
                    playerTurn--;          

                }
            }

        }
        else{
            board[newPosition.getX()][newPosition.getY()] = board[oldPosition.getX()][oldPosition.getY()];
            board[oldPosition.getX()][oldPosition.getY()] = null;
            player.setPosition(newPosition);
        }
        return true;
    }
    
    private Point calculateNewPosition(Point position, char direction) {
        int x = position.getX();
        int y = position.getY();
        switch (direction) {
            case 'U': y--; break; // Up
            case 'D': y++; break; // Down
            case 'L': x--; break; // Left
            case 'R': x++; break; // Right
        }
        return new Point(x, y);
    }
}