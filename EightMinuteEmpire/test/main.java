
import logic.game.Game;


public class main {
    public static void main(String[] args) {
        Game game = new Game();
        
        System.out.println("Y: " + game.getBoard().getMapSizeY());
        System.out.println("X: " + game.getBoard().getMapSizeX());

}
}