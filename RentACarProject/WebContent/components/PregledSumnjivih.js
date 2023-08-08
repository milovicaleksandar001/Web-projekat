Vue.component("pregled-sumnjivih", {
	data : function() {
		return {
			poslatoKorisnicko:null,
			korisnik:{
				id:null,korisnickoIme:null,lozinka:null,ime:null,prezime:null,pol:null,datum:null,uloga:null,svaIznajmljivanja:null,
				korpa:null,rentACarObjekat:null,bodovi:null,tipKupca:null	
					 },
			sviKorisnici:null,
		}
	},
	template:
	`
<div id="containerPregledKomentara">
<h2>PREGLED SUMNJIVIH KORISNIKA</h2>

<table id="komentariTable">
		    <tr>
		      <th colspan="6">Lista komentara</th>
		    </tr>
		    <tr>
		      <th>Korisnicko ime</th>
		      <th>Ime</th>
		      <th>Prezime</th>
		      <th>Pol</th>
		      <th>Uloga</th>
		      <th>Blokiraj</th>
		    </tr>
		    <tr v-for="k in sviKorisnici">
		      <td>{{k.korisnickoIme}}</td>
		      <td>{{k.ime}}</td>
		      <td>{{k.prezime}}</td>
		      <td>{{k.pol}}</td>
		      <td>{{k.uloga}}</td>
		      <td class="button-cell"><button v-on:click='Blokiraj(k.id)' id="innerButton">Blokiraj</button></td>
		    </tr>
		  </table>

</div>
	
	`
	,
	
	mounted(){
		axios.get('rest/porudzbine/getAllSumnjiviKupci')
			.then(response=>{
				this.sviKorisnici = response.data
			})
				}
			
	,
	methods:{
		Blokiraj : function(id){
			axios.put('rest/users/blokiraj/'+id)
		  .then(response=>{
			  alert("uspesno ste blokirali korisnika");
		  })
			
			
		}
		
	}
	});
	

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/PregledKomentara.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
