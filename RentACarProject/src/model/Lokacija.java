package model;

public class Lokacija {
	private int id;
	private String geografskaDuzina;
	private String geografskaSirina;
	private String adresa;
	public String getGeografskaDuzina() {
		return geografskaDuzina;
	}
	public void setGeografskaDuzina(String geografskaDuzina) {
		this.geografskaDuzina = geografskaDuzina;
	}
	public String getGeografskaSirina() {
		return geografskaSirina;
	}
	public void setGeografskaSirina(String geografskaSirina) {
		this.geografskaSirina = geografskaSirina;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public Lokacija() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Lokacija(int id, String geografskaDuzina, String geografskaSirina, String adresa) {
		super();
		this.id = id;
		this.geografskaDuzina = geografskaDuzina;
		this.geografskaSirina = geografskaSirina;
		this.adresa = adresa;
	}
	

}
