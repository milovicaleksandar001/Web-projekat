Vue.component("log-in", {
	data : function() {
		return {
			korisnicko:null,
			sifra:null,
			korisnik:{
				korisnickoIme:null,lozinka:null,ime:null,prezime:null,pol:null,datum:null,uloga:null,svaIznajmljivanja:null,
				korpa:null,rentACarObjekat:null,bodovi:null,tipKupca:null	
					 }
		}
	},
	template:
	`
		 <html>
<head>
  <title>Login Form</title>
</head>
<body>
  <div class="containerLogin">
   <h2>Log in</h2>
    <form>
      <label for="username"><b>Username</b></label>
      <input type="text" v-model="korisnicko" placeholder="Enter Username" name="username" required>

      <label for="password"><b>Password</b></label>
      <input type="password" v-model="sifra" placeholder="Enter Password" name="password" required>

      <button type="submit" v-on:click='logujSe()'>Login</button>
      <button v-on:click='registrujSe()'>Registracija</button>
    </form>


  </div>
</body>
</html>
	
	`
	,
	mounted(){

				}
			
	,
	methods : {
		registrujSe : function() {
			router.push(`/RegistracijaKorisnika`);
		},
		logujSe: function(){
			if(this.korisnicko==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			if(this.sifra==null){
				alert("Molim vas popunite sva polja");
				return;
			}
			event.preventDefault();
			

			axios.get('rest/users/login/' + this.korisnicko + '/' + this.sifra)
				.then(response => {
						axios.get('rest/users/checkBlock/' + this.korisnicko + '/' + this.sifra)
						.then(response1 => { 
					
					
				
							
					if(response.data == true)
					{
						if(response1.data == true)
						{
							alert("Korisnik je blokiran")
							return;
						}
						
						axios.get('rest/users/getByUsername/' + this.korisnicko)
							.then(response=>{
							this.korisnik = response.data
						if(this.korisnik.uloga=="KUPAC"){
							router.push(`/PrikazProfila/`+this.korisnicko);
						}else if(this.korisnik.uloga=="ADMINISTRATOR")
						{
							router.push(`/AdminHomePage/`+this.korisnicko);
						}else if(this.korisnik.uloga=="MENADZER")
						{
							router.push(`/ManagerHomePage/`+this.korisnicko);
						}
							})
					}else
					{
						alert("Ne postoji takav korisnik");
					}
					})
						})
		}
		}
		
});


var link = document.createElement("link");
link.rel = "stylesheet";
link.type = "text/css";
link.href = "css/login.css";
var firstLink = document.getElementsByTagName("link")[0];
document.head.insertBefore(link, firstLink);
