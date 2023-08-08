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

import dao.VoziloDao;
import model.RentACarObjekat;
import model.Vozilo;

@Path("/vozila")
public class VoziloService {
	@Context
    ServletContext ctx;
	
	public VoziloService()
	{
		
	}
	
	@PostConstruct
    public void init() {
        if (ctx.getAttribute("voziloDao") == null) {
            ctx.setAttribute("voziloDao", new VoziloDao());
        }
    }
	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vozilo> getVozila() {
        VoziloDao dao = (VoziloDao) ctx.getAttribute("voziloDao");
        return dao.getAll();
    }
	@POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Vozilo addVozilo(Vozilo vozilo) {
		System.out.println("aaaaaaa");
		VoziloDao dao = (VoziloDao) ctx.getAttribute("voziloDao");
		Vozilo addedVozilo = dao.add(vozilo);
        return addedVozilo;
    }
	@GET
	 @Path("/getById/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Vozilo getById (@PathParam("id") String id){
		VoziloDao dao = (VoziloDao) ctx.getAttribute("voziloDao");
		int indId = Integer.parseInt(id);
		return dao.getById(indId);
		}
	@GET
	 @Path("/getByManagerId/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Vozilo> getByManagerId (@PathParam("id") String id){
		VoziloDao dao = (VoziloDao) ctx.getAttribute("voziloDao");
		int indId = Integer.parseInt(id);
		return dao.getAllManagersVozila(indId);
		}
	@GET
	 @Path("/search/{naziv}/{tip}/{lokacija}/{ocena}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public ArrayList<RentACarObjekat> search (@PathParam("naziv") String naziv,@PathParam("tip") String tip,@PathParam("lokacija") String lokacija,@PathParam("ocena") String ocena){
		VoziloDao dao = (VoziloDao) ctx.getAttribute("voziloDao");
		int intOcena = Integer.parseInt(ocena);
		return dao.search(naziv,tip,lokacija,intOcena);
		}
	@GET
	 @Path("/filter/{naziv}/{tip}/{lokacija}/{status}/{vrsta}/{tipGoriva}/{ocena}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public ArrayList<RentACarObjekat> filter (@PathParam("naziv") String naziv,@PathParam("tip") String tip,@PathParam("lokacija") String lokacija,@PathParam("status") String status,@PathParam("vrsta") String vrsta,@PathParam("tipGoriva") String tipGoriva,@PathParam("ocena") String ocena){
		VoziloDao dao = (VoziloDao) ctx.getAttribute("voziloDao");
		int intOcena = Integer.parseInt(ocena);
		return dao.filter(naziv,tip,lokacija,intOcena,vrsta,tipGoriva,status);
		}
	@GET
	 @Path("/getVozilaObjekta/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Vozilo> getVozilaObjekta (@PathParam("id") String id){
		VoziloDao dao = (VoziloDao) ctx.getAttribute("voziloDao");
		int indId = Integer.parseInt(id);
		return dao.vozilaObjekta(indId);
		}
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vozilo updateVozilo(Vozilo vozilo) {
		VoziloDao dao = (VoziloDao) ctx.getAttribute("voziloDao");
		return dao.updateVozilo(vozilo);
	}
	@PUT
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vozilo brisanje(Vozilo vozilo) {
		VoziloDao dao = (VoziloDao) ctx.getAttribute("voziloDao");
		return dao.brisanje(vozilo);
	}
}
