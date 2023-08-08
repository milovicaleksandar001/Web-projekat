package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.LokacijaDao;
import model.Lokacija;

@Path("/locations")
public class LokacijaService {
	@Context
	ServletContext ctx;
	
	public LokacijaService()
	{
		
	}
	
	@PostConstruct
    public void init() {
        if (ctx.getAttribute("lokacijaDao") == null) {
            ctx.setAttribute("lokacijaDao", new LokacijaDao());
        }
    }
	
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lokacija> getLokacije() {
        LokacijaDao dao = (LokacijaDao) ctx.getAttribute("lokacijaDao");
        return dao.getAll();
    }
	
    @GET
    @Path("/getByAdresa/{adresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Lokacija getLokacijaByAdresa (@PathParam("adresa") String adresa){
    	LokacijaDao dao = (LokacijaDao) ctx.getAttribute("lokacijaDao");
		return dao.getByAdresa(adresa);
	}
}
