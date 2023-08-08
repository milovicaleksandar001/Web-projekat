package model;

public class Pomoc {

	private int Porudzbina;
	private int Vozilo;
	
	
	public Pomoc() 
	{
		super();
	}


	public Pomoc(int porudzbina,int vozilo) {
		super();
		Porudzbina = porudzbina;
		Vozilo = vozilo;
	}

	public int getPorudzbina() {
		return Porudzbina;
	}
	
	
	public void setPorudzbina(int porudzbina) {
		Porudzbina = porudzbina;
	}

	public int getVozilo() {
		return Vozilo;
	}


	public void setVozilo(int vozilo) {
		Vozilo = vozilo;
	}


	
	public String fileLine() {
		return Integer.toString(Porudzbina)+"|"+Integer.toString(Vozilo)+"\n";
	}
	
}
