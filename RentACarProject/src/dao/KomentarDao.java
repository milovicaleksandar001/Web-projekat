package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import model.Komentar;
import model.Korisnik;
import model.RentACarObjekat;

public class KomentarDao {

	private ArrayList<Komentar> komentari;
	private KorisnikDao korisnikDao;
	private RentACarObjekatDao racoDao;
	
	private final String contextPath="C:\\Users\\aleks\\Desktop\\WebPoceliRent\\WebRentACarProject\\RentACarProject\\src\\data\\";	
	
	public KomentarDao()
	{
		korisnikDao = new KorisnikDao();
		racoDao = new RentACarObjekatDao();
		komentari = new ArrayList<Komentar>();
		loadKomentari();
	}

	private int newId() {
		int id = 0;
		for(Komentar k : komentari) {
			if(k.getId() > id) {
				id = k.getId();
			}
		}
		return id + 1;
	}
	public Komentar odobri(int id) 
	{
		for(Komentar k : komentari) 
		{
			if(k.getId()==id) 
			{
				k.setStatus("ODOBREN");
			}
		}
		saveToFile();
		komentari.clear();
		loadKomentari();
		return null;
	}
	public Komentar obrisi(int id) 
	{
		for(Komentar k : komentari) 
		{
			if(k.getId()==id) 
			{
				k.setStatus("OBRISAN");
			}
		}
		saveToFile();
		komentari.clear();
		loadKomentari();
		return null;
	}
	public boolean proveriJelNjegovo(int id,int idKorisnika) 
	{
		for(Komentar k : komentari) 
		{
			if(k.getId()==id) 
			{
				if(k.getRentACarObjekat().getId()==idKorisnika) 
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public ArrayList<Komentar> getAll(){
		ArrayList<Komentar> zaVracanje = new ArrayList<Komentar>();
		zaVracanje = komentari;
		for(Komentar k : zaVracanje) 
		{
			Korisnik pomKom = new Korisnik();
			pomKom = korisnikDao.getById(k.getKupac().getId());
			k.setKupac(pomKom);
			RentACarObjekat pomRaco = new RentACarObjekat();
			pomRaco = racoDao.getById(k.getRentACarObjekat().getId());
			k.setRentACarObjekat(pomRaco);
		}
		return zaVracanje;
	}
	
	public Komentar getById(int id)
	{
		Komentar pomKom = new Komentar();
		for(Komentar k : komentari)
		{
			if(k.getId() == id)
			{
				pomKom = k;
				for(Korisnik kor : korisnikDao.getAll()) 
				{
					if(kor.getId()==k.getKupac().getId()) 
					{
						pomKom.setKupac(kor);
					}
				}
				for(RentACarObjekat raco : racoDao.prikazSvih()) 
				{
					if(raco.getId()==k.getRentACarObjekat().getId()) 
					{
						pomKom.setRentACarObjekat(raco);
					}
				}
			}
		}
		
		if(pomKom == null)
		{
			return null;
		}
		
		return pomKom;
	}
	
	public void loadKomentari() {
        BufferedReader in = null;
        try {
            File file = new File(contextPath + "komentari.txt");
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
                    int racoId = Integer.parseInt(st.nextToken().trim());
                    String tekstKomentara = st.nextToken().trim();
                    int ocena = Integer.parseInt(st.nextToken().trim());
                    String status = st.nextToken().trim();

                    Komentar komentar = new Komentar();
                    komentar.setId(id);
                   
                    Korisnik korisnik = new Korisnik();
                    korisnik.setId(korisnikId);
                    komentar.setKupac(korisnik);
                    
                    RentACarObjekat raco = new RentACarObjekat();
                    raco.setId(racoId);
                    komentar.setRentACarObjekat(raco);
                    
                    komentar.setTekstKomentara(tekstKomentara);
                    
                    komentar.setOcena(ocena);
                    
                    komentar.setStatus(status);
                    
                    komentari.add(komentar);
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
	
	public void saveToFile() {
        try {
              FileWriter file = new FileWriter(contextPath + "komentari.txt");

              BufferedWriter output = new BufferedWriter(file);

              for(Komentar k : komentari)
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
	
	public Komentar create(Komentar komentar) {
		komentar.setId(newId());
		komentari.add(komentar);
		saveToFile();
		komentari.clear();
		loadKomentari();
		return komentar;
	}
	
	public ArrayList<Komentar> getAllOdobreniKomentari()
	{
		ArrayList<Komentar> pomLista = new ArrayList<Komentar>();
		
		for(Komentar k : komentari)
		{
			if(k.getStatus().equals("ODOBREN") )
			{
				Korisnik pomKom = new Korisnik();
				pomKom = korisnikDao.getById(k.getKupac().getId());
				k.setKupac(pomKom);
				RentACarObjekat pomRaco = new RentACarObjekat();
				pomRaco = racoDao.getById(k.getRentACarObjekat().getId());
				k.setRentACarObjekat(pomRaco);
				pomLista.add(k);
			}
		}
		
		return pomLista;
	}
	
	public ArrayList<Komentar> getAllRacoKomentari(int racoId)
	{
		ArrayList<Komentar> pomLista = new ArrayList<Komentar>();
		
		for(Komentar k : komentari)
		{
			if(k.getRentACarObjekat().getId() == racoId )
			{
				Korisnik pomKom = new Korisnik();
				pomKom = korisnikDao.getById(k.getKupac().getId());
				k.setKupac(pomKom);
				RentACarObjekat pomRaco = new RentACarObjekat();
				pomRaco = racoDao.getById(k.getRentACarObjekat().getId());
				k.setRentACarObjekat(pomRaco);
				pomLista.add(k);
			}
		}
		
		return pomLista;
	}
	
	}
