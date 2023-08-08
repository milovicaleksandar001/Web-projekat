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

import dao.KomentarDao;
import dao.PorudzbinaDao;
import model.Komentar;
import model.Korisnik;
import model.Porudzbina;
import model.RentACarObjekat;

@Path("/komentari")
public class KomentarService {
	@Context
	ServletContext ctx;
	
	public KomentarService()
	{
		
	}
	
	@PostConstruct
    public void init() {
        if (ctx.getAttribute("komentarDao") == null) {
            ctx.setAttribute("komentarDao", new KomentarDao());
        }
    }
	
	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Komentar> getKomentari() {
		KomentarDao dao = (KomentarDao) ctx.getAttribute("komentarDao");
        return dao.getAll();
    }
	
	@POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Komentar dodajKomentar(Komentar komentar) {
		KomentarDao dao = (KomentarDao) ctx.getAttribute("komentarDao");
		Komentar dodatKomentar = dao.create(komentar);
        return dodatKomentar;
    }
	@POST
    @Path("/parametrima/{idKupca}/{idRaca}/{komentar}/{ocena}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Komentar dodajKomentarParametrima(@PathParam("idKupca") String idKupca,@PathParam("idRaca") String idRaca,@PathParam("komentar") String komentar,@PathParam("ocena") String ocena) {
		KomentarDao dao = (KomentarDao) ctx.getAttribute("komentarDao");
		int intIdKupca = Integer.parseInt(idKupca);
		int intIdRaca = Integer.parseInt(idRaca);
		int intOcena = Integer.parseInt(ocena);
		Komentar komentarDodavanje = new Komentar();
		komentarDodavanje.setOcena(intOcena);
		Korisnik korisnik = new Korisnik();
		korisnik.setId(intIdKupca);
		komentarDodavanje.setKupac(korisnik);
		RentACarObjekat raco = new RentACarObjekat();
		raco.setId(intIdRaca);
		komentarDodavanje.setRentACarObjekat(raco);
		komentarDodavanje.setTekstKomentara(komentar);
		komentarDodavanje.setStatus("CEKANJE");
		Komentar dodatKomentar = dao.create(komentarDodavanje);
        return dodatKomentar;
    }
	
	@GET
	 @Path("/getById/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Komentar getById (@PathParam("id") String id){
		KomentarDao dao = (KomentarDao) ctx.getAttribute("komentarDao");
		int indId = Integer.parseInt(id);
		return dao.getById(indId);
		}
	@GET
	 @Path("/proveriJelNjegovo/{id}/{idKorisnika}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public boolean proveriJelNjegovo (@PathParam("id") String id,@PathParam("idKorisnika") String idKorisnika){
		KomentarDao dao = (KomentarDao) ctx.getAttribute("komentarDao");
		int indId = Integer.parseInt(id);
		int indIdKorisnika = Integer.parseInt(idKorisnika);
		return dao.proveriJelNjegovo(indId,indIdKorisnika);
		}
	@PUT
	@Path("/odobri/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Komentar odobri(@PathParam("id") String id) {
		KomentarDao dao = (KomentarDao) ctx.getAttribute("komentarDao");
		int indId = Integer.parseInt(id);
		return dao.odobri(indId);
	}
	@PUT
	@Path("/obrisi/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Komentar obrisi(@PathParam("id") String id) {
		KomentarDao dao = (KomentarDao) ctx.getAttribute("komentarDao");
		int indId = Integer.parseInt(id);
		return dao.obrisi(indId);
	}
	@GET
	 @Path("/getAllOdobreni")
	 @Produces(MediaType.APPLICATION_JSON)
	 public ArrayList<Komentar> getAllOdoboreni (){
		KomentarDao dao = (KomentarDao) ctx.getAttribute("komentarDao");
		return dao.getAllOdobreniKomentari();
		}
	 @GET
	 @Path("/getAllRacoKomentari/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public ArrayList<Komentar> getAllRacoKomentari (@PathParam("id") String id){
		KomentarDao dao = (KomentarDao) ctx.getAttribute("komentarDao");
		int indId = Integer.parseInt(id);
		return dao.getAllRacoKomentari(indId);
		}
}
