Vue.component("izmena-automobila", {
    data() {
        return {
			id: null,
			pomKorisnicko:null,
			vozilo:{
					marka:null, model:null, cena:null, tip:null, vrsta:null, tipGoriva: null, potrosnja:null, brojVrata:null, brojPutnika: null, slika:null, opis:null, objekatKomPripada:null
					 }	   
			
        }
    },
    template: `
        <div class="containerEditAutomobil">
            <h2>Izmena vozila</h2>
		  <form>
		    <div class="form-group">
		      <label for="marka">Marka:</label>
		      <input type="text" id="marka" name="marka" v-model="vozilo.marka" required>
		    </div>
		    <div class="form-group">
		      <label for="model">Model:</label>
		      <input type="text" id="model" name="model" v-model="vozilo.model" required>
		    </div>
		    <div class="form-group">
		      <label>Cena:</label>
		      <input type="number" id="cena" v-model="vozilo.cena" required>
		    </div>
		    <div class="form-group">
		      <label for="tip">Tip:</label>
		      <input type="text" id="tip" name="tip" v-model="vozilo.tip" required>
		    </div>
		    <div class="form-group">
		      <label for="vrsta">Vrsta menjaca:</label>
		      <select id="vrsta" name="vrsta" v-model="vozilo.vrsta" required>
			  	<option value="MANUELNI">Manuelni</option>
		        <option value="AUTOMATIK">Automatik</option>
			  </select>
		    </div>
		    <div class="form-group">
		      <label for="tipGoriva">Tip goriva:</label>	      
		      <select id="tipGoriva" name="tipGoriva" v-model="vozilo.tipGoriva" required>
			  	<option value="DIZEL">Dizel</option>
		        <option value="BENZIN">Benzin</option>
		        <option value="HIBRID">Hibrid</option>
		        <option value="ELEKTRICNI">Elektricni</option>
			  </select>
		    </div>
		    <div class="form-group">
		      <label for="potrosnja">Potrosnja:</label>
		      <input type="number" id="potrosnja" name="potrosnja" v-model="vozilo.potrosnja" required>
		    </div>
		    <div class="form-group">
		      <label for="brojVrata">Broj vrata:</label>
		      <input type="number" id="brojVrata" name="brojVrata" v-model="vozilo.brojVrata" required>
		    </div>
		    <div class="form-group">
		      <label for="brojPutnika">Broj putnika:</label>
		      <input type="number" id="brojPutnika" name="brojPutnika" v-model="vozilo.brojPutnika" required>
		    </div>
		    <div class="form-group">
		      <label for="slika">Slika:</label>
		      <input type="url" id="slika" name="slika" v-model="vozilo.slika" required>
		    </div>
		    <div class="form-group">
		      <label for="opis">Opis:</label>
		      <textarea id="opis" name="opis" v-model="vozilo.opis" rows="4" cols="50"> </textarea>
		    </div>
		    
		    <div class="form-group">
		      <input type="submit" value="Izmeni vozilo" id="register-btn" v-on:click="izmeniVozilo()">
		    </div>
        </div>
    `,
	mounted(){	
			this.id = this.$route.params.id;
			this.pomKorisnicko = this.$route.params.pomKorisnicko
			axios.get('rest/vozila/getById/' + this.id) 
			.then(response=>{
				this.vozilo = response.data
			})
	     	 }

	 ,
    methods: {
			izmeniVozilo : function(){
			if(this.vozilo.marka == null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.vozilo.model == null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.vozilo.cena == null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.vozilo.tip == null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.vozilo.vrsta == null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.vozilo.tipGoriva == null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.vozilo.potrosnja == null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.vozilo.brojVrata == null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.vozilo.brojPutnika == null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.vozilo.slika == null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.vozilo.opis == null){
				this.vozilo.opis = "nema";
			}
			
			event.preventDefault();
							axios.put('rest/vozila/update/',this.vozilo).
					then(response => {
					router.push(`/ManagerHomePage/`+ this.pomKorisnicko);	
					});
		}
        
        
        
    }
});


var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/IzmenaAutomobila.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);