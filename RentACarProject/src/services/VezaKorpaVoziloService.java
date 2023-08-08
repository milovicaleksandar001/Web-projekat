package services;

import java.util.ArrayList;

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
import dao.VezaKorpaVoziloDao;
import model.Korisnik;
import model.VezaKorpaVozilo;
import model.Vozilo;

@Path("/veze")
public class VezaKorpaVoziloService {
	@Context
    ServletContext ctx;
	
	public VezaKorpaVoziloService()
	{
		
	}
	
	@PostConstruct
    public void init() {
        if (ctx.getAttribute("vezaKorpaVoziloDao") == null) {
            ctx.setAttribute("vezaKorpaVoziloDao", new VezaKorpaVoziloDao());
        }
    }
	@POST
    @Path("/dodavanje/{korpa}/{vozilo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VezaKorpaVozilo dodajVezu(@PathParam("korpa") String korpa,@PathParam("vozilo") String vozilo) {
		VezaKorpaVoziloDao dao = (VezaKorpaVoziloDao) ctx.getAttribute("vezaKorpaVoziloDao");
		int indKorpa = Integer.parseInt(korpa);
		int indVozilo = Integer.parseInt(vozilo);
		VezaKorpaVozilo vkz = new VezaKorpaVozilo();
		vkz.setKorpa(indKorpa);
		vkz.setVozilo(indVozilo);
		VezaKorpaVozilo dodataVeza = dao.dodaj(vkz);
        return dodataVeza;
    }
	@PUT
	@Path("/update/{korpa}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean brisanjeKorpe(@PathParam("korpa") String korpa) {
		VezaKorpaVoziloDao dao = (VezaKorpaVoziloDao) ctx.getAttribute("vezaKorpaVoziloDao");
		int indKorpa = Integer.parseInt(korpa);
		return dao.brisanjeKorpe(indKorpa);
	}
	@PUT
	@Path("/obrisiJedan/{korpa}/{vozilo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean brisanjeJednogVozila(@PathParam("korpa") String korpa,@PathParam("vozilo") String vozilo) {
		VezaKorpaVoziloDao dao = (VezaKorpaVoziloDao) ctx.getAttribute("vezaKorpaVoziloDao");
		int indKorpa = Integer.parseInt(korpa);
		int intVozilo = Integer.parseInt(vozilo);
		return dao.brisanjeJednog(indKorpa,intVozilo);
	}
	 @GET
	 @Path("/getAllVozilaFromCart/{idKorpe}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public ArrayList<Vozilo> getAllVozilaFromCart (@PathParam("idKorpe") String id){
		 VezaKorpaVoziloDao dao = (VezaKorpaVoziloDao) ctx.getAttribute("vezaKorpaVoziloDao");	
		 int indId = Integer.parseInt(id);
		return dao.getAllVozilaFromCart(indId);
		}

}
