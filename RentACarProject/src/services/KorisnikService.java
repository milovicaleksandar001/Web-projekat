package services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.KorisnikDao;
import model.Korisnik;
import model.RentACarObjekat;

@Path("/users")
public class KorisnikService {
	@Context
    ServletContext ctx;
	
	public KorisnikService()
	{
		
	}
	
	@PostConstruct
    public void init() {
        if (ctx.getAttribute("korisnikDao") == null) {
            ctx.setAttribute("korisnikDao", new KorisnikDao());
        }
    }
	
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Korisnik> getKorisnici() {
        KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
        return dao.getAll();
    }
    
    @GET
    @Path("/checkBlock/{korIme}/{lozinka}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean proveriKorisnikaBlock (@PathParam("korIme") String korisnickoIme,@PathParam("lozinka") String lozinkaKorisnika){
    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		return dao.isBlocked(korisnickoIme, lozinkaKorisnika);
	}
    
    @GET
    @Path("/login/{korIme}/{lozinka}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean proveriKorisnika (@PathParam("korIme") String korisnickoIme,@PathParam("lozinka") String lozinkaKorisnika){
    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		return dao.checkUser(korisnickoIme, lozinkaKorisnika);
	}
    @GET
	 @Path("/search/{ime}/{prezime}/{korisnickoIme}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public ArrayList<Korisnik> search (@PathParam("ime") String ime,@PathParam("prezime") String prezime,@PathParam("korisnickoIme") String korisnickoIme){
    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		return dao.search(ime,prezime,korisnickoIme);
		}
	
    @GET
    @Path("/getByUsername/{korIme}")
    @Produces(MediaType.APPLICATION_JSON)
    public Korisnik getUserByUsername (@PathParam("korIme") String korisnickoIme){
    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		return dao.getUserByUsername(korisnickoIme);
	}
    @GET
	 @Path("/filter/{ime}/{prezime}/{uloga}/{korisnickoIme}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public ArrayList<Korisnik> filter (@PathParam("ime") String ime,@PathParam("prezime") String prezime,@PathParam("uloga") String uloga,@PathParam("korisnickoIme") String korisnickoIme){
    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		return dao.filter(ime,prezime,korisnickoIme,uloga);
		}
    @GET
    @Path("/menagerGlavni/{korIme}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean menagerRasporedjen (@PathParam("korIme") String korisnickoIme){
    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		return dao.menagerRasporedjen(korisnickoIme);
	}
    @GET
    @Path("/getObjekat/{korIme}")
    @Produces(MediaType.APPLICATION_JSON)
    public RentACarObjekat getObjekat (@PathParam("korIme") String korisnickoIme){
    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		return dao.getObjekatByKorisnicko(korisnickoIme);
	}
    @GET
    @Path("/managers")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Korisnik> getAllManagers (){
    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		return dao.getAllManagers();
	}
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Korisnik registerKorisnik(Korisnik korisnik) {
    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
    	Korisnik registeredKorisnik = dao.register(korisnik);
        return registeredKorisnik;
    }
	
    @GET
    @Path("/register/{korIme}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean proveriKorisnickoIme (@PathParam("korIme") String korisnickoIme){
    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		return dao.checkUserName(korisnickoIme);
	}
    
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik updateKorisnik(Korisnik korisnik) {
		KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		return dao.updateKorisnik(korisnik);
	}
	@PUT
	@Path("/bodovi/{id}/{bodovi}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean dodajBodove(@PathParam("id") String id,@PathParam("bodovi") String bodovi) {
		KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		int intId = Integer.parseInt(id);
		double doubleBodovi = Double.parseDouble(bodovi);
		return dao.DodajBodove(intId,doubleBodovi);
	}
	@PUT
	@Path("/oduzmiBodove/{id}/{bodovi}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean oduzmiBodove(@PathParam("id") String id,@PathParam("bodovi") String bodovi) {
		KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		int intId = Integer.parseInt(id);
		double doubleBodovi = Double.parseDouble(bodovi);
		return dao.oduzmiBodove(intId,doubleBodovi);
	}
	
	 @GET
	 @Path("/getById/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Korisnik getById (@PathParam("id") String id){
	   	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		int indId = Integer.parseInt(id);
		return dao.getById(indId);
		}
	 
	 
	@GET
	@Path("/register1/{korIme}/{pom}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean proveriKorisnickoIme1 (@PathParam("korIme") String korisnickoIme, @PathParam("pom") String pomoc){
		KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
		return dao.checkUserName1(korisnickoIme,pomoc);
		}
	
	 @GET
	 @Path("/getIdByUsername/{username}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public int getIdByUsername (@PathParam("username") String username){
	   	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");		
		return dao.GetIdByUsername(username);
		}
	 
	    @GET
	    @Path("/getAllKupci")
	    @Produces(MediaType.APPLICATION_JSON)
	    public ArrayList<Korisnik> getAllKupci (){
	    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
			return dao.getAllKupci();
		}
	    
	    @GET
	    @Path("/getAllSumnjiviKupci")
	    @Produces(MediaType.APPLICATION_JSON)
	    public ArrayList<Korisnik> getAllSumnjiviKupci (){
	    	KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
			return dao.getAllSumnjiviKupci();
		}
	    
	    @PUT
	    @Path("/blokiraj/{id}")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Korisnik blockKorisnik(@PathParam("id") String id) {
	        KorisnikDao dao = (KorisnikDao) ctx.getAttribute("korisnikDao");
	        int indId = Integer.parseInt(id);
	        return dao.blockKorisnik(indId);
	    }
}
