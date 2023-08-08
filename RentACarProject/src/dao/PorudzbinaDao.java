package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import enums.Pol;
import enums.StatusPorudzbine;
import enums.Uloga;
import model.Korisnik;
import model.Korpa;
import model.Lokacija;
import model.Porudzbina;
import model.RentACarObjekat;
import model.TipKupca;

public class PorudzbinaDao {
	private ArrayList<Porudzbina> porudzbine;
	private KorisnikDao korisnikDao;
	
	private final String contextPath="C:\\Users\\aleks\\Desktop\\WebPoceliRent\\WebRentACarProject\\RentACarProject\\src\\data\\";	
	
	public PorudzbinaDao()
	{
		porudzbine = new ArrayList<Porudzbina>();
		korisnikDao = new KorisnikDao();
		loadPorudzbine();
	}
	private int newId() {
		int id = 0;
		for(Porudzbina p : porudzbine) {
			if(p.getId() > id) {
				id = p.getId();
			}
		}
		return id + 1;
	}
	public ArrayList<Porudzbina> searchKupac(int cenaOd,int cenaDo,LocalDateTime datumOd,LocalDateTime datumDo)
	{
		ArrayList<Porudzbina> zaVracanje = new ArrayList<Porudzbina>();
		ArrayList<Porudzbina> iteracije = new ArrayList<Porudzbina>();
		iteracije = getAll();
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
	public ArrayList<Korisnik> getAllSumnjiviKupci()
	{
		ArrayList<Korisnik> zaVracanje = new ArrayList<Korisnik>();
		for(Korisnik k : korisnikDao.getAllKupci()) 
		{
			int brojOtkazivanja=0;
			for(Porudzbina p : porudzbine) 
			{
				p.setKupac(korisnikDao.getById(p.getKupac().getId()));
				if(p.getKupac().getId()== k.getId() && k.getUloga().toString().equals("KUPAC")) 
				{
					if(p.getStatus().toString().equals("OTKAZANO")) 
					{
						if(p.getDatumIVreme().isAfter(LocalDateTime.now().minusMonths(1))) 
						{
							brojOtkazivanja++;
						}
					}
				}
			}
			if(brojOtkazivanja>=5 && k.getBlokiran()==0) 
			{
				zaVracanje.add(k);
			}
		}
		return zaVracanje;
		
	}
	public Porudzbina otkazi(int id)
	{
		for(Porudzbina p : porudzbine) 
		{
			if(p.getId()==id) 
			{
				p.setStatus(StatusPorudzbine.valueOf("OTKAZANO"));
			}
		}
		saveToFile();
		porudzbine.clear();
		loadPorudzbine();
		return null;
	}
	public boolean daLiJePocelo(int id) 
	{
		for(Porudzbina p : porudzbine) 
		{
			if(p.getId()==id) 
			{
				if(p.getDatumIVreme().isBefore(LocalDateTime.now())) 
				{
					return true;
				}
			}
		}
		return false;
	}
	public Porudzbina odobri(int id)
	{
		for(Porudzbina p : porudzbine) 
		{
			if(p.getId()==id) 
			{
				p.setStatus(StatusPorudzbine.valueOf("ODOBRENO"));
			}
		}
		saveToFile();
		porudzbine.clear();
		loadPorudzbine();
		return null;
	}
	public Porudzbina preuzeto(int id)
	{
		for(Porudzbina p : porudzbine) 
		{
			if(p.getId()==id) 
			{
				p.setStatus(StatusPorudzbine.valueOf("PREUZETO"));
			}
		}
		saveToFile();
		porudzbine.clear();
		loadPorudzbine();
		return null;
	}
	public Porudzbina vraceno(int id)
	{
		for(Porudzbina p : porudzbine) 
		{
			if(p.getId()==id) 
			{
				p.setStatus(StatusPorudzbine.valueOf("VRACENO"));
			}
		}
		saveToFile();
		porudzbine.clear();
		loadPorudzbine();
		return null;
	}
	public Porudzbina odbij(int id)
	{
		for(Porudzbina p : porudzbine) 
		{
			if(p.getId()==id) 
			{
				p.setStatus(StatusPorudzbine.valueOf("ODBIJENO"));
			}
		}
		saveToFile();
		porudzbine.clear();
		loadPorudzbine();
		return null;
	}
	public ArrayList<Porudzbina> getAll(){
		return new ArrayList<Porudzbina>(porudzbine);
	}
	public void saveToFile() {
        try {
              FileWriter file = new FileWriter(contextPath + "porudzbine.txt");

              BufferedWriter output = new BufferedWriter(file);

              for(Porudzbina p : porudzbine)
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
	public void loadPorudzbine() {
        BufferedReader in = null;
        try {
            File file = new File(contextPath + "porudzbine.txt");
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
                    int racoId = Integer.parseInt(st.nextToken().trim());
                    String timestampStr = st.nextToken().trim();
                    LocalDateTime timestamp = LocalDateTime.parse(timestampStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    //String dateStr = st.nextToken().trim();
                    //LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                    int trajanje = Integer.parseInt(st.nextToken().trim()); 
                    int cena = Integer.parseInt(st.nextToken().trim()); 
                    int kupacId = Integer.parseInt(st.nextToken().trim());
                    StatusPorudzbine status = StatusPorudzbine.valueOf(st.nextToken().trim());
                    int komentarisano = Integer.parseInt(st.nextToken().trim());
                    Porudzbina porudzbina = new Porudzbina();
                    porudzbina.setId(id);
                    RentACarObjekat raco = new RentACarObjekat();
                    raco.setId(racoId);
                    porudzbina.setRentACarObjekat(raco);
                    porudzbina.setDatumIVreme(timestamp);
                    porudzbina.setTrajanje(trajanje);
                    porudzbina.setCena(cena);
                    Korisnik korisnik = new Korisnik();
                    korisnik.setId(kupacId);
                    porudzbina.setKupac(korisnik);
                    porudzbina.setStatus(status);
                    porudzbina.setKomentarisano(komentarisano);
                    porudzbine.add(porudzbina);
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
	public Porudzbina dodaj(Porudzbina porudzbina) {
		porudzbina.setId(newId());
		porudzbine.add(porudzbina);
		saveToFile();
		porudzbine.clear();
		loadPorudzbine();
		return porudzbina;
	}
	public Porudzbina getById(int id)
	{
		Porudzbina pomPorudzbina = new Porudzbina();
		for(Porudzbina p : porudzbine)
		{
			if(p.getId() == id)
			{
				pomPorudzbina = p;
			}
		}
		
		if(pomPorudzbina == null)
		{
			return null;
		}
		
		return pomPorudzbina;
	}
	
	public ArrayList<Porudzbina> getAllKupacPorudzbina(int id){
		ArrayList<Porudzbina> pomLista = new ArrayList<Porudzbina>();
		
		for(Porudzbina p : porudzbine)
		{
			if(p.getKupac().getId() == id)
			{
				pomLista.add(p);
			}
		}
		
		return pomLista;
	}
	public void posaljiListu(ArrayList<Porudzbina> zaSlanje) {
		porudzbine = zaSlanje;
		saveToFile();
		porudzbine.clear();
		loadPorudzbine();
		return;
		
	}
}
