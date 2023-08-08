package model;

import enums.StatusVozilo;
import enums.TipGoriva;
import enums.Vrsta;

public class Vozilo {
	private int id;
	private String marka;
	private String model;
	private int cena;
	private String tip;
	private RentACarObjekat objekatKomPripada;
	private Vrsta vrsta;
	private TipGoriva tipGoriva;
	private int potrosnja;
	private int brojVrata;
	private int brojPutnika;
	private String opis;
	private String slika;
	private StatusVozilo status;
	private int obrisano;
	
	public int getObrisano() {
		return obrisano;
	}
	public void setObrisano(int obrisano) {
		this.obrisano = obrisano;
	}
	
	public Vozilo(int id, String marka, String model, int cena, String tip, RentACarObjekat objekatKomPripada,
			Vrsta vrsta, TipGoriva tipGoriva, int potrosnja, int brojVrata, int brojPutnika, String opis, String slika,
			StatusVozilo status, int obrisano) {
		super();
		this.id = id;
		this.marka = marka;
		this.model = model;
		this.cena = cena;
		this.tip = tip;
		this.objekatKomPripada = objekatKomPripada;
		this.vrsta = vrsta;
		this.tipGoriva = tipGoriva;
		this.potrosnja = potrosnja;
		this.brojVrata = brojVrata;
		this.brojPutnika = brojPutnika;
		this.opis = opis;
		this.slika = slika;
		this.status = status;
		this.obrisano = obrisano;
	}
	public Vozilo() {
		super();
	}
	public String getMarka() {
		return marka;
	}
	public String fileLine() {
		return Integer.toString(id)+"|"+marka+"|"+model+"|"+Integer.toString(cena)+"|"+tip+"|"+vrsta+"|"+tipGoriva+"|"+Integer.toString(potrosnja)+"|"+Integer.toString(brojVrata)+"|"+Integer.toString(brojPutnika)+"|"+slika+"|"+opis+"|"+Integer.toString(objekatKomPripada.getId())+"|"+status+"|"+Integer.toString(obrisano)+"\n";
	}
	public void setMarka(String marka) {
		this.marka = marka;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getCena() {
		return cena;
	}
	public void setCena(int cena) {
		this.cena = cena;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RentACarObjekat getObjekatKomPripada() {
		return objekatKomPripada;
	}
	public void setObjekatKomPripada(RentACarObjekat objekatKomPripada) {
		this.objekatKomPripada = objekatKomPripada;
	}
	public Vrsta getVrsta() {
		return vrsta;
	}
	public void setVrsta(Vrsta vrsta) {
		this.vrsta = vrsta;
	}
	public TipGoriva getTipGoriva() {
		return tipGoriva;
	}
	public void setTipGoriva(TipGoriva tipGoriva) {
		this.tipGoriva = tipGoriva;
	}
	public int getPotrosnja() {
		return potrosnja;
	}
	public void setPotrosnja(int potrosnja) {
		this.potrosnja = potrosnja;
	}
	public int getBrojVrata() {
		return brojVrata;
	}
	public void setBrojVrata(int brojVrata) {
		this.brojVrata = brojVrata;
	}
	public int getBrojPutnika() {
		return brojPutnika;
	}
	public void setBrojPutnika(int brojPutnika) {
		this.brojPutnika = brojPutnika;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	}
	public StatusVozilo getStatus() {
		return status;
	}
	public void setStatus(StatusVozilo status) {
		this.status = status;
	}
	
}
