Vue.component("izmena-korisnika", {
    data() {
        return {
			korisnik:{
				id:null,korisnickoIme:null,lozinka:null,ime:null,prezime:null,pol:null,datum:null,uloga:null,svaIznajmljivanja:null,
				korpa:null,rentACarObjekat:null,bodovi:null,tipKupca:null	
					 },
			id: null,
			pom: null		   
			
        }
    },
    template: `
        <div class="containerEdit">
            <h2>Izmena korisnika</h2>
            <form>
                <div class="form-group">
                    <label>Korisnicko ime:</label>
                    <input type="text" id="username" name="username" v-model = "korisnik.korisnickoIme" required>
                </div>
                <div class="form-group">
                    <label>Lozinka:</label>
                    <input type="password" id="password" name="password" v-model = "korisnik.lozinka" required>
                </div>
                <div class="form-group">
                    <label>Ime:</label>
                    <input type="text" id="name" name="name" v-model = "korisnik.ime" required>
                </div>
                <div class="form-group">
                    <label>Prezime:</label>
                    <input type="text" id="surname" name="surname" v-model = "korisnik.prezime" required>
                </div>
                <div class="form-group">
                    <label>Pol:</label>
                    <select id="gender" name="gender" v-model="korisnik.pol" required>
                        <option value="MUSKO">Male</option>
                        <option value="ZENSKO">Female</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Datum rodjenja:</label>
                    <input type="date" id="dob" name="dob" v-model = "korisnik.datum" required>
                </div>
                <div class="form-group">
                    <input type="submit" value="Izmeni" v-on:click = "updateKorisnik()">
                </div>
            </form>
        </div>
    `,
	mounted(){
		this.id = this.$route.params.id;
		axios.get('rest/users/getById/' + this.id) 
			.then(response=>{
				this.korisnik = response.data
				this.pom = this.korisnik.korisnickoIme
			})
				}
	 ,
    methods: {
        updateKorisnik: function()
        {
			if(this.korisnik.korisnickoIme ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.korisnik.lozinka ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.korisnik.ime ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.korisnik.prezime ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.korisnik.pol ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.korisnik.datum ==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			
			event.preventDefault();
			
			axios.get('rest/users/register1/' + this.korisnik.korisnickoIme + '/' + this.pom)
				.then(response1=>{
					if(response1.data == true)
					{
					axios.put('rest/users/update/',this.korisnik).
						then(response => {
						router.push(`/PrikazProfila/`+ this.korisnik.korisnickoIme);	
						});
					}
					else
					{
					alert("Vec postoji korisnik sa tim korisnickim imenom")
					}
				})
			

				
			

		}
        
        
        
    }
});


var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/edit.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);