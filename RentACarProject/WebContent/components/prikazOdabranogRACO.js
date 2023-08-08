Vue.component("prikaz-odabranog-raco", {
	data : function() {
		return {
			poslatID:null,
			raco:{
				  naziv:null,vozilaUPonudi:null,radnoVreme:null,status:null,lokacija:null,logo:null,ocena:null,komentar:null,menadzer:null	
				 },
			svaVozila:null,sviKomentari:null,
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
  <h2>Podaci o Rent A Car objektu</h2>
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
  		<h3>Vozila</h3>
    	<table id="vozilaTable">
		    <tr>
		      <th colspan="2">Lista vozila</th>
		    </tr>
		    <tr>
		      <th>Marka</th>
		      <th>Model</th>
		    </tr>
		    <tr v-for="v in svaVozila">
		      <td>{{v.marka}}</td>
		      <td>{{v.model}}</td>
		    </tr>
		  </table>
   </div>

	<div id="tableKomentari">
		<h3>Komentari</h3>
    	<table id="komentariTable">
		    <tr>
		      <th colspan="2">Lista komentara</th>
		    </tr>
		    <tr>
		      <th>Redni broj komentara</th>
		      <th>Tekst</th>
		    </tr>
		    <tr v-for="k in sviKomentari">
		      <td>{{k.id}}</td>
		      <td>{{k.tekstKomentara}}</td>
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
				axios.get('rest/vozila/getVozilaObjekta/' + this.poslatID)
					.then(response=>{
						this.svaVozila=response.data
					axios.get('rest/komentari/getAllRacoKomentari/' + this.poslatID)
					.then(response1=>{
						this.sviKomentari=response1.data
						})
					})
			});
				}			
	,
	methods:{

	}
	})
	

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/prikaz_odabranogRACO.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
