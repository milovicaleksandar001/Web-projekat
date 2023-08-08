Vue.component("manager-home-page", {
	data : function() {
		return {
			pomKorisnik:null,
			sviObjekti:null,
			poslatoKorisnicko:null,
			pomVozilo:null,
			korisnik:{
				id:null,korisnickoIme:null,lozinka:null,ime:null,prezime:null,pol:null,datum:null,uloga:null,svaIznajmljivanja:null,
				korpa:null,rentACarObjekat:null,bodovi:null,tipKupca:null	
					 },
			menadzerRacoId:null,
		}
	},
	template:
	`
	<html>
<head>
  <title>Manager Home Page</title>
</head>
<body>
  <div class="containerMHP">
  <h2>MANAGER Home Page</h2>    
    <div>
    	<button v-on:click='dodajVozilo()' id="mhpButton">Dodaj vozila u Rent a car objekat</button>
    	<button v-on:click='prikazRaco()' id="mhpButton">Prikaz informacija o vasem RACO-u</button>
    	<button v-on:click='prikaziIznajmljivanja()' id="mhpButton">Pregled svih iznajmljivanja na vasem RACO</button>
    	<button v-on:click='prikaziPodatke()' id="mhpButton">Prikazi podatke o ovom profilu</button>
    	<button v-on:click='prikaziKomentare()' id="mhpButton">Pregled komentara</button>
    	<button v-on:click='odjaviSe()' id="mhpButton">Odjavi se</button>
    </div>
  </div>
  
  		    <div id="carTable">
		    
		    <table id="vozilaTable">
		    <tr>
		      <th colspan="6">Lista vozila u vasem rent a car objektu</th>
		    </tr>
		    <tr>
		      <th>Marka</th>
		      <th>Model</th>
		      <th>Cena</th>
		      <th>Slika</th>
		      <th>Izmeni</th>
		      <th>Obrisi</th>
		    </tr>
		    <tr v-for="o in sviObjekti">
		      <td>{{o.marka}}</td>
		      <td>{{o.model}}</td>
		      <td>{{o.cena}}</td>
		      <td><img :src="o.slika" alt="Slika" id="slika-image"></td>
		      <td><button v-on:click='izmeni(o.id)' id="innerButtonMHP"> Izmeni </button></td>
		      <td><button v-on:click='obrisi(o.id)' id="innerButtonMHP">  Obrisi </button></td>
		    </tr>
		  </table>
		    
		    </div>
</body>
</html>
	
	`
	,
	
	mounted(){
		this.poslatoKorisnicko = this.$route.params.korisnicko;
		
		axios.get('rest/users/getByUsername/'+this.poslatoKorisnicko)
			.then(response=>{
					this.pomKorisnik = response.data
				
				
		axios.get('rest/vozila/getByManagerId/' + this.pomKorisnik.id)
			.then(response=>{
				this.sviObjekti = response.data
			
			});
					axios.get('rest/objects/getObjectIdByManagerId/' + this.pomKorisnik.id)
			.then(response=>{
				this.menadzerRacoId = response.data
				});
				
				});
				}
			
	,
	methods:{
		odjaviSe : function() {
			router.push(`/`);
		},
		dodajVozilo : function(){
			event.preventDefault();
			axios.get('rest/users/menagerGlavni/'+this.poslatoKorisnicko)
				.then(response=>{
				if(response.data==true){
			router.push('/DodajVozilo/'+this.poslatoKorisnicko)
			}else{
				alert("Niste rasporedjeni ni na jedan objekat")
			}
			})
		},
		obrisi : function(id){
			event.preventDefault();
			axios.get('rest/vozila/getById/'+id)
			.then(response=>{
					this.pomVozilo= response.data
					alert("Uspesno obrisan automobil")
			axios.put('rest/vozila/delete',this.pomVozilo)
			.then(response=>{
							axios.get('rest/vozila/getByManagerId/' + this.pomKorisnik.id)
					.then(response=>{
						this.sviObjekti = response.data
					});
					});
		
		});
		

		},
		izmeni : function(id){
			event.preventDefault();
			router.push('/IzmenaAutomobila/'+id+'/'+this.poslatoKorisnicko)
		},
		prikaziPodatke : function(){
			router.push(`/PrikazProfila1/`+this.poslatoKorisnicko);
		},
		prikaziIznajmljivanja : function(){
			router.push(`/PrikazIznajmljivanja/`+this.poslatoKorisnicko);
		},
		prikaziKomentare : function(){
			router.push(`/PregledKomentara/`+this.poslatoKorisnicko);
		},
		prikazRaco : function(id){
			router.push(`/PrikazMenadzerRaco/`+ this.menadzerRacoId);
		}
		
		
	}
	});
	

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/managerHomePage.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
