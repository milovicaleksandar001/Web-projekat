package model;

public class TipKupca {
	private int id;
	private String imeTipa;
	private int popust;
	private int trazeniBrojBodova;
	public TipKupca() {
		super();
	}
	public TipKupca(int id, String imeTipa, int popust, int trazeniBrojBodova) {
		super();
		this.id = id;
		this.imeTipa = imeTipa;
		this.popust = popust;
		this.trazeniBrojBodova = trazeniBrojBodova;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImeTipa() {
		return imeTipa;
	}
	public void setImeTipa(String imeTipa) {
		this.imeTipa = imeTipa;
	}
	public int getPopust() {
		return popust;
	}
	public void setPopust(int popust) {
		this.popust = popust;
	}
	public int getTrazeniBrojBodova() {
		return trazeniBrojBodova;
	}
	public void setTrazeniBrojBodova(int trazeniBrojBodova) {
		this.trazeniBrojBodova = trazeniBrojBodova;
	}
}
