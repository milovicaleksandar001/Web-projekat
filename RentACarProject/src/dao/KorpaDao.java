package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.StringTokenizer;

import enums.StatusPorudzbine;
import model.Korisnik;
import model.Korpa;
import model.Porudzbina;
import model.RentACarObjekat;

public class KorpaDao {
	private ArrayList<Korpa> korpe;
	private KorisnikDao korisnikDao;
	
	private final String contextPath="C:\\Users\\aleks\\Desktop\\WebPoceliRent\\WebRentACarProject\\RentACarProject\\src\\data\\";	
	
	public KorpaDao()
	{
		korisnikDao = new KorisnikDao();
		korpe = new ArrayList<Korpa>();
		loadKorpe();
	}
	private int newId() {
		int id = 0;
		for(Korpa k : korpe) {
			if(k.getId() > id) {
				id = k.getId();
			}
		}
		return id + 1;
	}
	public ArrayList<Korpa> getAll(){
		return new ArrayList<Korpa>(korpe);
	}
	public Korpa updateKorpa(int id,LocalDateTime pocetak,LocalDateTime kraj)
	{
		Korpa korpa = new Korpa();
		korpa = getById(id);
		for(Korpa k : korpe)
		{
			if(k.getId() == korpa.getId())
			{
		        k.setPocetanDatum(pocetak);
		        k.setKrajnjiDatum(kraj);
			}
		}
		saveToFile();
		korpe.clear();
		loadKorpe();
		return null;
	}
	public void saveToFile() {
        try {
              FileWriter file = new FileWriter(contextPath + "korpe.txt");

              BufferedWriter output = new BufferedWriter(file);

              for(Korpa k : korpe)
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
	public void loadKorpe() {
        BufferedReader in = null;
        try {
            File file = new File(contextPath + "korpe.txt");
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
                    int korisnikId = Integer.parseInt(st.nextToken().trim());
                    int cena = Integer.parseInt(st.nextToken().trim()); 
                    String timestampStr1 = st.nextToken().trim();
                    LocalDateTime timestamp1 = LocalDateTime.parse(timestampStr1, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    String timestampStr2 = st.nextToken().trim();
                    LocalDateTime timestamp2 = LocalDateTime.parse(timestampStr2, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    Korpa korpa = new Korpa();
                    korpa.setId(id);
                    Korisnik korisnik = new Korisnik();
                    korisnik.setId(korisnikId);
                    korpa.setKorisnik(korisnik);
                    korpa.setCena(cena);
                    korpa.setPocetanDatum(timestamp1);
                    korpa.setKrajnjiDatum(timestamp2);
                    korpe.add(korpa);
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
	public Korpa dodaj(Korpa korpa) {
		korpa.setPocetanDatum(LocalDateTime.MIN);
		korpa.setKrajnjiDatum(LocalDateTime.MIN);
		korpa.setId(newId());
		korpe.add(korpa);
		saveToFile();
		korpe.clear();
		loadKorpe();
		return korpa;
	}
	public Korpa getById(int id)
	{
		Korpa pomKorpa = new Korpa();
		for(Korpa k : korpe)
		{
			if(k.getId() == id)
			{
				pomKorpa = k;
			}
		}
		
		if(pomKorpa == null)
		{
			return null;
		}
		
		return pomKorpa;
	}
	
	public int getKorpaIdByUserId(int id)
	{
		
		for(Korpa k : korpe)
		{
			if(k.getKorisnik().getId() == id)
			{
				return k.getId();
			}
		}
		
		return -1;
	}
	public String datumPocetka(int id) 
	{
		String timestampStr = "";
		for(Korpa k : korpe)
		{
			if(k.getId() == id)
			{
				timestampStr=k.getPocetanDatum().toLocalDate().toString();
			}
		}
		return timestampStr;
	}
	
	
	public int getDaysOfPorudzbinaByKorpaId(int id)
	{
		Korpa pomKorpa = getById(id);
		
		int ret =  (int) ChronoUnit.DAYS.between(pomKorpa.getPocetanDatum(), pomKorpa.getKrajnjiDatum());
			
		return ret;
	}
}
