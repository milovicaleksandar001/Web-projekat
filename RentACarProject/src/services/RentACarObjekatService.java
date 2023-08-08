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
import dao.RentACarObjekatDao;
import model.RentACarObjekat;

@Path("/objects")
public class RentACarObjekatService {

	@Context
	ServletContext ctx;
	
	public RentACarObjekatService() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("rentACarObjekatDao") == null) {
			ctx.setAttribute("rentACarObjekatDao", new RentACarObjekatDao());
		}
	}
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<RentACarObjekat> prikazSvihObjekata() {
		RentACarObjekatDao dao = (RentACarObjekatDao) ctx.getAttribute("rentACarObjekatDao");
		return dao.prikazSvih();
	}
	@POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RentACarObjekat registerRACO(RentACarObjekat rentACarObjekat) {
		RentACarObjekatDao dao = (RentACarObjekatDao) ctx.getAttribute("rentACarObjekatDao");
		RentACarObjekat registeredRACO = dao.register(rentACarObjekat);
        return registeredRACO;
    }
	 @GET
	 @Path("/getById/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public RentACarObjekat getById (@PathParam("id") String id){
		RentACarObjekatDao dao = (RentACarObjekatDao) ctx.getAttribute("rentACarObjekatDao");
		int indId = Integer.parseInt(id);
		return dao.getById(indId);
		}
	@GET
	@Path("/sortPrikaz")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<RentACarObjekat> prikazSvihObjekataSort() {
		RentACarObjekatDao dao = (RentACarObjekatDao) ctx.getAttribute("rentACarObjekatDao");
		return dao.prikazSvihSort();
	}
	@PUT
	@Path("/menjanjeOcene/{ocena}/{idRaca}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean menjanjeOcene(@PathParam("ocena") String ocena,@PathParam("idRaca") String idRaca) {
		RentACarObjekatDao dao = (RentACarObjekatDao) ctx.getAttribute("rentACarObjekatDao");
		int intId = Integer.parseInt(idRaca);
		double doubleOcena = Double.parseDouble(ocena);
		return dao.dodajOcenu(doubleOcena,intId);
	}
	 @GET
	 @Path("/getObjectIdByManagerId/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public int getObjectIdByManagerId (@PathParam("id") String id){
		RentACarObjekatDao dao = (RentACarObjekatDao) ctx.getAttribute("rentACarObjekatDao");
		int indId = Integer.parseInt(id);
		return dao.getObjectIdByManagerId(indId);
		}
}