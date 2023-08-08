Vue.component("create-poruzbina", {
    data() {
        return {
			sviObjekti:null,
			porudzbina:{
				voziloList:null,rentACarObjekat:null,datumIVreme:null,trajanje:null,cena:null,tip:null,vrsta:null,tipGoriva:null,potrosnja:null,brojVrata:null,brojPutnika:null,opis:null,kupac:null,status:null
					 },
			pocetniDatum:null,
			krajnjiDatum:null,
			poslatoKorisnicko:null,
			veza:{Korpa:null,Vozilo:null},
			korisnik:{id:null,korisnickoIme:null,lozinka:null,ime:null,prezime:null,pol:null,datum:null,uloga:null},
			korpa:{id:null,korisnik:null,cena:null,pocetanDatum:null,krajnjiDatum:null}       
        }
    },
    template: `
		<div class="containerCreatePorudzbina">
		   <h2>
    		Kreiranje porudzbine <button id="korpaButton" v-on:click='openKorpa()'><i class="fas fa-shopping-cart"></i></button>
 		   </h2>

		  
		    <div class="form-group">
		      <label for="datumIVreme">Pocetni datum:</label>
		      <input type="date" id="datumIVreme" name="datumIVreme" v-model="pocetniDatum">
		    </div>
		    <div class="form-group">
		      <label for="datumIVreme">Krajnji datum:</label>
		      <input type="date" id="datumIVreme" name="datumIVreme" v-model="krajnjiDatum">
		    </div>
		    		    <div class="form-group">
		      <button id="buttonCP"  v-on:click='pretrazi()'>Pretrazi vozila</button>
		    </div>
		  
		   <div id="freeCarTable">
		    
		    <table id="freeVozilaTable">
		    <tr>
		      <th colspan="12">Lista slobodnih vozila</th>
		    </tr>
		    <tr>
		      <th>Marka</th>
		      <th>Model</th>
		      <th>Cena</th>
		      <th>Tip</th>
		      <th>Vrsta</th>
		      <th>Tip goriva</th>
		      <th>Potrosnja</th>
		      <th>Broj vrata</th>
		      <th>Broj putnika</th>
		      <th>Opis</th>
		      <th>Slika</th>
		      <th>Odaberi</th>
		    </tr>
		    <tr v-for="o in sviObjekti">
		      <td>{{o.marka}}</td>
		      <td>{{o.model}}</td>
		      <td>{{o.cena}}</td>
		      <td>{{o.tip}}</td>
		      <td>{{o.vrsta}}</td>
		      <td>{{o.tipGoriva}}</td>
		      <td>{{o.potrosnja}}</td>
		      <td>{{o.brojVrata}}</td>
		      <td>{{o.brojPutnika}}</td>
		      <td>{{o.opis}}</td>
		      <td><img :src="o.slika" alt="Slika" id="slika-image-cp"></td>
		      <td><button v-on:click='odaberi(o.id)' id="innerButtonCP"> Odaberi </button></td>
		      
		    </tr>
		  </table>
		  
		</div>
		</div>
    `,
    mounted() {
		this.poslatoKorisnicko = this.$route.params.korisnicko;
        axios.get('rest/users/getByUsername/'+this.poslatoKorisnicko)
        	.then(response=>{
				this.korisnik = response.data
				axios.get('rest/korpe/getIdByUserId/'+this.korisnik.id)
					.then(response1=>{
						this.veza.Korpa = response1.data
						axios.get('rest/korpe/getById/'+this.veza.Korpa)
							.then(response2=>{
								this.korpa = response2.data
							})
					})		
		})
    },
    methods: {
        openKorpa : function() {
			router.push(`/PregledKorpa/`+this.poslatoKorisnicko);
		},
		
		pretrazi: function() {
			event.preventDefault();
			axios.get('rest/pomoci/nadjiVozila/'+this.pocetniDatum+'/'+this.krajnjiDatum)
			.then(response=>{
				this.sviObjekti = response.data
				axios.put('rest/veze/update/'+this.veza.Korpa)
					.then(response=>{
						
					})
				
			})
		
		},
		odaberi: function(id) {
		  event.preventDefault();
		  this.veza.Vozilo = id;
		
		  const index = this.sviObjekti.findIndex((o) => o.id === id);
		  if (index > -1) {
		    this.sviObjekti.splice(index, 1);
		  }
		
		  axios.post('rest/veze/dodavanje/' + this.veza.Korpa + '/' + this.veza.Vozilo)
		    .then(response => {
		      this.korpa.pocetanDatum = this.pocetniDatum;
		      this.korpa.krajnjiDatum = this.krajnjiDatum;
		      axios.put('rest/korpe/update/' + this.korpa.id + '/' + this.pocetniDatum + '/' + this.krajnjiDatum)
		        .then(response1 => {
		          alert("Uspesno ste dodali vozilo u korpu");
		        });
		    });
		}
		
		}
        
});

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/CreatePorudzbina.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);