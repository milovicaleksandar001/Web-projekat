const PrikazObjekata = {template: '<prikaz-objekata></prikaz-Objekata>'}
const LogIn = { template: '<log-in></log-in>' }
const Register = { template: '<register></register>' }
const Prikaz = {template: '<prikaz-profila></prikaz-profila>'}
const Izmena = {template: '<izmena-korisnika></izmena-korisnika>'}
const AdminHomePage = {template: '<adminHomePage></adminHomePage>'}
const ManagerHomePage = {template: '<manager-home-page></manager-home-page>'}
const RegisterManager = {template: '<register-manager></register-manager>'}
const RegisterManager1 = {template: '<register-manager1></register-manager1>'}
const RegisterRACO = {template: '<register-RACO></register-RACO>'}
const DodajVozilo = {template: '<dodaj-vozilo></dodaj-vozilo>'}
const IzmenaAutomobila = {template: '<izmena-automobila></izmena-automobila>'}
const PrikazOdabranogRaco = {template: '<prikaz-odabranog-raco></prikaz-odabranog-raco>'}
const CreatePorudzbina = {template: '<create-poruzbina></create-poruzbina>'}
const PregledKorpa = {template: '<pregled-korpa></pregled-korpa>'}
const PrikazIznajmljivanja = {template: '<prikaz-iznajmljivanja></prikaz-iznajmljivanja>'}
const Prikaz1 = {template: '<prikaz-profila1></prikaz-profila1>'}
const PrikazOdabranePorudzbine = {template: '<prikaz-odabrane-porudzbine></prikaz-odabrane-porudzbine>'}
const KreiranjeKomentara = {template: '<kreiranje-komentara></kreiranje-komentara>'}
const PregledKomentara = {template: '<pregled-komentara></pregled-komentara>'}
const PregledKomentara1 = {template: '<pregled-komentara1></pregled-komentara1>'}
const PregledKomentara2 = {template: '<pregled-komentara2></pregled-komentara2>'}
const PregledSumnjivih = {template: '<pregled-sumnjivih></pregled-sumnjivih>'}
const PrikazMenadzerRaco = {template: '<prikaz-menadzer-raco></prikaz-menadzer-raco>'}



const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home',component: PrikazObjekata},
		{ path: '/Login', component: LogIn},
		{ path: '/RegistracijaKorisnika', component: Register},
		{ path: '/PrikazProfila/:korisnicko', component: Prikaz},
		{ path: '/IzmenaKorisnika/:id', component: Izmena},
		{ path: '/AdminHomePage/:korisnicko', component: AdminHomePage},
		{ path: '/ManagerHomePage/:korisnicko', component: ManagerHomePage},
		{ path: '/RegistracijaMenadzera/:korisnicko', component: RegisterManager},
		{ path: '/RegistracijaMenadzera1/:korisnicko', component: RegisterManager1},
		{ path: '/RegistracijaRACO/:korisnicko', component: RegisterRACO},
		{ path: '/DodajVozilo/:korisnicko', component: DodajVozilo},
		{ path: '/IzmenaAutomobila/:id/:pomKorisnicko', component: IzmenaAutomobila},
		{ path: '/PrikazOdabranogRaco/:id', component: PrikazOdabranogRaco},
		{ path: '/CreatePorudzbina/:korisnicko', component: CreatePorudzbina},
		{ path: '/PregledKorpa/:korisnicko', component: PregledKorpa},
		{ path: '/PrikazIznajmljivanja/:korisnicko', component: PrikazIznajmljivanja},
		{ path: '/PrikazProfila1/:korisnicko', component: Prikaz1},
		{ path: '/PrikazOdabranePORUDZBINE/:id', component: PrikazOdabranePorudzbine},
		{ path: '/KreiranjeKomentara/:korisnicko', component: KreiranjeKomentara},
		{ path: '/PregledKomentara/:korisnicko', component: PregledKomentara},
		{ path: '/PregledKomentara1', component: PregledKomentara1},
		{ path: '/PregledKomentara2', component: PregledKomentara2},
		{ path: '/PregledSumnjivih', component: PregledSumnjivih},
		{ path: '/PrikazMenadzerRaco/:id', component: PrikazMenadzerRaco},
	  ]
});

var app = new Vue({
	router,
	el: '#users'
});