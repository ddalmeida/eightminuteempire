package logic.game;

public class Player {

    private String name;
    private int coins;

    Player(String nome, int moedas) {
        this.name = nome;
        this.coins = moedas;
    }

    public String getNome() {
        return name;
    }

    public int getMoedas() {
        return coins;
    }

    public void retirarMoedas(int n) {
        coins -= n >= 0 ? coins - n : 0;
    }

    public void adicionarMoedas(int n) {
        coins += n;
    }
}
