Vue.component("prikaz-profila", {
	data : function() {
		return {
			poslatoKorisnicko:null,
			korisnik:{
				id:null,korisnickoIme:null,lozinka:null,ime:null,prezime:null,pol:null,datum:null,uloga:null,svaIznajmljivanja:null,
				korpa:null,rentACarObjekat:null,bodovi:null,tipKupca:null	
					 },
			
				cenaOd:0,
				raco:0,
				datumOd:null,
				datumDo:null,
				cenaDo:0,
				porudzbina:null,
			svePorudzbine:null,
			sviObjekti:null,
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
			<button v-on:click='kreirajPorudzbinu()' id="ppButton">Kreiraj porudzbinu</button>
			<button v-on:click='izmeni()' id="ppButton">Izmeni profil</button>
			<button v-on:click='oceni()' id="ppButton">Ostavite recenziju</button>
			<button v-on:click='prikaziKomentare()' id="ppButton">Pregled komentara</button>
			<button v-on:click='odjaviSe()' id="ppButton">Odjavi se</button>
		</div>
		
		  </div>
		  
		  <div id="filtersPP">
		  <h2>Pretraga</h2>
		  
		  <div class="form-group">
			<div class="rowPP">
			  <div class="colPP">
				<label for="cenaOd">Cena od:</label>
				<input type="number" id="cena" name="cena" v-model="cenaOd">
			  </div>
			  <div class="colPP">
				<label for="cenaDo">Cena do:</label>
				<input type="number" id="cena" name="cena" v-model="cenaDo">
			  </div>
			</div>
			
			<div class="rowPP">
			  <div class="colPP">
				<label for="datumOd">Datum od:</label>
				<input type="date" id="datum" name="datum" v-model="datumOd">
			  </div>
			  <div class="colPP">
				<label for="datumOd">Datum do:</label>
				<input type="date" id="datum" name="datum" v-model="datumDo">
			  </div>
			</div>
			
			<div class="row" id="zadnjiPP">
			<div class="col">
				<label for="raco">RACO:</label>
	        	<select id="raco" name="raco" v-model="raco">
	          	<option v-for="r in sviObjekti" :value="r.id">{{r.naziv}}</option>
	        </select>
			</div>
		  </div>
			
			<div class="rowPP">
			  <div class="colPP">
				<button v-on:click="search()" class="search-button-AHP">Pretrazi</button>
			  </div>
			  <div class="colPP">
				<button v-on:click="reset()" class="reset-button-AHP">Reset</button>
			  </div>
			</div>
		  
		  </div>
		  </div>
		  
		<div id="porudzbineTableContainer">
          <table id="porudzbineTable">
            <tr>
			<th @click="sortColumn = 'datumIVreme'; toggleSortOrder()" >
			Pocetni datum
			<span v-if="sortColumn === 'datumIVreme'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
			</th>
			<th @click="sortColumn = 'trajanje'; toggleSortOrder()">
			  Trajanje
			   <span v-if="sortColumn === 'trajanje'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
			</th>
			<th @click="sortColumn = 'cena'; toggleSortOrder()">
			  Cena
			   <span v-if="sortColumn === 'cena'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
			</th>
			<th  @click="sortColumn = 'status'; toggleSortOrder()">
			  Status
			  <span v-if="sortColumn === 'status'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
			</th>
			<th>
			Otkazi
			</th>
            </tr>
            <tr v-for="p in sortedPorudzbine">
              <td>{{ formatDate(p.datumIVreme) }}</td>
              <td>{{ p.trajanje }}</td>
              <td>{{ p.cena }}</td>
              <td>{{ p.status }}</td>
              <td> <button v-on:click='otkazi(p.id)' id="innerButton"> Otkazi </button></td>
            </tr>
          </table>
        </div>
		
	
	  
	</body>
	</html>
			
	`
	,
   computed: {
  sortedPorudzbine() {
    if (this.sortColumn) {
      const sorted = this.svePorudzbine.slice().sort((a, b) => {
        const aValue = this.getColumnValue(a, this.sortColumn);
        const bValue = this.getColumnValue(b, this.sortColumn);

        if (typeof aValue === 'string' && typeof bValue === 'string') {
          return aValue.localeCompare(bValue);
        } else {
          if (aValue < bValue) return -1;
          if (aValue > bValue) return 1;
          return 0;
        }
      });

      return this.sortOrder === 'asc' ? sorted : sorted.reverse();
    } else {
      return this.svePorudzbine;
    }
  },
},

	  mounted() {
	    this.poslatoKorisnicko = this.$route.params.korisnicko;
	
	    axios.get("rest/users/getByUsername/" + this.poslatoKorisnicko)
	      .then(response => {
	        this.korisnik = response.data;
	        axios.get("rest/porudzbine/getByKupacId/" + this.korisnik.id)
	          .then(response1 => {
	            this.svePorudzbine = response1.data;
	            	axios.get('rest/objects')
					.then(response2=>{
					this.sviObjekti = response2.data
			});
	          });
	      });
	  }		
	,
	methods:{
		otkazi: function(id){
			event.preventDefault();
			axios.get('rest/porudzbine/getById/'+id)
				.then(response=>{
				this.porudzbina=response.data
				if(this.porudzbina.status == 'OBRADA'){
					axios.put('rest/porudzbine/otkazi/'+id)
					.then(response=>{
						axios.put('rest/users/oduzmiBodove/'+this.korisnik.id+'/'+(this.porudzbina.cena/1000)*133*4)
						.then(response=>{
							this.cenaOd=0;
							this.cenaDo=0;
							this.datumOd=null;
							this.datumDo=null;
							this.raco=0;
							axios.get("rest/porudzbine/getByKupacId/" + this.korisnik.id)
	          				.then(response1 => {
	            			this.svePorudzbine = response1.data;
	          				});
						})
					})
				}else{
					alert("Ne mozete otkazati ako nije u statusu obrada")
				}	
					
				
				})
			},
		getColumnValue(obj, column) {
	      const keys = column.split(".");
	      let value = obj;
	
	      for (const key of keys) {
	        value = value[key];
	      }
	
	      return value;
	    },
	    toggleSortOrder() {
	      if (this.sortColumn === this.sortColumn) {
	        this.sortOrder = this.sortOrder === "asc" ? "desc" : "asc";
	      } else {
	        this.sortOrder = "asc";
	      }
	    },
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
		odjaviSe : function() {
			router.push(`/`);
		},
		izmeni : function(){
			router.push('/IzmenaKorisnika/'+this.korisnik.id)
		},
		search : function(){
			event.preventDefault();
			if(this.datumOd==null){
				this.datumOd="0001-01-01"
			}
			if(this.datumDo==null){
				this.datumDo="9999-09-09"
			}
			axios.get('rest/pomoci/searchKupac/'+this.cenaOd+'/'+this.cenaDo+'/'+this.datumOd+'/'+this.datumDo+'/'+this.raco+'/'+this.korisnik.id)
				.then(response=>{
					this.svePorudzbine = response.data;
					if(this.datumDo=="9999-09-09"){
						this.datumDo=null
					}
					if(this.datumOd=="0001-01-01"){
						this.datumOd=null
					}
				})
		},
		reset : function(){
			this.cenaOd=0;
			this.cenaDo=0;
			this.datumOd=null;
			this.datumDo=null;
			this.raco=0;
			axios.get("rest/porudzbine/getByKupacId/" + this.korisnik.id)
	          .then(response1 => {
	            this.svePorudzbine = response1.data;
	          });
		},
		oceni : function(){
			event.preventDefault();
			router.push('/KreiranjeKomentara/'+this.poslatoKorisnicko)
		},
			prikaziKomentare : function(){
		event.preventDefault();
		router.push(`/PregledKomentara2`);	
	}
	}
	})
	

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/prikaz_profila.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
