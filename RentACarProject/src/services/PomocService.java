package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.swing.text.DateFormatter;
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
import dao.PomocDao;
import dao.PorudzbinaDao;
import model.Korisnik;
import model.Pomoc;
import model.Porudzbina;
import model.RentACarObjekat;
import model.Vozilo;

@Path("/pomoci")
public class PomocService {
	@Context
    ServletContext ctx;
	
	public PomocService()
	{
		
	}
	
	@PostConstruct
    public void init() {
        if (ctx.getAttribute("pomocDao") == null) {
            ctx.setAttribute("pomocDao", new PomocDao());
        }
    }
	@GET
    @Path("/nadjiVozila/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Vozilo> getVozila(@PathParam("start") String start,@PathParam("end") String end) {
		PomocDao dao = (PomocDao) ctx.getAttribute("pomocDao");
		LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDateTime startTime = startDate.atStartOfDay();
		LocalDateTime endTime = endDate.atStartOfDay();
        return dao.getVozila(startTime,endTime);
    }
	@GET
    @Path("/porudzbineObjekta/{idObjekta}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Porudzbina> getPorudzbineObjekta(@PathParam("idObjekta") String idObjekta) {
		PomocDao dao = (PomocDao) ctx.getAttribute("pomocDao");
		int intId = Integer.parseInt(idObjekta);
        return dao.porudzbineMenadzera(intId);
    }
	@GET
    @Path("/objektiZaKomentarisanje/{idKorisnika}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<RentACarObjekat> objektiZaKomentarisanje(@PathParam("idKorisnika") String idKorisnika) {
		PomocDao dao = (PomocDao) ctx.getAttribute("pomocDao");
		System.out.println(idKorisnika);
		int intId = Integer.parseInt(idKorisnika);
        return dao.komentarisanje(intId);
    }
	@POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Pomoc dodajPomoc(Pomoc pomoc) {
		PomocDao dao = (PomocDao) ctx.getAttribute("pomocDao");
		Pomoc dodataPomoc = dao.dodaj(pomoc);
        return dodataPomoc;
    }
	@POST
    @Path("/dodavanje/{korpa}/{porudzbina}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean dodavanje(@PathParam("korpa") String korpa,@PathParam("porudzbina") String porudzbina) {
		PomocDao dao = (PomocDao) ctx.getAttribute("pomocDao");
		int intKorpa = Integer.parseInt(korpa);
		int intPorudzbina = Integer.parseInt(porudzbina);
        return dao.dodavanje(intPorudzbina,intKorpa);
    }
	@PUT
	@Path("/iznajmljeno/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean iznajmljeno(@PathParam("id") String id) {
		PomocDao dao = (PomocDao) ctx.getAttribute("pomocDao");
		int indId = Integer.parseInt(id);
		return dao.iznajmljeno(indId);
	}
	@PUT
	@Path("/ugasitiKomentarisanje/{idRaca}/{idKorisnika}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean ugasitiKomentarisanje(@PathParam("idRaca") String idRaca,@PathParam("idKorisnika") String idKorisnika) {
		PomocDao dao = (PomocDao) ctx.getAttribute("pomocDao");
		int indId = Integer.parseInt(idRaca);
		int indIdKorisnika = Integer.parseInt(idKorisnika);
		return dao.ugasitiKomentarisanje(indId,indIdKorisnika);
	}
	@PUT
	@Path("/vracanje/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean vracanje(@PathParam("id") String id) {
		PomocDao dao = (PomocDao) ctx.getAttribute("pomocDao");
		int indId = Integer.parseInt(id);
		return dao.vracanje(indId);
	}
	@GET
	 @Path("/searchMenadzer/{cenaOd}/{cenaDo}/{datumOd}/{datumDo}/{idObjekta}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public ArrayList<Porudzbina> searchMenadzer (@PathParam("cenaOd") String cenaOd,@PathParam("cenaDo") String cenaDo,@PathParam("datumOd") String datumOd,@PathParam("datumDo") String datumDo,@PathParam("idObjekta") String idObjekta){
		PomocDao dao = (PomocDao) ctx.getAttribute("pomocDao");
		int indCenaOd = Integer.parseInt(cenaOd);
		int indCenaDo = Integer.parseInt(cenaDo);
		int intId = Integer.parseInt(idObjekta);
		LocalDate startDate = LocalDate.parse(datumOd, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDateTime startTime = startDate.atStartOfDay();
		LocalDate endDate = LocalDate.parse(datumDo, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDateTime endTime = endDate.atStartOfDay();
		return dao.searchMenadzer(indCenaOd,indCenaDo,startTime,endTime,intId);
		}
	@GET
	 @Path("/searchKupac/{cenaOd}/{cenaDo}/{datumOd}/{datumDo}/{idObjekta}/{idKorisnika}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public ArrayList<Porudzbina> searchKupac (@PathParam("cenaOd") String cenaOd,@PathParam("cenaDo") String cenaDo,@PathParam("datumOd") String datumOd,@PathParam("datumDo") String datumDo,@PathParam("idObjekta") String idObjekta,@PathParam("idKorisnika") String idKorisnika){
		PomocDao dao = (PomocDao) ctx.getAttribute("pomocDao");
		int indCenaOd = Integer.parseInt(cenaOd);
		int indCenaDo = Integer.parseInt(cenaDo);
		int intId = Integer.parseInt(idObjekta);
		int intIdKupca = Integer.parseInt(idKorisnika);
		LocalDate startDate = LocalDate.parse(datumOd, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDateTime startTime = startDate.atStartOfDay();
		LocalDate endDate = LocalDate.parse(datumDo, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDateTime endTime = endDate.atStartOfDay();
		return dao.searchKupac(indCenaOd,indCenaDo,startTime,endTime,intId,intIdKupca);
		}
	
	
}
