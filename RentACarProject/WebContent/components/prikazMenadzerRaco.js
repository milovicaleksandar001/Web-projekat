Vue.component("prikaz-menadzer-raco", {
	data : function() {
		return {
			poslatID:null,
			raco:{
				  naziv:null,vozilaUPonudi:null,radnoVreme:null,status:null,lokacija:null,logo:null,ocena:null,komentar:null,menadzer:null	
				 },
			svaVozila:null,
			svePorudzbine:null
				 		}
	},
	template:
	`
	<html>
<head>
  <title>Rent A Car Information</title>
</head>
<body>
  <div class="containerShowRaco">
  <h2>Podaci o Rent A Car objektu MENADZERA</h2>
    <div class="info">
      <label>Naziv:</label>
      <span> {{this.raco.naziv}}</span>
    </div>

    <div class="info">
      <label>Radno vreme:</label>
      <span>{{this.raco.radnoVreme}}</span>
    </div>

    <div class="info">
      <label>Status:</label>
      <span>{{this.raco.status}}</span>
    </div>

    <div class="info">
      <label>Lokacija:</label>
      <span>{{this.raco.lokacija.adresa}}</span>
      <br><br>
    </div>
    
	<div class="info">
      <label>Logo:</label>
      <span><img :src="this.raco.logo" alt="Logo" class="logo-image"></span>
      <br><br>
    </div>
    
	<div class="info">
      <label>Ocena:</label>
      <span>{{this.raco.ocena}}</span>
      <br><br>
    </div>
    

  <div id="tableVozila">
  		<h3>Porudzbine i kupci</h3>
    	<table id="vozilaTable">
		    <tr>
		      <th colspan="5">Lista porudzbina</th>
		    </tr>
		    <tr>
		      <th>Datum porudzbine</th>
		      <th>Trajanje</th>
		      <th>Cena</th>
		      <th>Status</th>
		      <th>Kupac</th>
		    </tr>
		    <tr v-for="p in svePorudzbine">
				  <td>{{ formatDate(p.datumIVreme) }}</td>
				  <td>{{p.trajanje}}</td>
				  <td>{{p.cena}}</td>
				  <td>{{p.status}}</td>	     
				  <td>{{p.kupac.korisnickoIme}}</td> 
		    </tr>
		  </table>
   </div>

  </div>
  
</body>
</html>
	
	`
	,
	
	mounted(){
		this.poslatID = this.$route.params.id;
		axios.get('rest/objects/getById/' + this.poslatID)
			.then(response=>{
				this.raco = response.data
				axios.get('rest/pomoci/porudzbineObjekta/' + this.poslatID)
					.then(response=>{
						this.svePorudzbine=response.data
					})
			})
				}			
	,
	methods:{
		 formatDate(dateString) {
	    if (!dateString) {
	      return '';
	    }
	
	    const { year, monthValue, dayOfMonth } = dateString;
	    const date = new Date(year, monthValue - 1, dayOfMonth);
	    const formattedDate = `${date.getDate().toString().padStart(2, '0')}/${(date.getMonth() + 1)
	      .toString()
	      .padStart(2, '0')}/${date.getFullYear()}`;
	    return formattedDate;
	    },
		kreirajPorudzbinu: function(){
			router.push(`/CreatePorudzbina/`+this.poslatoKorisnicko);
		},
	}
	})
	

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/prikaz_odabranogRACO.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
