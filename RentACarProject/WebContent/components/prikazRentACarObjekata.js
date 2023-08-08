Vue.component("prikaz-objekata", {
    data() {
        return {
			sveLokacije:null,
			sviObjekti: null,
			naziv:'', 
			lokacija:'',
			tipVozila:'',
			prosecnaOcena:null,
			sortColumn: "",
      		sortOrder: "asc",  
      		tipGoriva:"",   
      		vrstaMenjaca:"",
      		prikazOtvorenih:""
        }
    },
    template: `
		<div id="rentACarList">
		
				  <div id="menu">
				  <br>
		    <button id="loginBtn" v-on:click="login()">Log in</button>
		    <button id="registerBtn" v-on:click="register()">Register</button>
		  		  </div>
		 
	<div id="filters">
	  <h2>Pretraga</h2>
	  
	  <div class="form-group">
	    <div class="row">
	      <div class="col">
	        <label for="naziv">Naziv:</label>
	        <input type="text" id="naziv" name="naziv" v-model="naziv">
	      </div>
	      <div class="col">
	        <label for="tipVozila">Tip vozila:</label>
	        <input type="text" id="tipVozila" name="tipVozila" v-model="tipVozila">
	      </div>
	    </div>
	    
	    <div class="row">
	      <div class="col">
	        <label for="lokacija">Lokacija:</label>
	        <select id="lokacija" name="lokacija" v-model="lokacija">
	          <option v-for="loc in sveLokacije" :value="loc.adresa">{{loc.adresa}}</option>
	        </select>
	      </div>
	      <div class="col">
	        <label for="prosecnaOcena">Prosecna ocena:</label>
	        <input type="text" id="prosecnaOcena" name="prosecnaOcena" v-model="prosecnaOcena">
	      </div>
	    </div>
	    
	    <div class="row">
	      <div class="col">
	        <button v-on:click="search()" class="search-button-AHP">Pretrazi</button>
	      </div>
	      <div class="col">
	        <button v-on:click="reset()" class="reset-button-AHP">Reset</button>
	      </div>
	    </div>
	    
	    <h2>Filteri</h2>
	    
	     <div class="row">
	      <div class="col">
	        <label for="naziv">Vrsta menjaca vozila:</label>
	        <select id="vrstaMenjaca" name="vrstaMenjaca" v-model="vrstaMenjaca">
	         	<option value="MANUELNI">Manuelni</option>
		      	<option value="AUTOMATIK">Automatik</option>
		      	</select>
	      </div>
	      <div class="col">
	        <label for="tipVozila">Tip goriva:</label>
	        <select id="tipGoriva" name="tipGoriva" v-model="tipGoriva">
	          	<option value="DIZEL">Dizel</option>
		      	<option value="BENZIN">Benzin</option>
		      	<option value="HIBRID">Hibrid</option>
		      	<option value="ELEKTRICNI">Elektricni</option>
		      	</select>
	      </div>
	    </div>

		
		<div class="row" id="zadnji">
		  <div class="col">
		    <label for="tipVozila">Prikaz samo otvorenih:</label>
		    <select id="prikazOtvorenih" name="prikazOtvorenih" v-model="prikazOtvorenih">
		      <option value="RADI">DA</option>
		      <option value="NE_RADI">NE</option>
		    </select>
		  </div>
		</div>
		
		<div class="row">
	      <div class="col">
	        <button v-on:click="filter()" class="search-button">Filter</button>
	      </div>
	      <div class="col">
	        <button v-on:click="resetFilters()" class="reset-button">Reset</button>
	      </div>
	    </div>
	    
	  </div>
	</div>
	
			  
      <table id="rentACarTable">
        <tr>
		    <th colspan="6">Lista Rent A Car objekata</th>
		  </tr>
		  <tr>
		    <th @click="sortColumn = 'naziv'; toggleSortOrder()" class="expand-cell">
		      Naziv
		      <span v-if="sortColumn === 'naziv'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
		    </th>
		    <th @click="sortColumn = 'lokacija.adresa'; toggleSortOrder()" class="expand-cell" >
		      Lokacija
		      <span v-if="sortColumn === 'lokacija.adresa'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
		    </th>
		    <th>Logo</th>
		    <th @click="sortColumn = 'ocena'; toggleSortOrder()">
		      Prosecna ocena
		      <span v-if="sortColumn === 'ocena'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
		    </th>
		    <th @click="sortColumn = 'status'; toggleSortOrder()" class="expand-cell">
		      Status
		      <span v-if="sortColumn === 'status'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
		    </th>
		    <th>Prikazi</th>
		  </tr>
		  <tr v-for="o in sortedObjekti">
		    <td>{{o.naziv}}</td>
		    <td>{{o.lokacija.adresa}}</td>
		    <td><img :src="o.logo" alt="Logo" class="logo-image"></td>
		    <td>{{o.ocena}}</td>
		    <td>{{o.status}}</td>
		    <td><button v-on:click='prikazi(o.id)' id="innerButton"> Prikazi </button></td>
		  </tr>
		</table>
		

		  <br>
		</div>
    `,
    computed: {
    sortedObjekti() {
      if (this.sortColumn) {
        const sorted = this.sviObjekti.slice().sort((a, b) => {
          const aValue = this.getColumnValue(a, this.sortColumn);
          const bValue = this.getColumnValue(b, this.sortColumn);

          return aValue.localeCompare(bValue);
        });

        return this.sortOrder === "asc" ? sorted : sorted.reverse();
      } else {
        return this.sviObjekti;
      }
    },
  },
    mounted(){
		this.naziv = "";
        this.tipVozila = "";
        this.lokacija = "";
        this.vrstaMenjaca= "";
		this.prikazOtvorenih = "";
		this.tipGoriva = "";
        this.prosecnaOcena = 0;
		axios.get('rest/objects/sortPrikaz')
			.then(response=>{
				this.sviObjekti = response.data
			})
		axios.get('rest/locations')
			.then(response=>{
				this.sveLokacije = response.data
			});
	},
	methods: {
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
		login : function() {
			router.push(`/Login`);
		},
		register : function() {
			router.push(`/RegistracijaKorisnika`);
		},
		search : function(){
			event.preventDefault();
					if(this.naziv==""){
						this.naziv = " "
					}
					if(this.tipVozila==""){
						this.tipVozila = " "
					}
					if(this.lokacija==""){
						this.lokacija = " "
					}
			axios.get('rest/vozila/search/'+this.naziv+'/'+this.tipVozila+'/'+this.lokacija+'/'+this.prosecnaOcena)
				.then(response=>{
					this.sviObjekti = response.data
					if(this.naziv==" "){
						this.naziv = ""
					}
					if(this.tipVozila==" "){
						this.tipVozila = ""
					}
					if(this.lokacija==" "){
						this.lokacija = ""
					}
				})
		},
		reset : function(){
			this.naziv = "";
			this.tipVozila = "";
			this.lokacija = "";
			this.vrstaMenjaca= "";
			this.prikazOtvorenih = "";
			this.tipGoriva = "";
			this.prosecnaOcena = 0;
			axios.get('rest/objects/sortPrikaz')
			.then(response=>{
				this.sviObjekti = response.data
			})
		},
		
		filter : function(){
			event.preventDefault();
			if(this.vrstaMenjaca==""){
					this.vrstaMenjaca = " "
				}
				if(this.prikazOtvorenih==""){
					this.prikazOtvorenih = " "
				}
				if(this.tipGoriva==""){
					this.tipGoriva = " "
					}
				if(this.naziv==""){
						this.naziv = " "
					}
					if(this.tipVozila==""){
						this.tipVozila = " "
					}
					if(this.lokacija==""){
						this.lokacija = " "
					}
				axios.get('rest/vozila/filter/'+this.naziv+'/'+this.tipVozila+'/'+this.lokacija+'/'+this.prikazOtvorenih+'/'+this.vrstaMenjaca+'/'+this.tipGoriva+'/'+this.prosecnaOcena)
				.then(response=>{
					this.sviObjekti = response.data
					if(this.vrstaMenjaca==" "){
						this.vrstaMenjaca = ""
					}
					if(this.prikazOtvorenih==" "){
						this.prikazOtvorenih = ""
					}
					if(this.tipGoriva==" "){
						this.tipGoriva = ""
					}
					if(this.naziv==" "){
						this.naziv = ""
					}
					if(this.tipVozila==" "){
						this.tipVozila = ""
					}
					if(this.lokacija==" "){
						this.lokacija = ""
					}
				})
		},
		resetFilters : function(){
			this.vrstaMenjaca= "";
			this.prikazOtvorenih = "";
			this.tipGoriva = "";
			if(this.naziv==""){
						this.naziv = " "
					}
					if(this.tipVozila==""){
						this.tipVozila = " "
					}
					if(this.lokacija==""){
						this.lokacija = " "
					}
			axios.get('rest/vozila/search/'+this.naziv+'/'+this.tipVozila+'/'+this.lokacija+'/'+this.prosecnaOcena)
				.then(response=>{
					this.sviObjekti = response.data
					if(this.naziv==" "){
						this.naziv = ""
					}
					if(this.tipVozila==" "){
						this.tipVozila = ""
					}
					if(this.lokacija==" "){
						this.lokacija = ""
					}
		})
		},
		prikazi : function(id){
			event.preventDefault();
			router.push(`/PrikazOdabranogRaco/`+id)
		}
		}
		})
		
		
var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/prikaz_raco.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
