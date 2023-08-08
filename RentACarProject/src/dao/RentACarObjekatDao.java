package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import enums.Status;
import model.Korisnik;
import model.Lokacija;
import model.RentACarObjekat;

public class RentACarObjekatDao {
	private ArrayList<RentACarObjekat> objekti;
	private LokacijaDao lokacijaDao;
	
	private final String contextPath="C:\\Users\\aleks\\Desktop\\WebPoceliRent\\WebRentACarProject\\RentACarProject\\src\\data\\";
	
	public RentACarObjekatDao() {
		objekti = new ArrayList<RentACarObjekat>();
		lokacijaDao = new LokacijaDao();
		loadObjekti();
	}
	public int GenerateId() {
		int id = 0;
		for (RentACarObjekat o : objekti) {
			if (o.getId() > id) {
				id = o.getId();
			}
		}
		return id + 1;
	}
	public RentACarObjekat register(RentACarObjekat rentACarObjekat) {
		rentACarObjekat.setId(GenerateId());
		objekti.add(rentACarObjekat);
		saveToFile();
		objekti.clear();
		loadObjekti();
		return rentACarObjekat;
	}
	public boolean dodajOcenu(double ocena,int idRaca) 
	{
		for(RentACarObjekat raco : objekti) 
		{
			if(raco.getId()==idRaca) 
			{
				int pom = raco.getBrojKomentara();
				double pomOcena = raco.getOcena();
				double novaOcena = pomOcena*pom;
				novaOcena = novaOcena+ocena;
				pom++;
				novaOcena = novaOcena/pom;
				raco.setBrojKomentara(pom);
				raco.setOcena(novaOcena);
			}
		}
		saveToFile();
		objekti.clear();
		loadObjekti();
		return true;
	}
	public RentACarObjekat getById(int id)
	{
		RentACarObjekat pomRACO = new RentACarObjekat();
		for(RentACarObjekat raco : objekti)
		{
			if(raco.getId() == id)
			{
				pomRACO = raco;
				for(Lokacija l : lokacijaDao.getAll()) 
				{
					if(l.getId()==raco.getLokacija().getId()) 
					{
						pomRACO.setLokacija(l);
					}
				}
			}
		}
		
		if(pomRACO == null)
		{
			return null;
		}
		
		return pomRACO;
	}
	public int getObjectIdByManagerId(int idKorisnika) 
	{
		for(RentACarObjekat raco : objekti) 
		{
			if(raco.getMenadzer().getId()==idKorisnika) 
			{
				return raco.getId();
			}
		}
		return 0;
	}
	public ArrayList<RentACarObjekat> prikazSvih() {
		ArrayList<RentACarObjekat> raciZaVracanje = new ArrayList<RentACarObjekat>();
		for(RentACarObjekat raco : objekti) 
		{
			for(Lokacija lokacija : lokacijaDao.getAll()) 
			{
				if(lokacija.getId()==raco.getLokacija().getId()) 
				{
					raco.setLokacija(lokacija);
					raciZaVracanje.add(raco);
				}
			}
		}
		return raciZaVracanje; 
	}
	public void saveToFile() {
        try {
              FileWriter file = new FileWriter(contextPath + "rentACarObjekti.txt");

              BufferedWriter output = new BufferedWriter(file);

              for(RentACarObjekat raco : objekti)
              {
                  output.write(raco.fileLine());
              }
              
              output.close();
              
        }     
        catch (Exception e) {
        	  System.out.println(e);
              e.getStackTrace();
            }
    }
	public void loadObjekti() {
        BufferedReader in = null;
        try {
            File file = new File(contextPath + "rentACarObjekti.txt");
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
                    String naziv = st.nextToken().trim();
                    int lokacijaId = Integer.parseInt(st.nextToken().trim());
                    String radnoVreme = st.nextToken().trim();
                    String logo = st.nextToken().trim();
                    int menadzerId = Integer.parseInt(st.nextToken().trim());
                    Status status = Status.valueOf(st.nextToken().trim());
                    double ocena = Double.parseDouble(st.nextToken().trim());
                    int brojKomentara = Integer.parseInt(st.nextToken().trim());
                    RentACarObjekat raco = new RentACarObjekat();
                    raco.setId(id);
                    raco.setNaziv(naziv);
                    Lokacija lokacija = new Lokacija();
                    lokacija.setId(lokacijaId);
                    raco.setLokacija(lokacija);
                    raco.setRadnoVreme(radnoVreme);
                    raco.setLogo(logo);
                    Korisnik menadzer = new Korisnik();
                    menadzer.setId(menadzerId);
                    raco.setMenadzer(menadzer);
                    raco.setStatus(status);
                    raco.setOcena(ocena);
                    raco.setBrojKomentara(brojKomentara);
                    objekti.add(raco);
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
	
	public ArrayList<RentACarObjekat> prikazSvihSort() {
		
		ArrayList<RentACarObjekat> raciZaVracanje = new ArrayList<RentACarObjekat>();
		for(RentACarObjekat raco : objekti) 
		{
			if(raco.getStatus().toString() == "RADI")
			{
				for(Lokacija lokacija : lokacijaDao.getAll()) 
				{
					if(lokacija.getId()==raco.getLokacija().getId()) 
					{
						raco.setLokacija(lokacija);
						raciZaVracanje.add(raco);
					}
				}
			}
		}
		
		for(RentACarObjekat raco : objekti) 
		{
			if(raco.getStatus().toString() == "NE_RADI")
			{
				for(Lokacija lokacija : lokacijaDao.getAll()) 
				{
					if(lokacija.getId()==raco.getLokacija().getId()) 
					{
						raco.setLokacija(lokacija);
						raciZaVracanje.add(raco);
					}
				}
			}
		}
		return raciZaVracanje; 
	}
	
	public boolean ocisti() 
    {
        objekti.clear();
        loadObjekti();
        return true;
    }
}
