package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import dao.PorudzbinaDao;
import enums.StatusPorudzbine;
import model.Korisnik;
import model.Porudzbina;
import model.RentACarObjekat;

@Path("/porudzbine")
public class PorudzbinaService {
	@Context
    ServletContext ctx;
	
	public PorudzbinaService()
	{
		
	}
	
	@PostConstruct
    public void init() {
        if (ctx.getAttribute("porudzbinaDao") == null) {
            ctx.setAttribute("porudzbinaDao", new PorudzbinaDao());
        }
    }
	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Porudzbina> getPorudzbine() {
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
        return dao.getAll();
    }
	@POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Porudzbina dodajPorudzbinu(Porudzbina porudzbina) {
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
		Porudzbina dodataPorudzbina = dao.dodaj(porudzbina);
        return dodataPorudzbina;
    }
	@GET
	 @Path("/getById/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Porudzbina getById (@PathParam("id") String id){
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
		int indId = Integer.parseInt(id);
		return dao.getById(indId);
		}
	@GET
    @Path("/getAllSumnjiviKupci")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Korisnik> getAllSumnjiviKupci() {
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
        return dao.getAllSumnjiviKupci();
    }
	@GET
	 @Path("/daLiJePocelo/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public boolean daLiJePocelo (@PathParam("id") String id){
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
		int indId = Integer.parseInt(id);
		return dao.daLiJePocelo(indId);
		}
	@PUT
	@Path("/otkazi/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Porudzbina otkazi(@PathParam("id") String id) {
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
		int indId = Integer.parseInt(id);
		return dao.otkazi(indId);
	}
	@PUT
	@Path("/odobri/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Porudzbina odobri(@PathParam("id") String id) {
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
		int indId = Integer.parseInt(id);
		return dao.odobri(indId);
	}
	@PUT
	@Path("/preuzeto/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Porudzbina preuzeto(@PathParam("id") String id) {
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
		int indId = Integer.parseInt(id);
		return dao.preuzeto(indId);
	}
	@PUT
	@Path("/vraceno/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Porudzbina vraceno(@PathParam("id") String id) {
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
		int indId = Integer.parseInt(id);
		return dao.vraceno(indId);
	}
	@PUT
	@Path("/odbij/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Porudzbina odbij(@PathParam("id") String id) {
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
		int indId = Integer.parseInt(id);
		return dao.odbij(indId);
	}
	@GET
	 @Path("/dodavanje/{id}/{pocetniDatum}/{ukupnaCena}/{brojDana}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public int dodavanjeIDobijanjeId (@PathParam("id") String id,@PathParam("pocetniDatum") String pocetniDatum,@PathParam("ukupnaCena") String ukupnaCena,@PathParam("brojDana") String brojDana){
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
		int intId = Integer.parseInt(id);
		int intCena = Integer.parseInt(ukupnaCena);
		int intDana = Integer.parseInt(brojDana);
		LocalDate startDate = LocalDate.parse(pocetniDatum, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDateTime startTime = startDate.atStartOfDay();
		Porudzbina porudzbina = new Porudzbina();
		porudzbina.setCena(intCena);
		porudzbina.setDatumIVreme(startTime);
		Korisnik korisnik = new Korisnik();
		korisnik.setId(intId);
		porudzbina.setKupac(korisnik);
		RentACarObjekat raco = new RentACarObjekat();
		raco.setId(0);
		porudzbina.setRentACarObjekat(raco);
		porudzbina.setStatus(StatusPorudzbine.valueOf("OBRADA"));
		porudzbina.setTrajanje(intDana);
		porudzbina = dao.dodaj(porudzbina);
		return porudzbina.getId();
		}
	@GET
	 @Path("/getByKupacId/{kupacId}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Porudzbina> getByKupacId (@PathParam("kupacId") String id){
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
		int indId = Integer.parseInt(id);
		return dao.getAllKupacPorudzbina(indId);
		}
	@GET
	 @Path("/searchKupac/{cenaOd}/{cenaDo}/{datumOd}/{datumDo}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public ArrayList<Porudzbina> searchKupac (@PathParam("cenaOd") String cenaOd,@PathParam("cenaDo") String cenaDo,@PathParam("datumOd") String datumOd,@PathParam("datumDo") String datumDo){
		PorudzbinaDao dao = (PorudzbinaDao) ctx.getAttribute("porudzbinaDao");
		int indCenaOd = Integer.parseInt(cenaOd);
		int indCenaDo = Integer.parseInt(cenaDo);
		LocalDate startDate = LocalDate.parse(datumOd, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDateTime startTime = startDate.atStartOfDay();
		LocalDate endDate = LocalDate.parse(datumDo, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDateTime endTime = endDate.atStartOfDay();
		return dao.searchKupac(indCenaOd,indCenaDo,startTime,endTime);
		}
	
}
