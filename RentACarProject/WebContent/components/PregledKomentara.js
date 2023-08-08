Vue.component("pregled-komentara", {
	data : function() {
		return {
			poslatoKorisnicko:null,
			idKorisnika:null,
			komentar:{rentACarObjekat:null,tekstKomentara:null,ocena:null,status:null,kupac:null},
			raco:null,
			idRaco:null,
			jelNjegovo:null,
			sviKomentari:null,
		}
	},
	template:
	`
<div id="containerPregledKomentara">
<h2>PREGLED KOMENTARA</h2>

<table id="komentariTable">
		    <tr>
		      <th colspan="7">Lista komentara</th>
		    </tr>
		    <tr>
		      <th>Rent a car objekat</th>
		      <th>Tekst komentara</th>
		      <th>Ocena</th>
		      <th>Kupac</th>
		      <th>Status</th>
		      <th>Prihvati</th>
		      <th>Odbij</th>
		    </tr>
		    <tr v-for="k in sviKomentari">
		      <td>{{k.rentACarObjekat.naziv}}</td>
		      <td>{{k.tekstKomentara}}</td>
		      <td>{{k.ocena}}</td>
		      <td>{{k.kupac.korisnickoIme}}</td>
		      <td>{{k.status}}</td>
			  <td class="button-cell"><button v-on:click='prihvati(k.id,k.status)' id="innerButtonMHP">Prihvati</button></td>
			  <td class="button-cell"><button v-on:click='obrisi(k.id,k.status)' id="innerButtonMHP">Odbij</button></td>


		    </tr>
		  </table>

</div>
	
	`
	,
	
	mounted(){
		this.poslatoKorisnicko = this.$route.params.korisnicko;
		this.jelNjegovo=false;
		axios.get('rest/komentari')
			.then(response=>{
				this.sviKomentari = response.data
				axios.get('rest/users/getIdByUsername/'+this.poslatoKorisnicko)
					.then(response1=>{
						this.idKorisnika = response1.data
						axios.get('rest/objects/getObjectIdByManagerId/'+this.idKorisnika)
							.then(response2=>{
								this.idRaco = response2.data
							})
					})
			})
				}
			
	,
	methods:{
		prihvati : function(id,status){
			event.preventDefault();
			if(status=="CEKANJE"){
			axios.get('rest/komentari/proveriJelNjegovo/'+id+'/'+this.idRaco)
				.then(response=>{
					this.jelNjegovo=response.data
					if(this.jelNjegovo==true){
						axios.put('rest/komentari/odobri/'+id)
							.then(response2=>{
								alert("uspesno ste odobrili komentar");
								axios.get('rest/komentari')
									.then(response=>{
									this.sviKomentari = response.data
									this.jelNjegovo=false;
									})
							})
					}else{
						alert("Ovo mozete uraditi samo sa vasim objektom");
					}
				})
			}else{
				alert("Ovo mozete uraditi samo sa komentarima u statusu CEKANJE");
			}
		},
		obrisi : function(id,status){
			event.preventDefault();
			if(status=="CEKANJE"){
			axios.get('rest/komentari/proveriJelNjegovo/'+id+'/'+this.idRaco)
				.then(response=>{
					this.jelNjegovo=response.data
					if(this.jelNjegovo==true){
						axios.put('rest/komentari/obrisi/'+id)
							.then(response2=>{
								alert("uspesno ste obrisali komentar");
								axios.get('rest/komentari')
									.then(response=>{
									this.sviKomentari = response.data
									this.jelNjegovo=false;
									})
							})
					}else{
						alert("Ovo mozete uraditi samo sa vasim objektom");
					}
				})
			}else{
				alert("Ovo mozete uraditi samo sa komentarima u statusu CEKANJE");
			}
		}
	}
	});
	

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/PregledKomentara.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
