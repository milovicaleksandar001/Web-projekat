Vue.component("register", {
    data() {
        return {
			korisnik:{
				korisnickoIme:null,lozinka:null,ime:null,prezime:null,pol:null,datum:null,uloga:null,svaIznajmljivanja:null,
				korpa:null,rentACarObjekat:null,bodovi:null,tipKupca:null	
					 },
			pomSifra:null,
			korpa:{korisnik:null,cena:null,pocetanDatum:null,krajnjiDatum:null}
            
        }
    },
    template: `
		<div class="containerRegister">
		  <h2>Registracija</h2>
		  <form>
		    <div class="form-group">
		      <label for="username">Korisnicko ime:</label>
		      <input type="text" id="username" name="username" v-model="korisnik.korisnickoIme" required>
		    </div>
		    <div class="form-group">
		      <label for="password">Lozinka:</label>
		      <input type="password" id="password" name="password" v-model="korisnik.lozinka" required>
		    </div>
		    <div class="form-group">
		      <label>Potvrdite lozinku:</label>
		      <input type="password" id="confirm-password" v-model="pomSifra" required>
		    </div>
		    <div class="form-group">
		      <label for="name">Ime:</label>
		      <input type="text" id="name" name="name" v-model="korisnik.ime" required>
		    </div>
		    <div class="form-group">
		      <label for="surname">Prezime:</label>
		      <input type="text" id="surname" name="surname" v-model="korisnik.prezime" required>
		    </div>
		    <div class="form-group">
		      <label for="gender">Pol:</label>
		      <select id="gender" name="gender" v-model="korisnik.pol" required>
		        <option value="MUSKO">Male</option>
		        <option value="ZENSKO">Female</option>
		      </select>
		    </div>
		    <div class="form-group">
		      <label for="dob">Datum rodjenja:</label>
		      <input type="date" id="dob" name="dob" v-model="korisnik.datum" required>
		    </div>
		    <div class="form-group">
		      <input type="submit" value="Registruj se" id="register-btn" v-on:click="registerKorisnik()">
		    </div>
		  </form>
		</div>
    `,
    mounted() {
        
    },
    methods: {
        registerKorisnik: function()
        {
			if(this.korisnik.korisnickoIme ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.korisnik.lozinka ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.pomSifra == null)
			{
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.korisnik.ime ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.korisnik.prezime ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.korisnik.pol ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.korisnik.datum ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
    		if (this.korisnik.lozinka !== this.pomSifra) {
       			alert("Lozinke se ne poklapaju");
       			this.korisnik.lozinka = null;
       			this.pomSifra = null;
				event.preventDefault();
       			return;
   			 }
			event.preventDefault();
			
			
			axios.get('rest/users/register/' + this.korisnik.korisnickoIme )
				.then(response1=>{
					if(response1.data == true)
					{
					this.korisnik.uloga="KUPAC";
					
					axios.post('rest/users',this.korisnik).
						then(response => {
						this.korpa.korisnik = response.data;
						this.korpa.cena = 0;
						this.korpa.pocetanDatum = null; //???
						this.korpa.krajnjiDatum = null; //???
						axios.post('rest/korpe',this.korpa)
						.then(response=>{
						router.push(`/Login`);	
						})
						});
					}
					else
					{
					alert("Vec postoji korisnik sa tim korisnickim imenom")
					}
				})	

		}
        
        
        
    }
});

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/register.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
