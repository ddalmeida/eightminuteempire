package UI.text;

import logic.game.Game;

public class main {

    public static void main(String[] args) {
        Game game = new Game();
        

        printf("Shall we play a game?", 0, 0);
        printf("by Luís Costa - 21XXXXXXX", 10, 1);
        printf("and Nuno Aguiar - 21160515", 11, 1);
    }

    private static void printf(String s, int l, int c) {
        System.out.print(String.format("%c[%d;%df%s", 0x1B, l, c, s));
    }
}
