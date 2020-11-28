# VideosDB

Programul implementeaza o simulare a unor actiuni pe care le pot face 
utilizatorii pe o platforma de vizualizare de filme.

Pachetele folosite sunt:
	
	• database:
		
		‣ clasa Database 
			
			◦ retine toate informatiile depsre actori, show-uri, utilizatori

	• actor:

		‣ clasa Actor

			◦ retine informatii despre un actor	
			◦ implementeaza metoda containsWords: verifica daca descrierea
			actorului contine toate keyword-urile

	• entertainment:

		‣ clasa Show:

			◦ retine informatii generale despre un film sau serial

		‣ clasa Movie:

			◦ mosteneste clasa Show	
			◦ are un ArrayList ce retine toate rating-urile filmului date de 
			utilizatori
			◦ in setAverageRating este calculata rating-ul mediu a filmului

		‣ clasa Season:

			◦ retine informatii despre sezonul unui serial

		‣ clasa Serial:

			◦ mosteneste clasa Show
			◦ are un ArrayList de sezoane cu informatii despre sezoanele 
			serialului
			◦ in setAverageRating este calculata rating-ul mediu a filmului

	• user:

		‣ clasa User:

			◦ retine informatii despre un utiliator
			◦ contine metodele favorite, view, ratingMovie si ratingSerial 
			ce reprezinta comenzile pe care le poate da utilizatorul

	• action:

		-> contine implementari ale query-urilor si a recomandarilor
		-> toate actiunile se realizeaza prin cautari in Database

		‣ clasa Recommendation:

			◦ contine metodele popularRecommendation, favouriteRecommendation,
			searchRecommendation, bestUnseenRecommendation, standardRecommendation
			◦ metodele cauta in Database show-urile pe care un utilizator nu
			le-a vizionat
			◦ in timpul cautarilor se tine cont si de informatiile utilizatorului

		‣ clasa QueryActor:

			◦ contine metodele awardsActors, filterDescriptionQuery, averageQuery
			◦ metodele cauta in Database actorii, tinand cont si de informatiile lor

		‣ clasa QueryMovie:

			◦ contine metodele queryRatingMovies, queryFavoriteMovies, 
			queryLongestMovie, queryViewedMovies
			◦ metodele cauta in Database filme in functie de unele specificatii
			◦ in timpul cautarilor se tine cont si de informtiile filmului

		‣ clasa QueryShow:

			◦ contine metodele queryFavoriteSerials, queryLongestSerials,
			queryViewedSerial, queryRatingSerials
			◦ metodele cauta in Database seriale in functie de unele specificatii
			◦ in timpul cautarilor se tine cont de informatiile serialului

		‣ clasa QueryUser:

			◦ contine metoda queryActiveUser 
			◦ metoda cauta in Database utilizatorii in functie de activitatea lor

		‣ clasa Sorting:

			◦ sunt implementati comparatori pentru sortarea ascendenta si 
			descendenta a HashMap-urilor.

	• main:

		‣ clasa Main:

			◦ apeleaza metodele din pachetele database, user si action			

