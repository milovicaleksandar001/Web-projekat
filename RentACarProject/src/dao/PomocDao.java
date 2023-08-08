package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import model.Korisnik;
import model.Korpa;
import model.Pomoc;
import model.Porudzbina;
import model.RentACarObjekat;
import model.Vozilo;

public class PomocDao {
	private ArrayList<Pomoc> pomoci;
	private VoziloDao voziloDao;
	private PorudzbinaDao porudzbinaDao;
	private VezaKorpaVoziloDao vezaKorpaVoziloDao;
	private RentACarObjekatDao racoDao;
	private KorisnikDao korisnikDao;
 	
	private final String contextPath="C:\\Users\\aleks\\Desktop\\WebPoceliRent\\WebRentACarProject\\RentACarProject\\src\\data\\";	
	
	public PomocDao()
	{
		pomoci = new ArrayList<Pomoc>();
		voziloDao = new VoziloDao();
		porudzbinaDao = new PorudzbinaDao();
		vezaKorpaVoziloDao = new VezaKorpaVoziloDao();
		racoDao = new RentACarObjekatDao();
		korisnikDao = new KorisnikDao();
		loadPomoci();
	}
	public ArrayList<Pomoc> getAll(){
		return new ArrayList<Pomoc>(pomoci);
	}
	public boolean ugasitiKomentarisanje(int idRaca,int idKorisnika) 
	{
		ArrayList<Porudzbina> zaSlanje = new ArrayList<Porudzbina>();
		zaSlanje = porudzbinaDao.getAll();
		for(Porudzbina p : zaSlanje) 
		{
			if(p.getKupac().getId()==idKorisnika && p.getStatus().toString().equals("VRACENO") && p.getKomentarisano()==0) 
			{
				int flag = 0;
				for(Pomoc pom : pomoci) 
				{
					if(pom.getPorudzbina()==p.getId()) 
					{
						if(flag==0) {
						Vozilo pomVozilo = new Vozilo();
						pomVozilo = voziloDao.getById(pom.getVozilo());
						if(pomVozilo.getObjekatKomPripada().getId()==idRaca) 
						{
							p.setKomentarisano(1);
							porudzbinaDao.posaljiListu(zaSlanje);
							flag = 1;
							return true;
						}
						}
					}
				}
			}
		}
		return true;
	}
	public ArrayList<Porudzbina> searchMenadzer(int cenaOd,int cenaDo,LocalDateTime datumOd,LocalDateTime datumDo,int idObjekta)
	{
		ArrayList<Porudzbina> zaVracanje = new ArrayList<Porudzbina>();
		ArrayList<Porudzbina> iteracije = new ArrayList<Porudzbina>();
		iteracije = porudzbineMenadzera(idObjekta);
		for(Porudzbina p : iteracije) 
		{
			if(cenaOd==0 || p.getCena()>cenaOd) 
			{
				if(cenaDo==0 || p.getCena()<cenaDo) 
				{
					if(p.getDatumIVreme().isAfter(datumOd)) 
					{
						if(p.getDatumIVreme().isBefore(datumDo)) 
						{
							zaVracanje.add(p);
						}
					}
				}
			}
		}
		
		
		return zaVracanje;
	}
	public boolean iznajmljeno(int id) 
	{
		for(Pomoc p : pomoci) 
		{
			if(p.getPorudzbina()==id) 
			{
				voziloDao.promeniIznajmljeno(p.getVozilo());
			}
		}
		return true;
	}
	public boolean vracanje(int id) 
	{
		for(Pomoc p : pomoci) 
		{
			if(p.getPorudzbina()==id) 
			{
				voziloDao.promeniVraceno(p.getVozilo());
			}
		}
		return true;
	}
	public ArrayList<Porudzbina> searchKupac(int cenaOd,int cenaDo,LocalDateTime datumOd,LocalDateTime datumDo,int idObjekta,int idKupca)
	{
		ArrayList<Porudzbina> zaVracanje = new ArrayList<Porudzbina>();
		ArrayList<Porudzbina> iteracije = new ArrayList<Porudzbina>();
		if(idObjekta==0) 
		{
			iteracije = porudzbinaDao.getAllKupacPorudzbina(idKupca);
		}else 
		{
			iteracije = objekatIKupac(idObjekta,idKupca);
		}
		for(Porudzbina p : iteracije) 
		{
			if(cenaOd==0 || p.getCena()>cenaOd) 
			{
				if(cenaDo==0 || p.getCena()<cenaDo) 
				{
					if(p.getDatumIVreme().isAfter(datumOd)) 
					{
						if(p.getDatumIVreme().isBefore(datumDo)) 
						{
							zaVracanje.add(p);
						}
					}
				}
			}
		}
		
		
		return zaVracanje;
	}
	public ArrayList<RentACarObjekat> komentarisanje(int idKorisnika)
	{
		ArrayList<RentACarObjekat> zaVracanje = new ArrayList<RentACarObjekat>();
		for(Porudzbina p : porudzbinaDao.getAll()) 
		{
			if(p.getKupac().getId()==idKorisnika && p.getStatus().toString().equals("VRACENO") && p.getKomentarisano()==0) 
			{
				int flag = 0;
				for(Pomoc pom : pomoci) 
				{
					if(pom.getPorudzbina()==p.getId()) 
					{
						if(flag==0) {
						Vozilo pomVozilo = new Vozilo();
						pomVozilo = voziloDao.getById(pom.getVozilo());
						zaVracanje.add(racoDao.getById(pomVozilo.getObjekatKomPripada().getId()));
						flag = 1;
						}
					}
				}
			}
		}
		return zaVracanje;
	}
	public void saveToFile() {
        try {
              FileWriter file = new FileWriter(contextPath + "pomoc.txt");

              BufferedWriter output = new BufferedWriter(file);

              for(Pomoc p : pomoci)
              {
                  output.write(p.fileLine());
              }
              
              output.close();
              
        }     
        catch (Exception e) {
        	  System.out.println(e);
              e.getStackTrace();
            }
    }
	public void loadPomoci() {
        BufferedReader in = null;
        try {
            File file = new File(contextPath + "pomoc.txt");
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
                    int porudzbinaId = Integer.parseInt(st.nextToken().trim());    
                    int voziloId = Integer.parseInt(st.nextToken().trim());
                    Pomoc pomoc = new Pomoc();
                    pomoc.setPorudzbina(porudzbinaId);
                    pomoc.setVozilo(voziloId);
                    pomoci.add(pomoc);
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
	public Pomoc dodaj(Pomoc pomoc) {
		pomoci.add(pomoc);
		saveToFile();
		pomoci.clear();
		loadPomoci();
		return pomoc;
	}
	public ArrayList<Vozilo> getVozila(LocalDateTime pocetak,LocalDateTime kraj){
		ArrayList<Vozilo> listaZaVracanje = new ArrayList<Vozilo>();
		for(Vozilo v : voziloDao.getAll()) 
		{
			int flag = 0;
			for(Pomoc p : pomoci) 
			{
				if(p.getVozilo()==v.getId()) 
				{
					for(Porudzbina por : porudzbinaDao.getAll()) 
					{
						if(p.getPorudzbina()==por.getId()) 
						{
							LocalDateTime pocetakPom = pocetak;
							LocalDateTime krajPom = kraj;
							for (; pocetakPom.isBefore(krajPom); pocetakPom = pocetakPom.plusDays(1)) {
							    for(int i=0;i<por.getTrajanje();i++) 
							    {
							    	if(pocetakPom.equals(por.getDatumIVreme().plusDays(i)) && por.getStatus().toString() !="OTKAZANO") 
							    	{
							    		flag=1;
							    	}
							    }
							}
						}
					}
				}
			}
			if(flag==0) {
				if(v.getStatus().toString() == "DOSTUPNO")
				{
				listaZaVracanje.add(v);
				}
			}
		}
		return listaZaVracanje;
	}
	public boolean dodavanje(int idPorudzbina,int idKorpa) 
	{
		ArrayList<Vozilo> vozilaIzKorpe = new ArrayList<Vozilo>();
		vozilaIzKorpe = vezaKorpaVoziloDao.getAllVozilaFromCartLoad(idKorpa);
		for(Vozilo v : vozilaIzKorpe) 
		{
			Pomoc pomoc = new Pomoc();
			pomoc.setPorudzbina(idPorudzbina);
			pomoc.setVozilo(v.getId());
			pomoci.add(pomoc);
		}
		saveToFile();
		pomoci.clear();
		loadPomoci();
		return true;
	}
	public ArrayList<Porudzbina> porudzbineMenadzera(int idObjekta)
	{
		ArrayList<Porudzbina> zaVracanje = new ArrayList<Porudzbina>();
		for(Porudzbina p : porudzbinaDao.getAll()) 
		{
			int flag = 0;
			for(Pomoc pom : pomoci) 
			{
				if(pom.getPorudzbina()==p.getId()) 
				{
					Vozilo pomocnoVozilo = new Vozilo();
					pomocnoVozilo = voziloDao.getById(pom.getVozilo());
					if(pomocnoVozilo.getObjekatKomPripada().getId()==idObjekta) 
					{
						flag=1;
					}
				}
			}
			if(flag == 1) 
			{
				p.setKupac(korisnikDao.getById(p.getKupac().getId()));
				zaVracanje.add(p);
			}
		}
		return zaVracanje;
	}
	public ArrayList<Porudzbina> objekatIKupac(int idObjekta,int idKupca)
	{
		ArrayList<Porudzbina> zaVracanje = new ArrayList<Porudzbina>();
		for(Porudzbina p : porudzbinaDao.getAll()) 
		{
			int flag = 0;
			for(Pomoc pom : pomoci) 
			{
				if(pom.getPorudzbina()==p.getId()) 
				{
					Vozilo pomocnoVozilo = new Vozilo();
					pomocnoVozilo = voziloDao.getById(pom.getVozilo());
					if(pomocnoVozilo.getObjekatKomPripada().getId()==idObjekta) 
					{
						if(p.getKupac().getId()==idKupca) 
						{
						flag=1;
						}
					}
				}
			}
			if(flag == 1) 
			{
				zaVracanje.add(p);
			}
		}
		return zaVracanje;
	}
}
