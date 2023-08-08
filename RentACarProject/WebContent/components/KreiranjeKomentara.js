
Vue.component("kreiranje-komentara", {
	data : function() {
		return {
			poslatoKorisnicko:null,
			idRaca:null,
			komentar:{tekstKomentara:null,Ocena:null},
			raco:null,
			id:null,
			sviObjekti:null,
		}
	},
	template:
	`
	 <div class="containerCreateComment">

	<h2>KOMENTARI</h2>
	
	<div class="form-group">
		      <label for="raco">Rent a car objekat:</label>
		      <select id="raco" name="raco" v-model="idRaca" >
				<option v-for="o in sviObjekti" :value="o.id">{{o.naziv}}</option>
		      </select>
	</div>
	
		   <div class="form-group">
            <label for="opis">Komentar:</label>
            <textarea id="komentar" name="komentar" rows="4" cols="50" v-model="komentar.tekstKomentara"> </textarea>  
          </div>

		   <div class="form-group">
            <label>Ocena:</label>
            <input type="number" id="cena" v-model=komentar.Ocena> 
          </div>
          
		<div>
			<button v-on:click='kreirajKomentar()' id="komButton">Kreiraj recenziju</button>
			
		</div>
	
	</div>
	`
	,
	
	mounted(){ 
		this.poslatoKorisnicko = this.$route.params.korisnicko;
		this.komentar.Ocena=0;
		axios.get('rest/users/getIdByUsername/'+this.poslatoKorisnicko)
			.then(response =>{
				this.id = response.data
				axios.get('rest/pomoci/objektiZaKomentarisanje/'+this.id)
					.then(response=>{
					this.sviObjekti = response.data
				})
			})
			
			}
	,
	methods:{
		kreirajKomentar : function(){
					event.preventDefault();
					if(this.komentar.Ocena<1 || this.komentar.Ocena>5){
						alert("ocena mora biti izmedju 1 i 5");
					}else{
					if(this.komentar.tekstKomentara==null || this.komentar.Ocena==0 || this.idRaca==null){
						alert("unesite sva polja");
					}else{
						axios.post('rest/komentari/parametrima/'+this.id+'/'+this.idRaca+'/'+this.komentar.tekstKomentara+'/'+this.komentar.Ocena)
							.then(response=>{
								axios.put('rest/pomoci/ugasitiKomentarisanje/'+this.idRaca+'/'+this.id)
									.then(response=>{
										axios.put('rest/objects/menjanjeOcene/'+this.komentar.Ocena+'/'+this.idRaca)
											.then(response=>{	
											alert("Uspesno kreirana recenzija");
											router.push(`/PrikazProfila/`+this.poslatoKorisnicko)
											})
									})
							})
					}
					}
		}
	}
	});
	

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/KreiranjeKomentara.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
