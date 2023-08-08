Vue.component("pregled-komentara1", {
	data : function() {
		return {
			poslatoKorisnicko:null,
			komentar:{rentACarObjekat:null,tekstKomentara:null,ocena:null,status:null,kupac:null},
			raco:null,
			sviKomentari:null,
		}
	},
	template:
	`
<div id="containerPregledKomentara">
<h2>PREGLED KOMENTARA</h2>

<table id="komentariTable">
		    <tr>
		      <th colspan="5">Lista komentara</th>
		    </tr>
		    <tr>
		      <th>Rent a car objekat</th>
		      <th>Tekst komentara</th>
		      <th>Ocena</th>
		      <th>Kupac</th>
		      <th>Status</th>
		    </tr>
		    <tr v-for="k in sviKomentari">
		      <td>{{k.rentACarObjekat.naziv}}</td>
		      <td>{{k.tekstKomentara}}</td>
		      <td>{{k.ocena}}</td>
		      <td>{{k.kupac.korisnickoIme}}</td>
		      <td>{{k.status}}</td>
		    </tr>
		  </table>

</div>
	
	`
	,
	
	mounted(){
		axios.get('rest/komentari')
			.then(response=>{
				this.sviKomentari = response.data
			})
				}
			
	,
	methods:{
		
	}
	});
	

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/PregledKomentara.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
