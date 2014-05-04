
import logic.game.Game;
import logic.map.BaseRegion;
import logic.map.LandRegion;

public class main {

    final static int REGION_SIZE = 7;

    public static void main(String[] args) {
        Game game = new Game();

        System.out.println("Y: " + game.getBoard().getMapSizeY());
        System.out.println("X: " + game.getBoard().getMapSizeX());

        drawMap(game);

    }

    private static void drawMap(Game game) {
        // Inicializar buffer
        StringBuilder drawBuffer[] = new StringBuilder[game.getBoard().getMapSizeY() * REGION_SIZE + 2];

        // Criar as "linhas" do mapa e meta-las vazias
        for (int i = 0; i < game.getBoard().getMapSizeY() * REGION_SIZE + 2; ++i) {
            drawBuffer[i] = new StringBuilder();
            for (int j = 0; j < game.getBoard().getMapSizeX() * REGION_SIZE + 2; ++j) {
                drawBuffer[i].append(' ');
            }
        }

        // "Pintar" as regioes
        for (int i = 0; i < game.getBoard().getMapSizeY(); ++i) {
            for (int j = 0; j < game.getBoard().getMapSizeX(); ++j) {
                drawRegion(game.getBoard().getRegion(i, j), drawBuffer);
            }
        }

        // "Desenhar" bordos
        for (int i = 0; i < game.getBoard().getMapSizeX() * REGION_SIZE + 2; ++i) {
            drawBuffer[0].setCharAt(i, '0');
            drawBuffer[game.getBoard().getMapSizeX() * REGION_SIZE + 1].setCharAt(i, '0');
        }
        for (int i = 0; i < game.getBoard().getMapSizeY() * REGION_SIZE + 2; ++i) {
            drawBuffer[i].setCharAt(0, '0');
            drawBuffer[i].setCharAt(game.getBoard().getMapSizeY() * REGION_SIZE + 1, '0');
        }

        // Mostrar na consola
        for (int i = 0; i < drawBuffer.length; ++i) {
            System.out.println(drawBuffer[i].toString());
        }
    }

    private static void drawRegion(BaseRegion region, StringBuilder drawBuffer[]) {
        if (region instanceof LandRegion) {
            for (int i = 0; i < REGION_SIZE; ++i) {
                for (int j = 0; j < REGION_SIZE; ++j) {
                    System.out.println("Y: " + i + " X: " + j);
                    drawBuffer[i + region.getY() * REGION_SIZE + 1].setCharAt(j + region.getX() * REGION_SIZE + 1, 'X');
                }
            }
        }

        // Adicionar coordenadas
        drawBuffer[region.getY() * REGION_SIZE + 4].setCharAt(region.getX() * REGION_SIZE + 2, '[');
        drawBuffer[region.getY() * REGION_SIZE + 4].setCharAt(region.getX() * REGION_SIZE + 3, (char) (region.getY() + 48));
        drawBuffer[region.getY() * REGION_SIZE + 4].setCharAt(region.getX() * REGION_SIZE + 4, ',');
        drawBuffer[region.getY() * REGION_SIZE + 4].setCharAt(region.getX() * REGION_SIZE + 5, (char) (region.getX() + 48));
        drawBuffer[region.getY() * REGION_SIZE + 4].setCharAt(region.getX() * REGION_SIZE + 6, ']');
    }
}
