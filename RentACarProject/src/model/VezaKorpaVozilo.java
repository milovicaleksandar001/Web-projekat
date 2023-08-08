package model;

public class VezaKorpaVozilo {
	private int Korpa;
	private int Vozilo;
	private int obrisano;
	
	public VezaKorpaVozilo(int korpa, int vozilo, int obrisano) {
		super();
		Korpa = korpa;
		Vozilo = vozilo;
		this.obrisano = obrisano;
	}
	public VezaKorpaVozilo() {
		super();
	}
	public int getKorpa() {
		return Korpa;
	}
	public void setKorpa(int korpa) {
		Korpa = korpa;
	}
	public int getVozilo() {
		return Vozilo;
	}
	public void setVozilo(int vozilo) {
		Vozilo = vozilo;
	}
	public String fileLine() {
		return Integer.toString(Korpa)+"|"+Integer.toString(Vozilo)+"|"+Integer.toString(obrisano)+"\n";
	}
	public int getObrisano() {
		return obrisano;
	}
	public void setObrisano(int obrisano) {
		this.obrisano = obrisano;
	}
	
}
