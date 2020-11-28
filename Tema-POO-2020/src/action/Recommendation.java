package action;

import database.Database;
import entertainment.Movie;
import entertainment.Serial;
import user.User;

import java.util.HashMap;
import java.util.Map;

public final class Recommendation {

    private Recommendation() {
    }

    /**
     * Popular Recommendation:
     * Cauta un show ce contine un gen popular pe care utilizatorul nu l-a vazut. Se parcurg
     * genurile de la cel mai poular. Pentru fiecare gen sunt parcurse filmele si serilele pana
     * cand se gaseste show-ul pe care nu l-a vazut utilizatorul.
     *
     * @param data obiect de tip Database
     * @param user utilizatorul caruia i se aplica recomandarea
     * @return primul show recomandat
     */
    public static String popularRecommendation(final Database data, final User user) {
        if (user.getSubscriptionType().equals("BASIC")) {
            // nu i se aplica recomandarea
            return "PopularRecommendation cannot be applied!";
        }
        String str = "PopularRecommendation result: ";
        Map<String, Double> sorted = Sorting.sortDesc(data.getPopularGenres());
        // parcurg genurile
        for (Map.Entry<String, Double> it : sorted.entrySet()) {
            // parcurg filmele din Database
            for (Movie m : data.getMovie()) {
                // verific daca filmul contine genul popular si este nevazut de utilizator
                if (m.getGenres().contains(it.getKey())
                        && !user.getHistory().containsKey(m.getTitle())) {
                    str += m.getTitle();
                    return str;
                }
            }
            // percurg serialele din baza de date
            for (Serial s : data.getSerial()) {
                if (s.getGenres().contains(it.getKey())
                        && !user.getHistory().containsKey(s.getTitle())) {
                    str += s.getTitle();
                    return str;
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }

    /**
     * Favourite Recommendation:
     * Cauta un show care este cel mai favorit de catre utilizatori si care ii este recomandat
     * utilizatorului dat ca parametru. Daca utilizatorul a vizionat toate show-urile favorite
     * se cauta in baza de date primul show nevazut.
     *
     * @param data obiect de tip Database
     * @param user utilizatorul caruia i se face recomandarea
     * @return show-ul recomandat
     */
    public static String favouriteRecommendation(final Database data, final User user) {
        if (user.getSubscriptionType().equals("BASIC")) {
            // nu i se aplica recomandarea
            return "FavoriteRecommendation cannot be applied!";
        }
        String str = "FavoriteRecommendation result: ";
        Map<String, Double> sorted = Sorting.sortMapVal(data.getMostFavouriteShows());
        // se parcurg show-rile favorite
        for (Map.Entry<String, Double> it : sorted.entrySet()) {
            // verific daca show-ul a fost vazut
            if (!user.getHistory().containsKey(it.getKey())) {
                str += it.getKey();
                return str;
            }
        }
        // percurg filmele
        for (Movie m : data.getMovie()) {
            // verific daca a fost vazut
            if (!user.getHistory().containsKey(m.getTitle())) {
                str += m.getTitle();
                return str;
            }
        }
        // parcurg serialele
        for (Serial s : data.getSerial()) {
            // verific daca a fost vazut
            if (!user.getHistory().containsKey(s.getTitle())) {
                str += s.getTitle();
                return str;
            }
        }
        return "FavoriteRecommendation cannot be applied!";
    }

    /**
     * Search Recommendation:
     * Cauta show-ul dintr-un anumit gen care i se recomanda utilizatorului. Toate filmele si
     * serialele din genul dorit sunt salvate intr-un hashmap ce retine numele show-ului si
     * rating-ul lui, show-urile fara rating sunt considrate cu rating 0.
     * Se parcurg show-urile pana cand se gaseste un show nevizionat de catre utilizator.
     *
     * @param data oiect de tip Database
     * @param user utilizatorul caruia i se face recomandarea
     * @param genre genul show-ului
     * @return show-ul recomandat
     */
    public static String searchRecommendation(final Database data, final User user,
                                              final String genre) {
        if (user.getSubscriptionType().equals("BASIC") || genre == null) {
            // nu se poate aplica recomandarea
            return "SearchRecommendation cannot be applied!";
        }
        HashMap<String, Double> filteredMovies = new HashMap<>(); // retine show-urile de gen
        Map<String, Double> sortedMovies = Sorting.sortAsc(data.getAverageRatingMovies());
        // parcurg filmele cu rating
        for (Map.Entry<String, Double> it : sortedMovies.entrySet()) {
            Movie m = data.extractMovie(it.getKey());
            if (m != null && m.getGenres().contains(genre)) {
                filteredMovies.put(m.getTitle(), it.getValue());
            }
        }
        Map<String, Double> sortedSerials = Sorting.sortAsc(data.getAverageRatingSerials());
        // parcurg serialele cu rating
        for (Map.Entry<String, Double> it : sortedSerials.entrySet()) {
            Serial s = data.extractSerial(it.getKey());
            if (s != null && s.getGenres().contains(genre)) {
                filteredMovies.put(s.getTitle(), it.getValue());
            }
        }
        // parcurg filmele din baza de date
        for (Movie x : data.getMovie()) {
            // adaug in array filmele care nu au rating si contin genul
            if (!sortedMovies.containsKey(x.getTitle())
                    && x.getGenres().contains(genre)) {
                filteredMovies.put(x.getTitle(), 0.0);
            }
        }
        for (Serial it : data.getSerial()) {
            if (!sortedSerials.containsKey(it.getTitle())
                    && it.getGenres().contains(genre)) {
                filteredMovies.put(it.getTitle(), 0.0);
            }
        }
        if (filteredMovies.isEmpty()) {
            // nu s-au gasit show-uri de genul respectiv
            return "SearchRecommendation cannot be applied!";
        }
        Map<String, Double> map = Sorting.sortAsc(filteredMovies);
        String str = "SearchRecommendation result: [";
        int len = str.length();
        StringBuilder strBuilder = new StringBuilder(str);
        for (Map.Entry<String, Double> it : map.entrySet()) {
            if (!user.getHistory().containsKey(it.getKey())) {
                strBuilder.append(it.getKey());
                strBuilder.append(", ");
            }
        }
        str = strBuilder.toString();
        if (str.length() == len) {
            // utilizatorul a vazut toate show-urile
            return "SearchRecommendation cannot be applied!";
        }
        str = str.substring(0, str.length() - 2);
        str += "]";
        return str;
    }

    /**
     * Best Unseen Recommendation:
     * Cauta show-ul cu cel mai bun rating care este recomandat utilizatorului. Show-urile sunt
     * sortate dupa media rating-urilor. Daca utilizatorul a vizionat aceste show-uri se cauta
     * primul show nevizualizat din baza de date.
     *
     * @param data obiect de tip data base
     * @param user utilizatorul caruia i se face recomandarea
     * @return show-ul recomandat
     */
    public static String bestUnseenRecommendation(final Database data, final User user) {
        Map<String, Double> map = Sorting.sortDesc(data.getTotalShowsRating());
        for (Map.Entry<String, Double> it : map.entrySet()) {
            if (!user.getHistory().containsKey(it.getKey())) {
                return "BestRatedUnseenRecommendation result: " + it.getKey();
            }
        }
        for (Movie x : data.getMovie()) {
            if (!user.getHistory().containsKey(x.getTitle())) {
                return "BestRatedUnseenRecommendation result: " + x.getTitle();
            }
        }
        for (Serial x : data.getSerial()) {
            if (!user.getHistory().containsKey(x.getTitle())) {
                return "BestRatedUnseenRecommendation result: " + x.getTitle();
            }
        }
        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    /**
     * Standard Recommendation:
     * Cauta primul show-ul din Database care nu a fost vizualizat de catre utilizator.
     * @param data obiect de tip Database
     * @param user utilizatorul caruia i se face recomandarea
     * @return show-ul recomandat
     */
    public static String standardRecommendation(final Database data, final User user) {
        for (Movie x : data.getMovie()) {
            if (!user.getHistory().containsKey(x.getTitle())) {
                return "StandardRecommendation result: " + x.getTitle();
            }
        }
        for (Serial x : data.getSerial()) {
            if (!user.getHistory().containsKey(x.getTitle())) {
                return "StandardRecommendation result: " + x.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }
}
