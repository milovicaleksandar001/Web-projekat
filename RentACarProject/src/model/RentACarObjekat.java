package model;

import java.util.List;

import enums.Status;

public class RentACarObjekat {
private int id;
private String naziv;
private List<Vozilo> vozilaUPonudi;
private String radnoVreme;
private Status status;
private Lokacija lokacija;
private String logo;
private double Ocena;
private Korisnik menadzer;
private int brojKomentara;



public RentACarObjekat() {
	super();
}



public String getNaziv() {
	return naziv;
}



public void setNaziv(String naziv) {
	this.naziv = naziv;
}



public RentACarObjekat(int id, String naziv, List<Vozilo> vozilaUPonudi, String radnoVreme, Status status,
		Lokacija lokacija, String logo, int ocena, Korisnik menadzer, int brojKomentara) {
	super();
	this.id = id;
	this.naziv = naziv;
	this.vozilaUPonudi = vozilaUPonudi;
	this.radnoVreme = radnoVreme;
	this.status = status;
	this.lokacija = lokacija;
	this.logo = logo;
	this.Ocena = ocena;
	this.menadzer = menadzer;
	this.brojKomentara = brojKomentara;
}



public Korisnik getMenadzer() {
	return menadzer;
}



public void setMenadzer(Korisnik menadzer) {
	this.menadzer = menadzer;
}



public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public List<Vozilo> getVozilaUPonudi() {
	return vozilaUPonudi;
}
public void setVozilaUPonudi(List<Vozilo> vozilaUPonudi) {
	this.vozilaUPonudi = vozilaUPonudi;
}
public String fileLine() {
	return Integer.toString(id)+"|"+naziv+"|"+Integer.toString(lokacija.getId())+"|"+radnoVreme+"|"+logo+"|"+Integer.toString(menadzer.getId())+"|"+status+"|"+Double.toString(Ocena)+"|"+Integer.toString(brojKomentara)+"\n";
}
public String getRadnoVreme() {
	return radnoVreme;
}
public void setRadnoVreme(String radnoVreme) {
	this.radnoVreme = radnoVreme;
}
public Status getStatus() {
	return status;
}
public void setStatus(Status status) {
	this.status = status;
}
public Lokacija getLokacija() {
	return lokacija;
}
public void setLokacija(Lokacija lokacija) {
	this.lokacija = lokacija;
}
public String getLogo() {
	return logo;
}
public void setLogo(String logo) {
	this.logo = logo;
}



public double getOcena() {
	return Ocena;
}



public void setOcena(double ocena) {
	Ocena = ocena;
}



public int getBrojKomentara() {
	return brojKomentara;
}



public void setBrojKomentara(int brojKomentara) {
	this.brojKomentara = brojKomentara;
}



}
