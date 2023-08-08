package model;

public class Komentar {
private int id;
private Korisnik kupac;
private RentACarObjekat rentACarObjekat;
private String tekstKomentara;
private int Ocena;
private String Status;


public Komentar() {
	super();
}

public Komentar(int id, Korisnik kupac, RentACarObjekat rentACarObjekat, String tekstKomentara, int ocena, String Status) {
	super();
	this.id = id;
	this.kupac = kupac;
	this.rentACarObjekat = rentACarObjekat;
	this.tekstKomentara = tekstKomentara;
	Ocena = ocena;
	this.Status = Status;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Korisnik getKupac() {
	return kupac;
}
public void setKupac(Korisnik kupac) {
	this.kupac = kupac;
}
public RentACarObjekat getRentACarObjekat() {
	return rentACarObjekat;
}
public void setRentACarObjekat(RentACarObjekat rentACarObjekat) {
	this.rentACarObjekat = rentACarObjekat;
}
public String getTekstKomentara() {
	return tekstKomentara;
}
public void setTekstKomentara(String tekstKomentara) {
	this.tekstKomentara = tekstKomentara;
}
public int getOcena() {
	return Ocena;
}
public void setOcena(int ocena) {
	Ocena = ocena;
}


public String getStatus() {
	return Status;
}

public void setStatus(String status) {
	Status = status;
}

public String fileLine() {
    return Integer.toString(id)+"|"+Integer.toString(kupac.getId())+"|"+Integer.toString(rentACarObjekat.getId())+"|"+tekstKomentara+"|"+Integer.toString(Ocena)+"|"+Status+"\n";
}


}
