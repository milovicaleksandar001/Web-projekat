package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import model.Korisnik;
import model.Lokacija;

public class LokacijaDao {

	private ArrayList<Lokacija> lokacije;
	
	private final String contextPath="C:\\Users\\aleks\\Desktop\\WebPoceliRent\\WebRentACarProject\\RentACarProject\\src\\data\\";	
	
	public LokacijaDao()
	{
		lokacije =  new ArrayList<Lokacija>();
		loadLokacije();
	}


	public void loadLokacije() {
        BufferedReader in = null;
        try {
            File file = new File(contextPath + "lokacije.txt");
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
                    String geografskaDuzina = st.nextToken().trim();
                    String geografskaSirina = st.nextToken().trim();
                    String adresa = st.nextToken().trim();
                    Lokacija lokacija = new Lokacija();
                    lokacija.setId(id);
                    lokacija.setGeografskaDuzina(geografskaDuzina);
                    lokacija.setGeografskaSirina(geografskaSirina);
                    lokacija.setAdresa(adresa);
                    lokacije.add(lokacija);                   
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

	public ArrayList<Lokacija> getAll()
	{
		return new ArrayList<Lokacija>(lokacije);
	}
	
	public Lokacija getByAdresa(String adresa)
	{
		for(Lokacija l : lokacije)
		{
			if(l.getAdresa().equals(adresa))
			{
				return l;
			}
		}
		return null;
	}
}
