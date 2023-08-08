Vue.component("prikaz-profila1", {
	data : function() {
		return {
			poslatoKorisnicko:null,
			korisnik:{
				id:null,korisnickoIme:null,lozinka:null,ime:null,prezime:null,pol:null,datum:null,uloga:null,svaIznajmljivanja:null,
				korpa:null,rentACarObjekat:null,bodovi:null,tipKupca:null	
					 },
			
				cena:null,raco:null,datum:null,
			sortColumn: "",
      		sortOrder: "asc",  
		}
	},
	template:
	`
<html>
	<head>
	  <title>User Information</title>
	</head>
	<body>
	  <div class="containerShowProfile">
	  <h2>Tvoji podaci</h2>
		<div class="info">
		  <label>Korisnicko ime:</label>
		  <span> {{this.korisnik.korisnickoIme}}</span>
		</div>
	
		<div class="info">
		  <label>Ime:</label>
		  <span>{{this.korisnik.ime}}</span>
		</div>
	
		<div class="info">
		  <label>Prezime:</label>
		  <span>{{this.korisnik.prezime}}</span>
		</div>
	
		<div class="info">
		  <label>Pol:</label>
		  <span>{{this.korisnik.pol}}</span>
		</div>
	
		<div class="info">
		  <label>Datum rodjenja:</label>
		  <span>{{this.korisnik.datum}}</span>
		  <br><br>
		</div>
		
		<div>
			<button v-on:click='izmeni()' id="ppButton">Izmeni profil</button>
			<button v-on:click='odjaviSe()' id="ppButton">Odjavi se</button>
		</div>
		
		  </div>
		  
	</body>
	</html>
			
	`
	,
	  mounted() {
	    this.poslatoKorisnicko = this.$route.params.korisnicko;
	
	    axios.get("rest/users/getByUsername/" + this.poslatoKorisnicko)
	      .then(response => {
	        this.korisnik = response.data;	     
	      });
	  }		
	,
	methods:{
		odjaviSe : function() {
			router.push(`/`);
		},
		izmeni : function(){
			router.push('/IzmenaKorisnika/'+this.korisnik.id)
		}
	}
	})
	

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/prikaz_profila.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
