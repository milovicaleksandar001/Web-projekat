Vue.component("adminHomePage", {
	data : function() {
		return {
			poslatoKorisnicko:null,
			sviObjekti: null,
			ime:'', 
			prezime:'',
			korisnickoIme:'',
			uloga:"",
			tipKorisnika:'',
			sortColumn: "",
      		sortOrder: "asc",
			korisnik:{
				id:null,korisnickoIme:null,lozinka:null,ime:null,prezime:null,pol:null,datum:null,uloga:null,svaIznajmljivanja:null,
				korpa:null,rentACarObjekat:null,bodovi:null,tipKupca:null	
					 }
		}
	},
	template:
	`
	<html>
<head>
  <title>Admin Home Page</title>
</head>
<body>
  <div class="containerAHP">
  <h2>ADMIN Home Page</h2>    
  
    <div>
        <button v-on:click='kreirajMenadzera()' id="ahpButton">Kreiraj menadzera</button>
        <button v-on:click='kreirajRACO()' id="ahpButton">Kreiraj Rent A Car Objekat</button>
        <button v-on:click='prikaziPodatke()' id="ahpButton">Prikazi podatke o ovom profilu</button>
        <button v-on:click='prikaziKomentare()' id="ahpButton">Pregled komentara</button>
        <button v-on:click='prikaziSumnjive()' id="ahpButton">Pregled sumnjivih korisnika</button>
    	<button v-on:click='odjaviSe()' id="ahpButton">Odjavi se</button>
    </div>
  </div>
  
  
  <div id="filtersAHP">
  
  <h2>Pretraga</h2>
  
  <div class="form-group">
    <div class="rowAHP">
      <div class="colAHP">
        <label for="ime">Ime:</label>
        <input type="text" id="ime" name="ime" v-model="ime">
      </div>
      <div class="colAHP">
        <label for="tipVozila">Prezime:</label>
        <input type="text" id="prezime" name="prezime" v-model="prezime">
      </div>
    </div>
    
    <div class="rowAHP" id="zadnjiAHP">
      <div class="colAHP">
        <label for="korisnickoIme">Korisnicko ime:</label>
        <input type="text" id="korisnickoIme" name="korisnickoIme" v-model="korisnickoIme">
        </select>
      </div>
    </div>

    <div class="rowAHP">
      <div class="colAHP">
        <button v-on:click="search()" class="search-button">Pretrazi</button>
      </div>
      <div class="colAHP">
        <button v-on:click="reset()" class="reset-button">Reset</button>
      </div>
    </div>
        
    <h2>Filteri</h2>
    
     <div class="rowAHP">
      <div class="colAHP">
        <label for="uloga">Uloga:</label>
        <select id="prikazOtvorenih" name="prikazOtvorenih" v-model="uloga">
          <option value="ADMINISTRATOR">Administrator</option>
          <option value="MENADZER">Menadzer</option>
          <option value="KUPAC">Kupac</option>
        </select>
      </div>
      <div class="colAHP">
        <label for="tipKorisnika">Tip korisnika:</label>
        <input type="text" id="tipKorisnika" name="tipKorisnika" v-model="tipKorisnika">
      </div>
    </div>
  
  <div class="rowAHP">
      <div class="colAHP">
        <button v-on:click="filter()" class="search-button">Filter</button>
      </div>
      <div class="colAHP">
        <button v-on:click="resetFilters()" class="reset-button">Reset</button>
      </div>
    </div>
    
  </div>
</div>
  
  
<div id="tableUsers">
	  <table id="usersTable">
	    <tr>
	      <th @click="sortColumn = 'korisnickoIme'; toggleSortOrder()" class="expand-cell">
	        Korisnicko ime
	        <span v-if="sortColumn === 'korisnickoIme'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
	      </th>
	      <th @click="sortColumn = 'ime'; toggleSortOrder()" class="expand-cell">
	        Ime
	        <span v-if="sortColumn === 'ime'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
	      </th>
	      <th @click="sortColumn = 'prezime'; toggleSortOrder()" class="expand-cell">
	        Prezime
	        <span v-if="sortColumn === 'prezime'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
	      </th>
	      <th @click="sortColumn = 'uloga'; toggleSortOrder()" class="expand-cell">
	        Uloga
	        <span v-if="sortColumn === 'uloga'" :class="sortOrder === 'asc' ? 'sort-asc' : 'sort-desc'"></span>
	      </th>
	    </tr>
	    <tr v-for="o in sortedObjekti">
	      <td>{{o.korisnickoIme}}</td>
	      <td>{{o.ime}}</td>
	      <td>{{o.prezime}}</td>
	      <td>{{o.uloga}}</td>
	    </tr>
	  </table>
</div>
</body>
</html>
	
	`
	,
	    computed: {
  sortedObjekti() {
    if (this.sortColumn) {
      const sorted = this.sviObjekti.slice().sort((a, b) => {
        const aValue = this.getColumnValue(a, this.sortColumn);
        const bValue = this.getColumnValue(b, this.sortColumn);

        return aValue.localeCompare(bValue);
      });

      return this.sortOrder === 'asc' ? sorted : sorted.reverse();
    } else {
      return this.sviObjekti;
    }
  },
},
	
	 mounted(){
		this.poslatoKorisnicko = this.$route.params.korisnicko;
		this.ime = "";
		this.prezime = "";
		this.korisnickoIme="";
		this.uloga = "";
		this.tipKorisnika = "";
		axios.get('rest/users')

			.then(response=>{
				this.sviObjekti = response.data
			});
	},
	methods:{
			toggleSortOrder() {
			    if (this.sortColumn === this.sortColumn) {
			      this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
			    } else {
			      this.sortOrder = 'asc';
			    }
			  },
			getColumnValue(obj, column) {
			    const keys = column.split('.');
			    let value = obj;
			
			    for (let key of keys) {
			      value = value[key];
			    }
			
			    return value;
			  },
		odjaviSe : function() {
			router.push(`/`);
		},
		kreirajMenadzera : function(){
			router.push('/RegistracijaMenadzera/'+this.poslatoKorisnicko)
		},
		kreirajRACO : function(){
			router.push('/RegistracijaRACO/'+this.poslatoKorisnicko)
		},
		prikaziPodatke : function(){
			router.push(`/PrikazProfila1/`+this.poslatoKorisnicko);
		},
		search : function(){
			event.preventDefault();
					if(this.ime==""){
						this.ime = " "
					}
					if(this.prezime==""){
						this.prezime = " "
					}
					if(this.korisnickoIme==""){
						this.korisnickoIme = "prenosVrednost"
					}
					axios.get('rest/users/search/'+this.ime+'/'+this.prezime+'/'+this.korisnickoIme)
					.then(response=>{
					this.sviObjekti = response.data
					if(this.ime==" "){
						this.ime = ""
					}
					if(this.prezime==" "){
						this.prezime = ""
					}
					if(this.korisnickoIme=="prenosVrednost"){
						this.korisnickoIme = ""
					}
				})
		},
		reset : function(){
			this.ime = "";
			this.prezime = "";
			this.korisnickoIme = "";
			this.uloga= "";
			this.tipKorisnika = "";
			axios.get('rest/users')
			.then(response=>{
				this.sviObjekti = response.data
			})
		},
		filter : function(){
			event.preventDefault();
			if(this.uloga==""){
					this.uloga = " "
				}
				if(this.korisnickoIme==""){
					this.korisnickoIme = "prenosVrednost"
					}
				if(this.prezime==""){
						this.prezime = " "
					}
					if(this.ime==""){
						this.ime = " "
					}
				axios.get('rest/users/filter/'+this.ime+'/'+this.prezime+'/'+this.uloga+'/'+this.korisnickoIme)
				.then(response=>{
					this.sviObjekti = response.data
					if(this.uloga==" "){
						this.uloga = ""
					}
					if(this.korisnickoIme=="prenosVrednost"){
						this.korisnickoIme = ""
					}
					if(this.prezime==" "){
						this.prezime = ""
					}
					if(this.ime==" "){
						this.ime = ""
					}
				})
	},
	resetFilters : function(){
			this.uloga= "";
			this.tipKorisnika = "";
			if(this.ime==""){
						this.ime = " "
					}
					if(this.prezime==""){
						this.prezime = " "
					}
					if(this.korisnickoIme==""){
						this.korisnickoIme = "prenosVrednost"
					}
			axios.get('rest/users/search/'+this.ime+'/'+this.prezime+'/'+this.korisnickoIme)
				.then(response=>{
					this.sviObjekti = response.data
					if(this.ime==" "){
						this.ime = ""
					}
					if(this.prezime==" "){
						this.prezime = ""
					}
					if(this.korisnickoIme=="prenosVrednost"){
						this.korisnickoIme = ""
					}
		})
	},
	prikaziKomentare : function(){
		event.preventDefault();
		router.push(`/PregledKomentara1`);	
	},
	prikaziSumnjive : function(){
		event.preventDefault();
		router.push(`/PregledSumnjivih`);	
	}
	}
	})
	

var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/adminHomePage.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
