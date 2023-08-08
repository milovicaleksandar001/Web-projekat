package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import model.Pomoc;
import model.VezaKorpaVozilo;
import model.Vozilo;

public class VezaKorpaVoziloDao {
	
	private ArrayList<VezaKorpaVozilo> veze;
	private VoziloDao voziloDao;
	
	private final String contextPath="C:\\Users\\aleks\\Desktop\\WebPoceliRent\\WebRentACarProject\\RentACarProject\\src\\data\\";	
	
	public VezaKorpaVoziloDao()
	{
		veze = new ArrayList<VezaKorpaVozilo>();
		voziloDao = new VoziloDao();
		loadVeze();
	}
	public ArrayList<VezaKorpaVozilo> getAll(){
		return new ArrayList<VezaKorpaVozilo>(veze);
	}
	public void saveToFile() {
        try {
              FileWriter file = new FileWriter(contextPath + "veze.txt");

              BufferedWriter output = new BufferedWriter(file);

              for(VezaKorpaVozilo v : veze)
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
	public void loadVeze() {
        BufferedReader in = null;
        try {
            File file = new File(contextPath + "veze.txt");
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
                    int korpaId = Integer.parseInt(st.nextToken().trim());    
                    int voziloId = Integer.parseInt(st.nextToken().trim());
                    int obrisano = Integer.parseInt(st.nextToken().trim());
                    VezaKorpaVozilo veza = new VezaKorpaVozilo();
                    veza.setKorpa(korpaId);
                    veza.setVozilo(voziloId);
                    veza.setObrisano(obrisano);
                    veze.add(veza);
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
	public VezaKorpaVozilo dodaj(VezaKorpaVozilo veza) {
		veza.setObrisano(0);
		veze.add(veza);
		saveToFile();
		veze.clear();
		loadVeze();
		return veza;
	}
	public boolean brisanjeKorpe(int idKorpe) 
	{
		for(VezaKorpaVozilo v : veze) 
		{
			if(v.getKorpa() == idKorpe && v.getObrisano()==0)
			{
				v.setObrisano(1);
			}
		}
		saveToFile();
		veze.clear();
		loadVeze();
		return true;
	}
	public boolean brisanjeJednog(int idKorpe,int idVozila) 
	{
		for(VezaKorpaVozilo v : veze) 
		{
			if(v.getKorpa() == idKorpe && v.getObrisano()==0 && v.getVozilo() == idVozila)
			{
				v.setObrisano(1);
			}
		}
		saveToFile();
		veze.clear();
		loadVeze();
		return true;
	}
	
	public ArrayList<Vozilo> getAllVozilaFromCart(int id) 
	{
		ArrayList<Vozilo> pomLista = new ArrayList<Vozilo>();
	
		
		for (VezaKorpaVozilo v : veze)
		{	
			if(v.getKorpa() == id && v.getObrisano()==0)
			{
				Vozilo pomVozilo = voziloDao.getById(v.getVozilo());
				pomLista.add(pomVozilo);
			}
		}
		
		return pomLista;
	}
	public ArrayList<Vozilo> getAllVozilaFromCartLoad(int id) 
	{
		veze.clear();
		loadVeze();
		ArrayList<Vozilo> pomLista = new ArrayList<Vozilo>();
	
		
		for (VezaKorpaVozilo v : veze)
		{	
			if(v.getKorpa() == id && v.getObrisano()==0)
			{
				Vozilo pomVozilo = voziloDao.getById(v.getVozilo());
				pomLista.add(pomVozilo);
			}
		}
		
		return pomLista;
	}
}
