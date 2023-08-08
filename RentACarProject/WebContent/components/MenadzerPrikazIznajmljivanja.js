Vue.component("prikaz-iznajmljivanja", {
    data() {
        return {
			cenaOd:0,
			cenaDo:0,
			raco:null,
			datumOd:null,
			datumDo:null,
			poslatoKorisnicko:null,
			svePorudzbine:null,
			menadzerovObjekatId:null,
			idKorisnika:null,	
			sortColumn: "",
      		sortOrder: "asc",  
        }
    },
    template: `
		<div id="PregledIznajmljivanja">
		
				  <div id="filtersPPM">
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
			
			<div class="rowPP">
			  <div class="colPP">
				<button v-on:click="search()" class="search-button-AHP">Pretrazi</button>
			  </div>
			  <div class="colPP">
				<button v-on:click="reset()" class="reset-button-AHP">Reset</button>
			  </div>
			</div>
		  
		  </div>
		 <div id="porudzbineTableContainer">
				
				<table id="porudzbineTable">
				<tr>
				  <th colspan="5">Lista vasih porudzbina</th>
				</tr>
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
				Prikazi
				</th>
	            </tr>
				<tr v-for="p in sortedPorudzbine">
				  <td>{{ formatDate(p.datumIVreme) }}</td>
				  <td>{{p.trajanje}}</td>
				  <td>{{p.cena}}</td>
				  <td>{{p.status}}</td>	      
				  <td> <button v-on:click='prikazi(p.id)' id="innerButton"> Prikazi </button></td>
				</tr>
			  </table>
			  
			</div>
		
		</div>
		</div>
    `,
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
    mounted(){
		this.poslatoKorisnicko = this.$route.params.korisnicko;
		axios.get('rest/users/getIdByUsername/'+this.poslatoKorisnicko)
			.then(response=>{
				this.idKorisnika = response.data
				axios.get('rest/objects/getObjectIdByManagerId/'+this.idKorisnika)
					.then(response2=>{
						this.menadzerovObjekatId = response2.data
						axios.get('rest/pomoci/porudzbineObjekta/'+this.menadzerovObjekatId)
							.then(response3=>{
								this.svePorudzbine = response3.data
							})
					})
			})
		
			/*axios.get('rest/users/getByUsername/' + this.poslatoKorisnicko)
			.then(response=>{
				this.korisnik = response.data
					axios.get('rest/porudzbine/getByKupacId/'+this.korisnik.id) 
					.then(response1=>{
						this.svePorudzbine = response1.data
					})
				
			})*/
	},
	methods: {
		prikazi : function(id){
			event.preventDefault();
			router.push(`/PrikazOdabranePORUDZBINE/`+id)
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
		search : function(){
			event.preventDefault();
			if(this.datumOd==null){
				this.datumOd="0001-01-01"
			}
			if(this.datumDo==null){
				this.datumDo="9999-09-09"
			}
			axios.get('rest/pomoci/searchMenadzer/'+this.cenaOd+'/'+this.cenaDo+'/'+this.datumOd+'/'+this.datumDo+'/'+this.menadzerovObjekatId)
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
			axios.get('rest/pomoci/porudzbineObjekta/'+this.menadzerovObjekatId)
							.then(response3=>{
								this.svePorudzbine = response3.data
							})
		}
		}
		})
		
		
var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/MenadzerPrikazIznajmljivanja.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
