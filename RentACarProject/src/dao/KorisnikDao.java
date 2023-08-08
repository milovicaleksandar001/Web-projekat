package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.StringTokenizer;

import enums.Pol;
import enums.Uloga;
import model.Korisnik;
import model.Korpa;
import model.Porudzbina;
import model.RentACarObjekat;
import model.TipKupca;
import model.Vozilo;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

public class KorisnikDao {

	private ArrayList<Korisnik> korisnici;
	private ArrayList<Porudzbina> porudzbine;
	private Korpa korpa;
	private RentACarObjekat raco;
	private TipKupca tk;
	private RentACarObjekatDao racoDao;

	private final String contextPath="C:\\Users\\aleks\\Desktop\\WebPoceliRent\\WebRentACarProject\\RentACarProject\\src\\data\\";	

	public KorisnikDao()
	{
		tk = new TipKupca();
		raco = new RentACarObjekat();
		korpa = new Korpa();
		porudzbine = new ArrayList<Porudzbina>();
		korisnici =  new ArrayList<Korisnik>();
		racoDao = new RentACarObjekatDao();
		loadKorisnici();
	}
	
	private int newId() {
		int id = 0;
		for(Korisnik k : korisnici) {
			if(k.getId() > id) {
				id = k.getId();
			}
		}
		return id + 1;
	}
	public ArrayList<Korisnik> getAllManagers() {
		korisnici.clear();
        loadKorisnici();
        racoDao.ocisti();
		ArrayList<Korisnik> menadzeri = new ArrayList<Korisnik>();
		for (Korisnik k : korisnici) {
			int flag = 0;
			for(RentACarObjekat raco : racoDao.prikazSvih()) {
				if(raco.getMenadzer().getId()==k.getId()) {
					flag = 1;
					}
				}
			if(flag==0) {
				if (k.getUloga()==Uloga.valueOf("MENADZER")) {
					menadzeri.add(k);
				}
			}
		}
		return menadzeri;
	}
	public boolean menagerRasporedjen(String korisnicko) 
	{
		Korisnik menadzer = new Korisnik();
		menadzer = getUserByUsername(korisnicko);
		for(RentACarObjekat raco : racoDao.prikazSvih()) 
		{
			if(raco.getMenadzer().getId()==menadzer.getId()) 
			{
				return true;
			}
		}
		return false;
	}
	public ArrayList<Korisnik> filter(String ime, String prezime, String korisnickoIme,String uloga)
	{
		ArrayList<Korisnik> korisniciZaVracanje = new ArrayList<Korisnik>();
		ArrayList<Korisnik> trenutniKorisnici = new ArrayList<Korisnik>();
		trenutniKorisnici = search(ime,prezime,korisnickoIme);
		if(uloga.equals(" ")) 
		{
			uloga = "";
		}
		for(Korisnik k : trenutniKorisnici) 
		{
			if(k.getUloga().toString().equals(uloga) || uloga=="") 
			{
					korisniciZaVracanje.add(k);
			}
		}
		return korisniciZaVracanje;
	}
	public ArrayList<Korisnik> search(String ime, String prezime, String korisnickoIme)
	{
		ArrayList<Korisnik> sviKorisnici = new ArrayList<Korisnik>();
		ArrayList<Korisnik> korisniciZaVracanje = new ArrayList<Korisnik>();
		sviKorisnici = getAll();
		if(ime.equals(" ")) 
		{
			ime = "";
		}
		if(prezime.equals(" ")) 
		{
			prezime = "";
		}
		if(korisnickoIme.equals("prenosVrednost")) 
		{
			korisnickoIme = "";
		}
		for(Korisnik k : sviKorisnici) 
		{
			if(k.getIme().contains(ime)) 
			{
				if(k.getPrezime().contains(prezime)) 
				{
					if(k.getKorisnickoIme().contains(korisnickoIme)) 
					{
						korisniciZaVracanje.add(k);
					}
				}
			}
		}
		return korisniciZaVracanje;
	}
	public ArrayList<Korisnik> getAll(){
		return new ArrayList<Korisnik>(korisnici);
	}
	public RentACarObjekat getObjekatByKorisnicko(String korisnicko) 
	{
		Korisnik korisnik = new Korisnik();
		korisnik = getUserByUsername(korisnicko);
		for(RentACarObjekat o : racoDao.prikazSvih()) 
		{
			if(o.getMenadzer().getId()==korisnik.getId()) 
			{
				korisnik.setRentACarObjekat(o);
			}
		}
		return korisnik.getRentACarObjekat();
	}
	
	public boolean isBlocked(String korIme,String lozinka) {
		for(Korisnik k : korisnici) 
		{
			if(k.getKorisnickoIme().equals(korIme) && k.getLozinka().equals(lozinka) && k.getBlokiran() == 1) 
			{				
				return true;
			}
		}
		return false;
	}
	
	public boolean checkUser(String korIme,String lozinka) {
		for(Korisnik k : korisnici) 
		{
			if(k.getKorisnickoIme().equals(korIme) && k.getLozinka().equals(lozinka)) 
			{
				return true;
			}
		}
		return false;
	}
	public void saveToFile() {
        try {
              FileWriter file = new FileWriter(contextPath + "korisnici.txt");

              BufferedWriter output = new BufferedWriter(file);

              for(Korisnik k : korisnici)
              {
                  output.write(k.fileLine());
              }
              
              output.close();
              
        }     
        catch (Exception e) {
        	  System.out.println(e);
              e.getStackTrace();
            }
    }
	public void loadKorisnici() {
        BufferedReader in = null;
        try {
            File file = new File(contextPath + "korisnici.txt");
            System.out.println(file.getCanonicalPath());
            in = new BufferedReader(new FileReader(file));
            String line;
            StringTokenizer st;
            
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.equals("") || line.indexOf('#') == 0)
                    continue;
                st = new StringTokenizer(line, "|");
                while (st.hasMoreTokens()) {
                    int id = Integer.parseInt(st.nextToken().trim());    
                    String korisnickoIme = st.nextToken().trim();
                    String lozinka = st.nextToken().trim();
                    String ime = st.nextToken().trim();
                    String prezime = st.nextToken().trim();
                    Pol pol = Pol.valueOf(st.nextToken().trim());
                    String datum = st.nextToken().trim();
                    Uloga uloga = Uloga.valueOf(st.nextToken().trim());
                    double bodovi = Double.parseDouble(st.nextToken().trim());
                    int blokiran = Integer.parseInt(st.nextToken().trim());    
                    Korisnik korisnik = new Korisnik();
                    korisnik.setId(id);
                    korisnik.setKorisnickoIme(korisnickoIme);
                    korisnik.setLozinka(lozinka);
                    korisnik.setIme(ime);
                    korisnik.setPrezime(prezime);
                    korisnik.setPol(pol);
                    korisnik.setDatum(datum);
                    korisnik.setUloga(uloga);
                    korisnik.setBodovi(bodovi);
                    korisnik.setBlokiran(blokiran);
                    korisnici.add(korisnik);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }

    }
	
	public Korisnik register(Korisnik korisnik) {
		korisnik.setId(newId());
		for(Korisnik k : korisnici)
		{
			if(k.getKorisnickoIme() == korisnik.getKorisnickoIme())
			{
				return null;
			}
		}
		korisnici.add(korisnik);
		saveToFile();
		korisnici.clear();
		loadKorisnici();
		return korisnik;
	}
	
	public Korisnik getUserByUsername(String username) {
		for(Korisnik k : korisnici)
		{
			if(k.getKorisnickoIme().equals(username))
			{
				return k;
			}
		}
		return null;
	}
	
	public boolean checkUserName(String korIme) {
		for(Korisnik k : korisnici) 
		{
			if(k.getKorisnickoIme().equals(korIme) ) 
			{
				return false;
			}
		}
		return true;
	}
	
	public Korisnik updateKorisnik(Korisnik korisnik)
	{
		for(Korisnik k : korisnici)
		{
			if(k.getId() == korisnik.getId())
			{
				k.setKorisnickoIme(korisnik.getKorisnickoIme());
		        k.setLozinka(korisnik.getLozinka());
		        k.setIme(korisnik.getIme());
		        k.setPrezime(korisnik.getPrezime());
		        k.setPol(korisnik.getPol());
		        k.setDatum(korisnik.getDatum());
			}
		}
		saveToFile();
		korisnici.clear();
		loadKorisnici();
		return null;
	}
	
	public Korisnik getById(int id)
	{
		Korisnik pomKorisnik = new Korisnik();
		for(Korisnik k : korisnici)
		{
			if(k.getId() == id)
			{
				pomKorisnik = k;
			}
		}
		
		if(pomKorisnik == null)
		{
			return null;
		}
		
		return pomKorisnik;
	}
	
	public boolean checkUserName1(String korIme,String pom) {
		for(Korisnik k : korisnici) 
		{

			if(k.getKorisnickoIme().equals(pom))
			{
				return true;
			}
			if(k.getKorisnickoIme().equals(korIme) ) 
			{
				return false;
			}

		}
		return true;
	}
	
	public int GetIdByUsername(String username)
	{
		for(Korisnik k : korisnici)
		{
			if(k.getKorisnickoIme().equals(username))
			{
				return k.getId();
			}
		}
		return -1;
	}
	public boolean DodajBodove(int id,double bodovi) 
	{
		for(Korisnik k:korisnici) {
			if(k.getId()==id) {
				double trenBodovi = k.getBodovi();
				double pomoc = bodovi+trenBodovi;
				k.setBodovi(pomoc);
			}
		}
		saveToFile();
		return true;
	}
	public boolean oduzmiBodove(int id,double bodovi) 
	{
		for(Korisnik k:korisnici) {
			if(k.getId()==id) {
				double trenBodovi = k.getBodovi();
				double pomoc = trenBodovi-bodovi;
				k.setBodovi(pomoc);
			}
		}
		saveToFile();
		korisnici.clear();
		loadKorisnici();
		return true;
	}
	
	public ArrayList<Korisnik> getAllKupci() {
		ArrayList<Korisnik> pomLista = new ArrayList<Korisnik>();
		for (Korisnik k : korisnici) {
			if (k.getUloga()==Uloga.valueOf("KUPAC")) {
					pomLista.add(k);
				}
			
		}
		return pomLista;
	}
	
	public ArrayList<Korisnik> getAllSumnjiviKupci() {
		ArrayList<Korisnik> pomLista = new ArrayList<Korisnik>();
		for (Korisnik k : korisnici) {
			if (k.getUloga()==Uloga.valueOf("KUPAC") && k.getBlokiran() == 1) {
					pomLista.add(k);
				}
			
		}
		return pomLista;
	}
	
	public Korisnik blockKorisnik(int id)
	{
	    for(Korisnik k : korisnici)
	    {
	        if(k.getId() == id)
	        {
	            k.setBlokiran(1);
	        }
	    }
	    saveToFile();
	    korisnici.clear();
	    loadKorisnici();
	    return null;
	}
}

