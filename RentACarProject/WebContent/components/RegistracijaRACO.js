Vue.component("register-RACO", {
    data() {
        return {
			poslatoKorisnicko:null,
			sveLokacije: null,
			sviMenadzeri: null,
			lokacijaa:null,
			menadzerr:null,
			dugme:true,
			raco:{
				naziv:null,vozilaUPonudi:null,radnoVreme:null,status:null,lokacija:null,logo:null,menadzer:null	
				 }
        }
    },
    template: `
		<div class="containerRegisterRACO">
		  <h2>Registracija Rent A Car Objekta</h2>
		  <form>
		    <div class="form-group">
		      <label for="naziv">Naziv:</label>
		      <input type="text" id="naziv" name="naziv" v-model="raco.naziv" required>
		    </div>
		    <div class="form-group">
		      <label for="lokacija">Lokacija:</label>
		      <select id="lokacija" name="lokacija" v-model="raco.lokacija" required>
		        <option v-for="loc in sveLokacije" :value="loc.adresa">{{loc.adresa}}</option>

		      </select>
		    </div>
		    
			<div class="form-group">
			  <label for="radnoVreme">Radno vreme:</label>
			  <input type="text" id="radnoVreme" name="radnoVreme" v-model="raco.radnoVreme" required placeholder="HH:MM - HH:MM">	  
			</div>
				    
		    <div class="form-group">
		      <label for="logo">Logo:</label>
		      <input type="url" id="logo" name="logo" v-model="raco.logo" required placeholder="input url of photo">
		    </div>
		    <div class="form-group">
		      <label for="menadzer">Menadzer:</label>
		       <select id="menadzer" name="menadzer" v-model="raco.menadzer" required>
		        <option v-for="men in sviMenadzeri" :value="men.korisnickoIme">{{men.korisnickoIme}}</option>
		      </select>
		      <button :disabled="dugme" id="add-button" type="button" v-on:click='kreirajMenadzera()'>+</button>
		    </div>
		    <div class="form-group">
		      <input type="submit" value="Kreiraj" id="register-btn" v-on:click="registerRACO()">
		    </div>
		  </form>
		</div>
    `,
    mounted() {
		this.poslatoKorisnicko = this.$route.params.korisnicko;
        axios.get('rest/users/managers/')
			.then(response=>{
				this.sviMenadzeri = response.data
				if(this.sviMenadzeri.length===0){
				this.dugme=false;
				}
			})
		axios.get('rest/locations')
		.then(response=>{
				this.sveLokacije = response.data
			})
    },
    methods: {		
        registerRACO: function()
        {
			if(this.raco.naziv ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.raco.lokacija ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.raco.radnoVreme == null)
			{
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.raco.logo ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			event.preventDefault();
			axios.get('rest/locations/getByAdresa/'+ this.raco.lokacija)
				.then(response =>{
					this.lokacijaa = response.data
			
			axios.get('rest/users/getByUsername/'+ this.raco.menadzer)
				.then(response1 =>{
					this.menadzerr = response1.data
				this.raco.lokacija = this.lokacijaa;
				this.raco.menadzer = this.menadzerr;
				this.raco.status = "RADI";	
			axios.post('rest/objects',this.raco).
				then(response => {
				alert("Uspesno napravljen novi objekat")
				router.push(`/AdminHomePage/`+this.poslatoKorisnicko);	
				});
					
			})	
			})

		},
		kreirajMenadzera : function(){
			router.push('/RegistracijaMenadzera1/'+this.poslatoKorisnicko)
		}
        
        
        
    }
});

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/registerRACO.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
