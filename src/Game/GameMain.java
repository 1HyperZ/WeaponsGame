package Game;
public class GameMain {
    
    public static void main(String[] args) {
        GameConsole gameConsole = new GameConsole(new Board());
        gameConsole.startGame();
    }
}
