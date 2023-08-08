package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Korpa {
private int id;
private List<Vozilo> voziloList;
private Korisnik korisnik;
private int cena;
private LocalDateTime pocetanDatum;
private LocalDateTime krajnjiDatum;


public Korpa() {
	super();
}



public Korpa(int id, List<Vozilo> voziloList, Korisnik korisnik, int cena, LocalDateTime pocetanDatum,
		LocalDateTime krajnjiDatum) {
	super();
	this.id = id;
	this.voziloList = voziloList;
	this.korisnik = korisnik;
	this.cena = cena;
	this.pocetanDatum = pocetanDatum;
	this.krajnjiDatum = krajnjiDatum;
}



public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}
public String fileLine() {
	String timestampStr1 = pocetanDatum.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	String timestampStr2 = krajnjiDatum.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	return Integer.toString(id)+"|"+Integer.toString(korisnik.getId())+"|"+Integer.toString(cena)+"|"+timestampStr1+"|"+timestampStr2+"\n";
}


public List<Vozilo> getVoziloList() {
	return voziloList;
}


public LocalDateTime getPocetanDatum() {
	return pocetanDatum;
}

public void setPocetanDatum(LocalDateTime pocetanDatum) {
	this.pocetanDatum = pocetanDatum;
}

public LocalDateTime getKrajnjiDatum() {
	return krajnjiDatum;
}

public void setKrajnjiDatum(LocalDateTime krajnjiDatum) {
	this.krajnjiDatum = krajnjiDatum;
}

public void setVoziloList(List<Vozilo> voziloList) {
	this.voziloList = voziloList;
}


public Korisnik getKorisnik() {
	return korisnik;
}


public void setKorisnik(Korisnik korisnik) {
	this.korisnik = korisnik;
}


public int getCena() {
	return cena;
}


public void setCena(int cena) {
	this.cena = cena;
}


}
