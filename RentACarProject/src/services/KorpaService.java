package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import dao.KorpaDao;
import model.Korisnik;
import model.Korpa;

@Path("/korpe")
public class KorpaService {
	@Context
    ServletContext ctx;
	
	public KorpaService()
	{
		
	}
	
	@PostConstruct
    public void init() {
        if (ctx.getAttribute("korpaDao") == null) {
            ctx.setAttribute("korpaDao", new KorpaDao());
        }
    }
	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Korpa> getKorpe() {
		KorpaDao dao = (KorpaDao) ctx.getAttribute("korpaDao");
        return dao.getAll();
    }
	@POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Korpa dodajKorpu(Korpa korpa) {
		KorpaDao dao = (KorpaDao) ctx.getAttribute("korpaDao");
		Korpa dodataKorpa = dao.dodaj(korpa);
        return dodataKorpa;
    }
	@GET
	 @Path("/getById/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Korpa getById (@PathParam("id") String id){
		KorpaDao dao = (KorpaDao) ctx.getAttribute("korpaDao");
		int indId = Integer.parseInt(id);
		return dao.getById(indId);
		}
	@GET
	 @Path("/datumPocetka/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String datumPocetka (@PathParam("id") String id){
		KorpaDao dao = (KorpaDao) ctx.getAttribute("korpaDao");
		int indId = Integer.parseInt(id);
		return dao.datumPocetka(indId);
		}
	@PUT
	@Path("/update/{id}/{start}/{end}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Korpa updateKorpa(@PathParam("id") String id,@PathParam("start") String start,@PathParam("end") String end) {
		KorpaDao dao = (KorpaDao) ctx.getAttribute("korpaDao");
		int intId = Integer.parseInt(id);
		LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDateTime startTime = startDate.atStartOfDay();
		LocalDateTime endTime = endDate.atStartOfDay();
		return dao.updateKorpa(intId,startTime,endTime);
	}
	@GET
	 @Path("/getIdByUserId/{userId}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public int getKorpaIdByUserId (@PathParam("userId") String id){
		KorpaDao dao = (KorpaDao) ctx.getAttribute("korpaDao");
		int indId = Integer.parseInt(id);
		return dao.getKorpaIdByUserId(indId);
		}
	@GET
	 @Path("/getNumberOfDays/{korpaId}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public int getNumberOfDaysByKorpaId (@PathParam("korpaId") String id){
		KorpaDao dao = (KorpaDao) ctx.getAttribute("korpaDao");
		int indId = Integer.parseInt(id);
		return dao.getDaysOfPorudzbinaByKorpaId(indId);
		}
}
