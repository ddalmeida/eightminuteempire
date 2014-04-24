package logic;

public class Player {
	private String nome;
	private int moedas;
	
	Player(String nome, int moedas)
	{
		this.nome = nome;
		this.moedas = moedas;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public int getMoedas()
	{
		return moedas;
	}
	
	public void retirarMoedas(int n)
	{
		moedas = moedas - n >= 0 ? moedas - n : 0;
	}
	
	public void adicionarMoedas(int n)
	{
		moedas = moedas + n;
	}

}
