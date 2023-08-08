package model;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Pol;
import enums.Uloga;

public class Korisnik {
	private int id;
	private String korisnickoIme;
	private String lozinka;
	private String ime;
	private String prezime;
	private Pol pol;
	private String datum;
	private Uloga uloga;
	private ArrayList<Porudzbina> svaIznajmljivanja;
	private Korpa korpa;
	private RentACarObjekat rentACarObjekat;
	private double bodovi;
	private TipKupca tipKupca;
	private int blokiran;
	
	public Korisnik() {
		super();
	}
	
	public Korisnik(int id, String korisnickoIme, String lozinka, String ime, String prezime, Pol pol, String datum,
			Uloga uloga, ArrayList<Porudzbina> svaIznajmljivanja, Korpa korpa, RentACarObjekat rentACarObjekat,
			double bodovi, TipKupca tipKupca,int blokiran) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datum = datum;
		this.uloga = uloga;
		this.svaIznajmljivanja = svaIznajmljivanja;
		this.korpa = korpa;
		this.rentACarObjekat = rentACarObjekat;
		this.bodovi = bodovi;
		this.tipKupca = tipKupca;
		this.blokiran = blokiran;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String fileLine() {
		return Integer.toString(id)+"|"+korisnickoIme+"|"+lozinka+"|"+ime+"|"+prezime+"|"+pol+"|"+datum+"|"+uloga+"|"+Double.toString(bodovi)+"|"+Integer.toString(blokiran)+"\n";
	}
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public Pol getPol() {
		return pol;
	}
	public void setPol(Pol pol) {
		this.pol = pol;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public Uloga getUloga() {
		return uloga;
	}
	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
	public ArrayList<Porudzbina> getSvaIznajmljivanja() {
		return svaIznajmljivanja;
	}
	public void setSvaIznajmljivanja(ArrayList<Porudzbina> svaIznajmljivanja) {
		this.svaIznajmljivanja = svaIznajmljivanja;
	}
	public Korpa getKorpa() {
		return korpa;
	}
	public void setKorpa(Korpa korpa) {
		this.korpa = korpa;
	}
	public RentACarObjekat getRentACarObjekat() {
		return rentACarObjekat;
	}
	public void setRentACarObjekat(RentACarObjekat rentACarObjekat) {
		this.rentACarObjekat = rentACarObjekat;
	}
	
	public double getBodovi() {
		return bodovi;
	}

	public void setBodovi(double bodovi) {
		this.bodovi = bodovi;
	}

	public TipKupca getTipKupca() {
		return tipKupca;
	}
	public void setTipKupca(TipKupca tipKupca) {
		this.tipKupca = tipKupca;
	}

	public int getBlokiran() {
		return blokiran;
	}

	public void setBlokiran(int blokiran) {
		this.blokiran = blokiran;
	}
	
	
	
}
