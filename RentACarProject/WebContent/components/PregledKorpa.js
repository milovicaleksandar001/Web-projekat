Vue.component("pregled-korpa", {
    data() {
        return {
			sviObjekti:null,
			porudzbina:{
				voziloList:null,rentACarObjekat:null,datumIVreme:null,trajanje:null,cena:null,kupac:null,status:null
					 },
					 poslatoKorisnicko:null,
					 id:null,
					 korpaId:null,
					 brojDana:null,
					 pocetniDatum:null,
					 idPorudzbine:null,
					 cenaZaSlanje:null,
			korpa:{id:null,korisnik:null,cena:null,pocetanDatum:null,krajnjiDatum:null}				    
        }
    },
    template: `
    	
		<div class="containerCreatePorudzbina">
		   <h2>
    		KORPA 
 		   </h2>
		  
		   <div id="carTableKorpa">
		    
		    <table id="vozilaTableKorpa">
		    <tr>
		      <th colspan="5">Lista rent-a-car objekata</th>
		    </tr>
		    <tr>
		      <th>Marka</th>
		      <th>Model</th>
		      <th>Cena</th>
		      <th>Slika</th>
		      <th>Ukloni</th>
		    </tr>
		    <tr v-for="o in sviObjekti">
		      <td>{{o.marka}}</td>
		      <td>{{o.model}}</td>
		      <td>{{o.cena}}</td>
		      <td><img :src="o.slika" alt="Slika" id="slika-image-k"></td>
		      <td><button v-on:click='odaberi(o.id)' id="innerButtonK"> - </button></td>
		      
		    </tr>
		  </table>
		  
		  <div id="sumCena">
		  <label>Ukupna cena: </label>
		  <label>{{ ukupnaCena }}</label> 
		 
		  </div>
		  
		  <button id="buttonKorpa" v-on:click='iznajmi()'>Iznajmi</button>
		  
		</div>
    `,
		computed: {
		  ukupnaCena() {
		    let sum = 0;
		    for (let i = 0; i < this.sviObjekti.length; i++) {
		      sum += this.sviObjekti[i].cena;
		    }
		    return sum * this.brojDana;
		  },
		},
   mounted() {
            this.poslatoKorisnicko = this.$route.params.korisnicko;
                axios.get('rest/users/getIdByUsername/'+this.poslatoKorisnicko)
                .then(response1=>{
                    this.id = response1.data
                        axios.get('rest/korpe/getIdByUserId/'+this.id)
                        .then(response2=>{
                            this.korpaId = response2.data
                            
					axios.get('rest/korpe/getNumberOfDays/'+this.korpaId) 
					.then(response3=>{
						this.brojDana = response3.data
						})                          
                    axios.get('rest/veze/getAllVozilaFromCart/'+this.korpaId) 
                    .then(response=>{
                        this.sviObjekti = response.data
                        })
                    })
                })

    },
    methods: {
		odaberi:function(idVozila){
			axios.put('rest/veze/obrisiJedan/'+this.korpaId+'/'+idVozila)
			.then(response=>{
				axios.get('rest/veze/getAllVozilaFromCart/'+this.korpaId) 
                    .then(response=>{
                        this.sviObjekti = response.data
                        })
			})
		},
		iznajmi:function(){
			event.preventDefault();
			if(this.sviObjekti.length>0){
				axios.get('rest/korpe/datumPocetka/'+this.korpaId)
					.then(response=>{
						this.pocetniDatum = response.data
						axios.get('rest/porudzbine/dodavanje/'+this.id+'/'+this.pocetniDatum+'/'+this.ukupnaCena+'/'+this.brojDana)
						.then(response1=>{
							this.idPorudzbine = response1.data
							axios.post('rest/pomoci/dodavanje/'+this.korpaId+'/'+this.idPorudzbine)
							.then(response2=>{
								axios.put('rest/veze/update/'+this.korpaId)
								.then(response=>{
									this.cenaZaSlanje=(this.ukupnaCena/1000)*133
									axios.put('rest/users/bodovi/'+this.id+'/'+this.cenaZaSlanje)
									.then(response3=>{	
										alert("uspesno kreirana porudzbina");
										router.push(`/PrikazProfila/`+this.poslatoKorisnicko);						
									})
								})
							})
						})
					})
				}else{
					alert("odaberite vozila za rezervisanje")
				}
		}
        }

});

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/PregledKorpa.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);