Vue.component("prikaz-odabrane-porudzbine", {
    data() {
        return {
			poslatID:null,
			porudzbina:{
				voziloList:null,rentACarObjekat:null,datumIVreme:null,trajanje:null,cena:null,tip:null,vrsta:null,tipGoriva:null,potrosnja:null,brojVrata:null,brojPutnika:null,opis:null,kupac:null,status:null
					 },
			cancelReason: '',
			pom:null,
        }
    },
    template: `
	  <div class="containerShowProfile">
	  <h2>Podaci o porudzbini</h2>
		
		<div class="info">
    	<label>Datum:</label>
   		 <span> {{formatDate(this.porudzbina.datumIVreme)}}</span>
 		 </div>
		
		  <div class="info">
		    <label>Trajanje:</label>
		    <span>{{this.porudzbina.trajanje}}</span>
		  </div>
		
		  <div class="info">
		    <label>Cena:</label>
		    <span>{{this.porudzbina.cena}}</span>
		  </div>
		
		  <div class="info">
		    <label>Status:</label>
		    <span>{{this.porudzbina.status}}</span>
		  </div>

		
		
		  <div>
      <button v-on:click='Odobri()' id="popButtonOdobri">Odobri</button>
      <button v-on:click="Odbij()" id="popButtonOdbij">Odbij</button>
      
      <div class="form-group">
			  <label >Razlog otkazivanja:</label>
			  <input type="text" id="cancelReason" name="cancelReason" v-model="cancelReason" placeholder="Unesite razlog odbijanja ukoliko odbijate">	  
	  </div>
      
      <button v-on:click='Preuzeto()' id="popButton">Promeni status u preuzeto</button>
      <button v-on:click='Vraceno()' id="popButton">Promeni status u vraceno</button>
  		</div>
  		  		
		  </div>
    `,
	
	mounted(){
		this.poslatID = this.$route.params.id;
		this.cancelReason="";
		this.pom=false;
		axios.get('rest/porudzbine/getById/' + this.poslatID)
			.then(response=>{
				this.porudzbina = response.data
			})
				}			
	,
	methods: {
		
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
	    
	  Odobri : function(){
		  if(this.porudzbina.status=="OBRADA"){
		  axios.put('rest/porudzbine/odobri/'+this.poslatID)
		  .then(response=>{
			  axios.get('rest/porudzbine/getById/' + this.poslatID)
				.then(response=>{
				this.porudzbina = response.data
			})
		  })
		  }else{
			  alert("Ovo mozete samo sa porudzbinama u statusu OBRADA");
		  }
	  },
	  Odbij : function(){
		  if(this.porudzbina.status=="OBRADA"){
		  if(this.cancelReason!=""){
		  axios.put('rest/porudzbine/odbij/'+this.poslatID)
		  .then(response=>{
			  axios.get('rest/porudzbine/getById/' + this.poslatID)
				.then(response=>{
				this.porudzbina = response.data
			})
		  })
		  }else{
			  alert("Unesite razlog odbijanja");
		  }
		  }else{
			  alert("Ovo mozete samo sa porudzbinama u statusu OBRADA");
		  }
	  },
	  Preuzeto : function(){
		  if(this.porudzbina.status=="ODOBRENO"){
			  axios.get('rest/porudzbine/daLiJePocelo/'+this.poslatID)
			  .then(response=>{
				  this.pom = response.data
				  if(this.pom==true){
					  axios.put('rest/porudzbine/preuzeto/'+this.poslatID)
		 			 .then(response=>{
						  axios.put('rest/pomoci/iznajmljeno/'+this.poslatID)
						  .then(response=>{
							  axios.get('rest/porudzbine/getById/' + this.poslatID)
								.then(response=>{
								this.porudzbina = response.data
							})
						  })
					  })
				  }else{
					  alert("Nije stigao datum porudzbine");
				  }
			  })
			  
		  }else{
			  alert("Ovo mozete samo sa porudzbinama u statusu ODOBRENO");
		  }
	  },
	  Vraceno : function(){
		  if(this.porudzbina.status=="PREUZETO"){
			  axios.put('rest/porudzbine/vraceno/'+this.poslatID)
		 			 .then(response=>{
						  axios.put('rest/pomoci/vracanje/'+this.poslatID)
						  .then(response=>{
							  axios.get('rest/porudzbine/getById/' + this.poslatID)
								.then(response=>{
								this.porudzbina = response.data
							})
						  })
					  })
		  }else{
			  alert("Ovo mozete samo sa porudzbinama u statusu PREUZETO");
		  }
	  }
	  
	    
		}
		})
		
		
var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/prikazOdabranePorudzbine.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
