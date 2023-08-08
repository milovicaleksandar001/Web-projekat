package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import enums.Pol;
import enums.Status;
import enums.StatusVozilo;
import enums.Uloga;
import model.Korisnik;
import model.Korpa;
import model.Lokacija;
import model.Porudzbina;
import model.RentACarObjekat;
import model.TipKupca;
import model.Vozilo;
import enums.Vrsta;
import enums.TipGoriva;

public class VoziloDao {
	
	private ArrayList<Vozilo> vozila;
	private RentACarObjekatDao racoDao;
	
	private final String contextPath="C:\\Users\\aleks\\Desktop\\WebPoceliRent\\WebRentACarProject\\RentACarProject\\src\\data\\";	
	
	public VoziloDao()
	{
		vozila =  new ArrayList<Vozilo>();
		racoDao = new RentACarObjekatDao();
		loadVozila();
	}
	private int newId() {
		int id = 0;
		for(Vozilo v : vozila) {
			if(v.getId() > id) {
				id = v.getId();
			}
		}
		return id + 1;
	}
	public ArrayList<RentACarObjekat> filter(String naziv, String tip, String lokacija, int ocena,String vrsta,String tipGoriva,String status)
	{
		ArrayList<RentACarObjekat> objektiZaVracanje = new ArrayList<RentACarObjekat>();
		ArrayList<RentACarObjekat> trenutniObjekti = new ArrayList<RentACarObjekat>();
		trenutniObjekti = search(naziv,tip,lokacija,ocena);
		if(vrsta.equals(" ")) 
		{
			vrsta = "";
		}
		if(tipGoriva.equals(" ")) 
		{
			tipGoriva = "";
		}
		if(status.equals(" ")) 
		{
			status = "";
		}
		for(RentACarObjekat raco:trenutniObjekti) 
		{
			if(raco.getStatus().toString().equals(status) || status=="") 
			{
				int flag=0;
				for(Vozilo v : vozila) 
				{
					if((v.getObjekatKomPripada().getId()==raco.getId() && v.getVrsta().toString().equals(vrsta)) || vrsta=="") 
					{
						if((v.getObjekatKomPripada().getId()==raco.getId() && v.getTipGoriva().toString().equals(tipGoriva)) || tipGoriva=="") 
						{
							if(flag==0)
							{
							objektiZaVracanje.add(raco);
							flag=1;
							}
						}
					}
				}
			}
		}
		return objektiZaVracanje;
	}
	public ArrayList<Vozilo> vozilaObjekta(int idObjekta)
	{
		ArrayList<Vozilo> objektiZaVracanje = new ArrayList<Vozilo>();
		for(Vozilo v : vozila) 
		{
			if(v.getObjekatKomPripada().getId()==idObjekta) 
			{
				objektiZaVracanje.add(v);
			}
		}
		return objektiZaVracanje;
	}
	public boolean promeniIznajmljeno(int id) 
	{
		for(Vozilo v : vozila) 
		{
			if(v.getId()==id) 
			{
				v.setStatus(StatusVozilo.valueOf("IZNAJMLJENO"));
			}
		}
		saveToFile();
		vozila.clear();
		loadVozila();
		return true;
	}
	public boolean promeniVraceno(int id) 
	{
		for(Vozilo v : vozila) 
		{
			if(v.getId()==id) 
			{
				v.setStatus(StatusVozilo.valueOf("DOSTUPNO"));
			}
		}
		saveToFile();
		vozila.clear();
		loadVozila();
		return true;
	}
	public ArrayList<RentACarObjekat> search(String naziv, String tip, String lokacija, int ocena)
	{
		ArrayList<RentACarObjekat> sviObjekti = new ArrayList<RentACarObjekat>();
		ArrayList<RentACarObjekat> objektiZaVracanje = new ArrayList<RentACarObjekat>();
		sviObjekti = racoDao.prikazSvih();
		if(naziv.equals(" ")) 
		{
			naziv = "";
		}
		if(tip.equals(" ")) 
		{
			tip = "";
		}
		if(lokacija.equals(" ")) 
		{
			lokacija = "";
		}
		for(RentACarObjekat raco : sviObjekti) 
		{
			if(raco.getNaziv().contains(naziv)) 
			{
				if(raco.getLokacija().getAdresa().contains(lokacija)) 
				{
					if(raco.getOcena()==ocena || ocena==0) 
					{
						int flag = 0;
						for(Vozilo v : vozila) 
						{
							if(tip.equals("")) 
							{
								if(flag==0)
								{
								objektiZaVracanje.add(raco);
								flag=1;
								}
							}else {
							if(v.getObjekatKomPripada().getId()==raco.getId() && v.getTip().contains(tip)) 
							{
								if(flag==0)
								{
								objektiZaVracanje.add(raco);
								flag=1;
								}
							}
							}
						}
					}
				}
			}
		}
		return objektiZaVracanje;
	}
	public ArrayList<Vozilo> getAll(){
		ArrayList<Vozilo> svaVozila = new ArrayList<Vozilo>();
		for (Vozilo v : vozila)
		{
			for(RentACarObjekat raco : racoDao.prikazSvih()) 
			{
				if(v.getObjekatKomPripada().getId()==raco.getId()) 
				{
					v.setObjekatKomPripada(raco);
				}
			}
			if(v.getObrisano()==0) {
			svaVozila.add(v);
			}
		}
		return svaVozila;
	}
	public void saveToFile() {
        try {
              FileWriter file = new FileWriter(contextPath + "vozila.txt");

              BufferedWriter output = new BufferedWriter(file);

              for(Vozilo v : vozila)
              {
                  output.write(v.fileLine());
              }
              
              output.close();
              
        }     
        catch (Exception e) {
        	  System.out.println(e);
              e.getStackTrace();
            }
    }
	public void loadVozila() {
        BufferedReader in = null;
        try {
            File file = new File(contextPath + "vozila.txt");
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
                    String marka = st.nextToken().trim();
                    String model = st.nextToken().trim();
                    int cena = Integer.parseInt(st.nextToken().trim());
                    String tip = st.nextToken().trim();
                    Vrsta vrsta = Vrsta.valueOf(st.nextToken().trim());
                    TipGoriva tipGoriva = TipGoriva.valueOf(st.nextToken().trim());
                    int potrosnja = Integer.parseInt(st.nextToken().trim());
                    int brojVrata = Integer.parseInt(st.nextToken().trim());
                    int brojPutnika = Integer.parseInt(st.nextToken().trim());
                    String slika = st.nextToken().trim();
                    String opis = st.nextToken().trim();
                    int objekatId = Integer.parseInt(st.nextToken().trim());
                    StatusVozilo status = StatusVozilo.valueOf(st.nextToken().trim());
                    int obrisan = Integer.parseInt(st.nextToken().trim());
                    Vozilo vozilo = new Vozilo();
                    vozilo.setId(id);
                    vozilo.setMarka(marka);
                    vozilo.setModel(model);
                    vozilo.setCena(cena);
                    vozilo.setTip(tip);
                    vozilo.setVrsta(vrsta);
                    vozilo.setTipGoriva(tipGoriva);
                    vozilo.setPotrosnja(potrosnja);
                    vozilo.setBrojVrata(brojVrata);
                    vozilo.setBrojPutnika(brojPutnika);
                    vozilo.setSlika(slika);
                    vozilo.setOpis(opis);
                    RentACarObjekat raco = new RentACarObjekat();
                    raco.setId(objekatId);
                    vozilo.setObjekatKomPripada(raco);
                    vozilo.setStatus(status);
                    vozilo.setObrisano(obrisan);
                    vozila.add(vozilo);
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
	public Vozilo add(Vozilo vozilo) {
		vozilo.setId(newId());
		vozila.add(vozilo);
		saveToFile();
		vozila.clear();
		loadVozila();
		return vozilo;
	}
	public Vozilo getById(int id)
	{
		Vozilo pomVozilo = new Vozilo();
		for(Vozilo v : vozila)
		{
			if(v.getId() == id)
			{
				pomVozilo = v;
			}
		}
		
		if(pomVozilo == null)
		{
			return null;
		}
		
		return pomVozilo;
	}
	public ArrayList<Vozilo> getAllManagersVozila(int idMenadzera) 
	{
		ArrayList<Vozilo> vozilaMenadzera = new ArrayList<Vozilo>();
		for (Vozilo v : vozila)
		{	
			for(RentACarObjekat raco : racoDao.prikazSvih()) 
			{
				if(v.getObjekatKomPripada().getId()==raco.getId()) 
				{
					v.setObjekatKomPripada(raco);
				}
			}
			if(v.getObjekatKomPripada().getMenadzer().getId()==idMenadzera) 
			{
				if(v.getObrisano()==0)
				{
				vozilaMenadzera.add(v);
				}
			}
		}
		return vozilaMenadzera;
	}
	public Vozilo updateVozilo(Vozilo vozilo)
	{
		for(Vozilo v : vozila)
		{
			if(v.getId() == vozilo.getId())
			{
				v.setMarka(vozilo.getMarka());
                v.setModel(vozilo.getModel());
                v.setCena(vozilo.getCena());
                v.setTip(vozilo.getTip());
                v.setVrsta(vozilo.getVrsta());
                v.setTipGoriva(vozilo.getTipGoriva());
                v.setPotrosnja(vozilo.getPotrosnja());
                v.setBrojVrata(vozilo.getBrojVrata());
                v.setBrojPutnika(vozilo.getBrojPutnika());
                v.setSlika(vozilo.getSlika());
                v.setOpis(vozilo.getOpis());
                v.setStatus(vozilo.getStatus());
                v.setObjekatKomPripada(vozilo.getObjekatKomPripada());
			}
		}
		saveToFile();
		vozila.clear();
		loadVozila();
		return vozilo;
	}
	public Vozilo brisanje(Vozilo vozilo) 
	{
		for(Vozilo v : vozila)
		{
			if(v.getId() == vozilo.getId())
			{
				v.setObrisano(1);
			}
		}
		saveToFile();
		vozila.clear();
		loadVozila();
		return vozilo;
	}
	
}

