package zoom.city.android.main.container;

import java.util.ArrayList;
import java.util.List;

public class StringContainer {
	// CONSTUCTOR
	
	List<List<String>> iconTextList = new ArrayList<List<String>>();
	

	public List<List<String>> getIconTextList() {
		return iconTextList;
	}

	public void setIconTextList(List<List<String>> iconTextList) {
		this.iconTextList = iconTextList;
	}


	private static StringContainer instance = null;

	public StringContainer() {
		inicIconTitleListLocal();
		inicIconTitleListEnglish();
		inicIconTitleListEspanola();
		inicIconTitleListFrench();
		inicIconTitleListItalian();
		inicIconTitleListGerman();
		inicIconTitleListRuski();
	}

	public static StringContainer getInstance() {
		if (instance == null) {
			instance = new StringContainer();		
		}
		return instance;
	}

	public static void setInstance(StringContainer instance) {
		StringContainer.instance = instance;
	}
	
	
	private void inicIconTitleListLocal(){
		List<String> iconTextLocal = new ArrayList<String>();
		
		
		iconTextLocal.add("Must 2 Do");
		iconTextLocal.add("Culture");
		iconTextLocal.add("City Explore");
		iconTextLocal.add("Business Directory");
		iconTextLocal.add("Drink & Food");
		iconTextLocal.add("Night Life");
		iconTextLocal.add("Shopping");
		iconTextLocal.add("Excursions");
		iconTextLocal.add("Accommodation");
		iconTextLocal.add("Wellness&Spa");
		iconTextLocal.add("Maps");
		iconTextLocal.add("Calendar");
		iconTextLocal.add("Theatre");
		iconTextLocal.add("Concerts");
		iconTextLocal.add("Museum And Gallery");
		iconTextLocal.add("Cultural Center");
		iconTextLocal.add("Books");
		iconTextLocal.add("Attractions");
		iconTextLocal.add("Inspire Me");
		iconTextLocal.add("Traffic");
		iconTextLocal.add("Money");
		iconTextLocal.add("Medicine");
		iconTextLocal.add("Petrol");
		iconTextLocal.add("Wi-Fi");
		iconTextLocal.add("Hotels");
		iconTextLocal.add("Apartments");
		iconTextLocal.add("Motels");
		iconTextLocal.add("Hostels");
		iconTextLocal.add("Rooms");
		iconTextLocal.add("Camps");
		iconTextLocal.add("Rent A Local");
		iconTextLocal.add("Long Term Rental");
		iconTextLocal.add("Bus");
		iconTextLocal.add("Taxi");
		iconTextLocal.add("Rent a car");
		iconTextLocal.add("Rent a Scooter");
		iconTextLocal.add("Air Traffic");
		iconTextLocal.add("Train");
		iconTextLocal.add("Rent a ...");
		iconTextLocal.add("Banks");
		iconTextLocal.add("Atms");
		iconTextLocal.add("Exchange Office");
		iconTextLocal.add("Hospital");
		iconTextLocal.add("Pharmacy");
		iconTextLocal.add("Clinics");
		iconTextLocal.add("Cafe");
		iconTextLocal.add("Restaurants");
		iconTextLocal.add("Fast Food");
		iconTextLocal.add("Clubs");
		iconTextLocal.add("Bars");
		iconTextLocal.add("Events");
		iconTextLocal.add("Wellness");
		iconTextLocal.add("Spa");
		iconTextLocal.add("Beauty");
		iconTextLocal.add("Rainbow");
		iconTextLocal.add("Business Directory");
		iconTextLocal.add("Search");
		iconTextLocal.add("Custom Search");
		iconTextLocal.add("Company Name");
		iconTextLocal.add("Address");
		iconTextLocal.add("City");
		iconTextLocal.add("Zone");
		iconTextLocal.add("Activities Sector");
		iconTextLocal.add("Activities");
		iconTextLocal.add("State");
		iconTextLocal.add("Language");
		iconTextLocal.add("OK");
		iconTextLocal.add("Settings");
		iconTextLocal.add("You must enter a state!");
		iconTextLocal.add("You must enter a city!");
		iconTextLocal.add("You must enter a language!");
		iconTextLocal.add("Send SMS");
		iconTextLocal.add("PLEASE ONE CAB to this address:");
		iconTextLocal.add("View on map");
		iconTextLocal.add("Categories");
		iconTextLocal.add("More...");
		iconTextLocal.add("Must 2 Do");
		iconTextLocal.add("Metro");
		iconTextLocal.add("Sport");
		iconTextLocal.add("Cruising");
		
		iconTextList.add(iconTextLocal);
		
	}
	
	
	private void inicIconTitleListEnglish(){
		List<String> iconTextEnglish = new ArrayList<String>();
		
		iconTextEnglish.add("Must 2 Do");
		iconTextEnglish.add("Culture");
		iconTextEnglish.add("City Zoom");
		iconTextEnglish.add("Business Directory");
		iconTextEnglish.add("Drink & Food");
		iconTextEnglish.add("Night Life");
		iconTextEnglish.add("Shopping");
		iconTextEnglish.add("Excursions");
		iconTextEnglish.add("Accommodation");
		iconTextEnglish.add("Wellness&Spa");
		iconTextEnglish.add("Maps");
		iconTextEnglish.add("Calendar");
		iconTextEnglish.add("Theatre");
		iconTextEnglish.add("Concerts");
		iconTextEnglish.add("Museum And Gallery");
		iconTextEnglish.add("Cultural Center");
		iconTextEnglish.add("Books");
		iconTextEnglish.add("Attractions");
		iconTextEnglish.add("Inspire Me");
		iconTextEnglish.add("Traffic");
		iconTextEnglish.add("Money");
		iconTextEnglish.add("Medicine");
		iconTextEnglish.add("Petrol");
		iconTextEnglish.add("Wi-Fi");
		iconTextEnglish.add("Hotels");
		iconTextEnglish.add("Apartments");
		iconTextEnglish.add("Motels");
		iconTextEnglish.add("Hostels");
		iconTextEnglish.add("Rooms");
		iconTextEnglish.add("Camps");
		iconTextEnglish.add("Rent A Local");
		iconTextEnglish.add("Long Term Rental");
		iconTextEnglish.add("Bus");
		iconTextEnglish.add("Taxi");
		iconTextEnglish.add("Rent a car");
		iconTextEnglish.add("Rent a Scooter");
		iconTextEnglish.add("Air Traffic");
		iconTextEnglish.add("Train");
		iconTextEnglish.add("Rent a ...");
		iconTextEnglish.add("Banks");
		iconTextEnglish.add("Atms");
		iconTextEnglish.add("Exchange Office");
		iconTextEnglish.add("Hospital");
		iconTextEnglish.add("Pharmacy");
		iconTextEnglish.add("Clinics");
		iconTextEnglish.add("Cafe");
		iconTextEnglish.add("Restaurants");
		iconTextEnglish.add("Fast Food");
		iconTextEnglish.add("Clubs");
		iconTextEnglish.add("Bars");
		iconTextEnglish.add("Events");
		iconTextEnglish.add("Wellness");
		iconTextEnglish.add("Spa");
		iconTextEnglish.add("Beauty");
		iconTextEnglish.add("Rainbow");
		iconTextEnglish.add("Business Directory");
		iconTextEnglish.add("Search");
		iconTextEnglish.add("Custom Search");
		iconTextEnglish.add("Company Name");
		iconTextEnglish.add("Address");
		iconTextEnglish.add("City");
		iconTextEnglish.add("Zone");
		iconTextEnglish.add("Activities Sector");
		iconTextEnglish.add("Activities");
		iconTextEnglish.add("State");
		iconTextEnglish.add("Language");
		iconTextEnglish.add("OK");
		iconTextEnglish.add("Settings");
		iconTextEnglish.add("You must enter a state!");
		iconTextEnglish.add("You must enter a city!");
		iconTextEnglish.add("You must enter a language!");
		iconTextEnglish.add("Send SMS");
		iconTextEnglish.add("PLEASE ONE CAB to this address:");
		iconTextEnglish.add("View on map");
		iconTextEnglish.add("Categories");
		iconTextEnglish.add("More...");
		iconTextEnglish.add("Must 2 Do");
		iconTextEnglish.add("Metro");
		iconTextEnglish.add("Sport");
		iconTextEnglish.add("Cruising");
		
		iconTextList.add(iconTextEnglish);
		
	}

	
	private void inicIconTitleListGerman(){
		List<String> iconTextGerman = new ArrayList<String>();
		
		iconTextGerman.add("Müssen Tun");
		iconTextGerman.add("Kultur");
		iconTextGerman.add("Stadt-Zoom");
		iconTextGerman.add("Branchenbuch");
		iconTextGerman.add("Trinken & Essen");
		iconTextGerman.add("Nachtleben");
		iconTextGerman.add("Einkaufen");
		iconTextGerman.add("Ausflug");
		iconTextGerman.add("Unterkunft");
		iconTextGerman.add("Wellness&Spa");
		iconTextGerman.add("Karten");
		iconTextGerman.add("Kalender");
		iconTextGerman.add("Theater");
		iconTextGerman.add("Konzerte");
		iconTextGerman.add("Museum & Galerie");
		iconTextGerman.add("Cultural Center");
		iconTextGerman.add("Bücher");
		iconTextGerman.add("Attraktion");
		iconTextGerman.add("Inspiration");
		iconTextGerman.add("Verkehr");
		iconTextGerman.add("Geld");
		iconTextGerman.add("Arznei");
		iconTextGerman.add("Benzin");
		iconTextGerman.add("Wi-Fi");
		iconTextGerman.add("Hotels");
		iconTextGerman.add("Apartments");
		iconTextGerman.add("Motels");
		iconTextGerman.add("Hostels");
		iconTextGerman.add("Zimmer");
		iconTextGerman.add("Camps");
		iconTextGerman.add("Mieten Sie Einen Lokalen");
		iconTextGerman.add("Langzeitvermietung");
		iconTextGerman.add("Bus");
		iconTextGerman.add("Taxi");
		iconTextGerman.add("Ein Auto Mieten");
		iconTextGerman.add("Einen Motorroller Mieten");
		iconTextGerman.add("Luftverkehr");
		iconTextGerman.add("Zug");
		iconTextGerman.add("Mieten A ...");
		iconTextGerman.add("Banken");
		iconTextGerman.add("Geldautomaten");
		iconTextGerman.add("Wechselstube");
		iconTextGerman.add("Krankenhaus");
		iconTextGerman.add("Apotheke");
		iconTextGerman.add("Kliniken");
		iconTextGerman.add("Café");
		iconTextGerman.add("Restaurants");
		iconTextGerman.add("Fast Food");
		iconTextGerman.add("Clubs");
		iconTextGerman.add("Barren");
		iconTextGerman.add("Ereignis");
		iconTextGerman.add("Bien-Être");
		iconTextGerman.add("Spa");
		iconTextGerman.add("Schönheit");
		iconTextGerman.add("Regenbogen");
		iconTextGerman.add("Branchenbuch");
		iconTextGerman.add("Suche");
		iconTextGerman.add("Benutzerdefinierte Suche ");
		iconTextGerman.add("Firmenname");
		iconTextGerman.add("Adresse");
		iconTextGerman.add("Stadt");
		iconTextGerman.add("Zone");
		iconTextGerman.add("Sektor");
		iconTextGerman.add("Aktivitäten");
		iconTextGerman.add("Zustand");
		iconTextGerman.add("Sprache ");
		iconTextGerman.add("Ok");
		iconTextGerman.add("Einstellungen");
		iconTextGerman.add("Sie müssen einen Zustand geben!");
		iconTextGerman.add("Sie müssen eine Stadt zu betreten!");
		iconTextGerman.add("Sie müssen eine Sprache geben!");
		iconTextGerman.add("SMS senden");
		iconTextGerman.add("Bitte ein Taxi zu dieser Adresse:");
		iconTextGerman.add("Auf der Karte");
		iconTextGerman.add("Kategorien");
		iconTextGerman.add("More...");
		iconTextGerman.add("Must 2 Do");
		iconTextGerman.add("Metro");
		iconTextGerman.add("Sport");
		iconTextGerman.add("Kreuzfahrt");
		
		iconTextList.add(iconTextGerman);
		
	}
	
	
	private void inicIconTitleListFrench(){
		List<String> iconTextFrench = new ArrayList<String>();
		
		iconTextFrench.add("Doit Faire");
		iconTextFrench.add("Culture");
		iconTextFrench.add("Ville Zoom");
		iconTextFrench.add("Annuaire Des Entreprises");
		iconTextFrench.add("Boisson Et Des Aliments");
		iconTextFrench.add("Vie Nocturne");
		iconTextFrench.add("Shopping");
		iconTextFrench.add("Excursion");
		iconTextFrench.add("Logement");
		iconTextFrench.add("Bien-Être & Spa");
		iconTextFrench.add("Cartes");
		iconTextFrench.add("Calendrier");
		iconTextFrench.add("Théâtre");
		iconTextFrench.add("Concerts");
		iconTextFrench.add("Musée & Galerie");
		iconTextFrench.add("Centre Culturel");
		iconTextFrench.add("Livres");
		iconTextFrench.add("Attraction");
		iconTextFrench.add("Inspirez-Moi");
		iconTextFrench.add("Trafic");
		iconTextFrench.add("Argent");
		iconTextFrench.add("Médecine");
		iconTextFrench.add("Essence");
		iconTextFrench.add("Wi-Fi");
		iconTextFrench.add("Hôtels");
		iconTextFrench.add("Appartements");
		iconTextFrench.add("Motels");
		iconTextFrench.add("Auberges De Jeunesse");
		iconTextFrench.add("Chambre");
		iconTextFrench.add("Camps");
		iconTextFrench.add("Louer Une Locale");
		iconTextFrench.add("Location À Long Terme");
		iconTextFrench.add("Bus");
		iconTextFrench.add("Taxi");
		iconTextFrench.add("Louer Une Voiture");
		iconTextFrench.add("Louez Un Scooter");
		iconTextFrench.add("Trafic Aérien");
		iconTextFrench.add("Train");
		iconTextFrench.add("Rent a...");
		iconTextFrench.add("Banques");
		iconTextFrench.add("Auto De Billets");
		iconTextFrench.add("Échange");
		iconTextFrench.add("Hôpital");
		iconTextFrench.add("Pharmacie");
		iconTextFrench.add("Cliniques");
		iconTextFrench.add("Café");
		iconTextFrench.add("Restaurants");
		iconTextFrench.add("Restauration Rapide");
		iconTextFrench.add("Clubs");
		iconTextFrench.add("Bars");
		iconTextFrench.add("Événement");
		iconTextFrench.add("Wellness");
		iconTextFrench.add("Terme");
		iconTextFrench.add("Beauté");
		iconTextFrench.add("Rainbow");
		iconTextFrench.add("Annuaire Des Entreprises");
		iconTextFrench.add("Recherche");
		iconTextFrench.add("Recherche Personnalisée");
		iconTextFrench.add("Nom De L'entreprise");
		iconTextFrench.add("Adresse");
		iconTextFrench.add("Ville");
		iconTextFrench.add("Zone");
		iconTextFrench.add("Secteur");
		iconTextFrench.add("Activités");
		iconTextFrench.add("état");
		iconTextFrench.add("Langue");
		iconTextFrench.add("Bien");
		iconTextFrench.add("Paramètres");
		iconTextFrench.add("Vous devez entrer dans un état!");
		iconTextFrench.add("Vous devez entrer une ville!");
		iconTextFrench.add("Vous devez entrer une langue!");
		iconTextFrench.add("Envoyer un SMS");
		iconTextFrench.add("S'il vous plaît un taxi pour cette adresse:");
		iconTextFrench.add("Voir sur la carte");
		iconTextFrench.add("Catégories");
		iconTextFrench.add("More...");
		iconTextFrench.add("Must 2 Do");
		iconTextFrench.add("Métro");
		iconTextFrench.add("Sport");
		iconTextFrench.add("Croisière");
		
		iconTextList.add(iconTextFrench);
		
	}

	
	private void inicIconTitleListItalian(){
		List<String> iconTextItalian = new ArrayList<String>();
		
		iconTextItalian.add("Deve Fare");
		iconTextItalian.add("Cultura");
		iconTextItalian.add("Città Zoom");
		iconTextItalian.add("Directory Aziende");
		iconTextItalian.add("Cibi E Bevande");
		iconTextItalian.add("Vita Notturna");
		iconTextItalian.add("Shopping");
		iconTextItalian.add("Escursione");
		iconTextItalian.add("Sistemazione");
		iconTextItalian.add("Wellness & Spa");
		iconTextItalian.add("Mappe");
		iconTextItalian.add("Calendario");
		iconTextItalian.add("Teatro");
		iconTextItalian.add("Concerti");
		iconTextItalian.add("Museo E Galleria");
		iconTextItalian.add("Cultural Center");
		iconTextItalian.add("Libri");
		iconTextItalian.add("Attrazione");
		iconTextItalian.add("Ispirazione");
		iconTextItalian.add("Traffico");
		iconTextItalian.add("Soldi");
		iconTextItalian.add("Medicina");
		iconTextItalian.add("Benzina");
		iconTextItalian.add("Wi-Fi");
		iconTextItalian.add("Hotel");
		iconTextItalian.add("Apartments");
		iconTextItalian.add("Motels");
		iconTextItalian.add("Ostelli");
		iconTextItalian.add("Camere");
		iconTextItalian.add("Camps");
		iconTextItalian.add("Noleggia Locale");
		iconTextItalian.add("Noleggia Lungo Termine");
		iconTextItalian.add("Autobus");
		iconTextItalian.add("Taxi");
		iconTextItalian.add("Noleggia Auto");
		iconTextItalian.add("Noleggia Scooter");
		iconTextItalian.add("Aereo Traffico");
		iconTextItalian.add("Treno");
		iconTextItalian.add("Noleggia Una…");
		iconTextItalian.add("Banche");
		iconTextItalian.add("ATM");
		iconTextItalian.add("Cambio");
		iconTextItalian.add("Ospedale");
		iconTextItalian.add("Farmacia");
		iconTextItalian.add("Cliniche");
		iconTextItalian.add("Caffè");
		iconTextItalian.add("Ristoranti");
		iconTextItalian.add("Fast Food");
		iconTextItalian.add("Clubs");
		iconTextItalian.add("Bar");
		iconTextItalian.add("Eventi");
		iconTextItalian.add("Wellness");
		iconTextItalian.add("Spa");
		iconTextItalian.add("Bellezza");
		iconTextItalian.add("Arc En Ciel");
		iconTextItalian.add("Directory Aziende");
		iconTextItalian.add("Ricerca");
		iconTextItalian.add("Ricerca Personalizzata");
		iconTextItalian.add("Nome Della Società");
		iconTextItalian.add("Indirizzo");
		iconTextItalian.add("Città");
		iconTextItalian.add("Zona");
		iconTextItalian.add("Settore");
		iconTextItalian.add("Attività");
		iconTextItalian.add("Stato");
		iconTextItalian.add("Lingua");
		iconTextItalian.add("Bene");
		iconTextItalian.add("Impostazioni");
		iconTextItalian.add("È necessario entrare in uno stato!");
		iconTextItalian.add("È necessario inserire una città!");
		iconTextItalian.add("È necessario inserire una lingua!");
		iconTextItalian.add("Inviare SMS");
		iconTextItalian.add("Si prega di un taxi, a questo indirizzo:");
		iconTextItalian.add("Vedi sulla mappa");
		iconTextItalian.add("Categorie");
		iconTextItalian.add("More...");
		iconTextItalian.add("Must 2 Do");
		iconTextItalian.add("Métro");
		iconTextItalian.add("Sport");
		iconTextItalian.add("Crociera");
		
		iconTextList.add(iconTextItalian);
		
	}
	
	
	private void inicIconTitleListEspanola(){
		List<String> iconTextEspanola = new ArrayList<String>();
		
		iconTextEspanola.add("Recomendaciones");
		iconTextEspanola.add("Agenda Cultural");
		iconTextEspanola.add("Zoom  de Ciudad");
		iconTextEspanola.add("Directorio de Empresas");
		iconTextEspanola.add("Comida y Bebida");
		iconTextEspanola.add("Vida Nocturna");
		iconTextEspanola.add("Shopping");
		iconTextEspanola.add("Excursión");
		iconTextEspanola.add("Alojamiento");
		iconTextEspanola.add("Wellness & Spa");
		iconTextEspanola.add("Mapas");
		iconTextEspanola.add("Calendario");
		iconTextEspanola.add("Teatro");
		iconTextEspanola.add("Conciertos");
		iconTextEspanola.add("Museos y Galerías");
		iconTextEspanola.add("Centros Culturales");
		iconTextEspanola.add("Libros");
		iconTextEspanola.add("Atracciones");
		iconTextEspanola.add("Inspiración");
		iconTextEspanola.add("Tráfico");
		iconTextEspanola.add("Dinero");
		iconTextEspanola.add("Medicina");
		iconTextEspanola.add("Gasolina");
		iconTextEspanola.add("Wi-Fi");
		iconTextEspanola.add("Hoteles");
		iconTextEspanola.add("Apartamentos");
		iconTextEspanola.add("Moteles");
		iconTextEspanola.add("Hostales");
		iconTextEspanola.add("Habitaciones");
		iconTextEspanola.add("Campamentos");
		iconTextEspanola.add("Alquiler de Locales");
		iconTextEspanola.add("Alquiler a Largo Plazo");
		iconTextEspanola.add("Autobuses");
		iconTextEspanola.add("Taxis");
		iconTextEspanola.add("Alquiler de Coches");
		iconTextEspanola.add("Alquiler de Motos");
		iconTextEspanola.add("Tráfico Aéreo");
		iconTextEspanola.add("Trenes");
		iconTextEspanola.add("Alquiler de ...");
		iconTextEspanola.add("Bancos");
		iconTextEspanola.add("Cajeros Automáticos");
		iconTextEspanola.add("Casas de Cambio");
		iconTextEspanola.add("Hospitales");
		iconTextEspanola.add("Farmacias");
		iconTextEspanola.add("Clínicas");
		iconTextEspanola.add("Cafés");
		iconTextEspanola.add("Restaurantes");
		iconTextEspanola.add("Comida Rápida");
		iconTextEspanola.add("Clubs");
		iconTextEspanola.add("Bares");
		iconTextEspanola.add("Eventos");
		iconTextEspanola.add("Wellness");
		iconTextEspanola.add("Spa");
		iconTextEspanola.add("Belleza");
		iconTextEspanola.add("Rainbow");
		iconTextEspanola.add("Directorio de Empresas");
		iconTextEspanola.add("Buscar");
		iconTextEspanola.add("Búsqueda Personalizada");
		iconTextEspanola.add("Nombre de Compañía");
		iconTextEspanola.add("Dirección");
		iconTextEspanola.add("Ciudad");
		iconTextEspanola.add("Zona");
		iconTextEspanola.add("Sector");
		iconTextEspanola.add("Actividades");
		iconTextEspanola.add("Estado");
		iconTextEspanola.add("Idioma");
		iconTextEspanola.add("Bien");
		iconTextEspanola.add("Configuración");
		iconTextEspanola.add("Debe introducir un estado!");
		iconTextEspanola.add("Debe introducir una ciudad!");
		iconTextEspanola.add("Debe introducir un idioma!");
		iconTextEspanola.add("Enviar SMS");
		iconTextEspanola.add("Por favor, un taxi a esta dirección:");
		iconTextEspanola.add("Ver en el mapa");
		iconTextEspanola.add("Categorías");
		iconTextEspanola.add("More...");
		iconTextEspanola.add("Must 2 Do");
		iconTextEspanola.add("Metro");
		iconTextEspanola.add("Deporte");
		iconTextEspanola.add("Crucero");
		
		iconTextList.add(iconTextEspanola);
		
	}
	
	
	private void inicIconTitleListRuski(){
		List<String> iconTextRuski = new ArrayList<String>();
		
		iconTextRuski.add("Должны сделать");
		iconTextRuski.add("Kультура");
		iconTextRuski.add("Город Увеличить");
		iconTextRuski.add("Бизнес-справочник");
		iconTextRuski.add("Пейте & Питание");
		iconTextRuski.add("Ночная жизнь");
		iconTextRuski.add("Покупка товаров");
		iconTextRuski.add("Экскурсия");
		iconTextRuski.add("Жилье");
		iconTextRuski.add("Wellness & Spa");
		iconTextRuski.add("Карты");
		iconTextRuski.add("Календарь");
		iconTextRuski.add("Театр");
		iconTextRuski.add("Концерты");
		iconTextRuski.add("Музей и галерея");
		iconTextRuski.add("Культурный центр");
		iconTextRuski.add("Книги");
		iconTextRuski.add("Достопримечательности");
		iconTextRuski.add("Вдохновляют меня");
		iconTextRuski.add("Трафик");
		iconTextRuski.add("Деньги");
		iconTextRuski.add("Медицина");
		iconTextRuski.add("Бензин");
		iconTextRuski.add("Wi-Fi");
		iconTextRuski.add("Гостиницы");
		iconTextRuski.add("Апартаменты");
		iconTextRuski.add("Санатории");
		iconTextRuski.add("Хостелы");
		iconTextRuski.add("Номера");
		iconTextRuski.add("Лагеря");
		iconTextRuski.add("Аренда Часовой");
		iconTextRuski.add("Долгосрочная аренда");
		iconTextRuski.add("Автобус");
		iconTextRuski.add("Такси");
		iconTextRuski.add("Rent a car");
		iconTextRuski.add("Аренда мопедов");
		iconTextRuski.add("Воздушного движения");
		iconTextRuski.add("Поезд");
		iconTextRuski.add("Аренда ...");
		iconTextRuski.add("Банки");
		iconTextRuski.add("в банкоматах");
		iconTextRuski.add("Обменный пункт");
		iconTextRuski.add("Больница");
		iconTextRuski.add("Аптека");
		iconTextRuski.add("Клиники");
		iconTextRuski.add("Кафе");
		iconTextRuski.add("Рестораны");
		iconTextRuski.add("Фастфуд");
		iconTextRuski.add("Клубы");
		iconTextRuski.add("Бары");
		iconTextRuski.add("События");
		iconTextRuski.add("Wellness");
		iconTextRuski.add("Spa");
		iconTextRuski.add("Красота");
		iconTextRuski.add("Rainbow");
		iconTextRuski.add("Бизнес-справочник");
		iconTextRuski.add("Поиск");
		iconTextRuski.add("Custom Search");
		iconTextRuski.add("Название компании");
		iconTextRuski.add("Адрес");
		iconTextRuski.add("Сити");
		iconTextRuski.add("Зона");
		iconTextRuski.add("Деятельность сектора");
		iconTextRuski.add("деятельность");
		iconTextRuski.add("Государство");
		iconTextRuski.add("язык");
		iconTextRuski.add("Хорошо");
		iconTextRuski.add("Настройки");
		iconTextRuski.add("Вы должны войти в состояние!");
		iconTextRuski.add("Вы должны ввести город!");
		iconTextRuski.add("Вы должны ввести язык!");
		iconTextRuski.add("Отправить SMS");
		iconTextRuski.add("Пожалуйста одно такси на этот адрес:");
		iconTextRuski.add("Посмотреть на карте");
		iconTextRuski.add("Категории");
		iconTextRuski.add("More...");
		iconTextRuski.add("Must 2 Do");
		iconTextRuski.add("Mетро");
		iconTextRuski.add("Спорт");
		iconTextRuski.add("Kрейсерский");
		
		iconTextList.add(iconTextRuski);
		
	}
	
}
