package action;

import database.Database;
import entertainment.Movie;

import java.util.Map;

/**
 * Query-uri pentru filme
 */
public final class QueryMovie {

    private QueryMovie() {
    }

    /**
     * Rating Query:
     * Cauta maxim n filme sortate. Sortarea lor se face dupa media
     * rating-urilor, acestea fiind salvate mai intai in averageRatingMovies din Database.
     * Se parcurge HashMap-ul sortat si se verifica daca filmul indeplineste criteriile de
     * cautare.
     *
     * @param data obiect de tip Database
     * @param sortType tipul sortarii
     * @param year criteriu pentru anul aparitie
     * @param genre criteriu pentru genul filmului
     * @param n numarul maxim de filme cautate
     * @return filmele sortate ce respecta cele doua criterii
     */
    public static String queryRatingMovies(final Database data, final String sortType,
                                           final int year, final String genre, final int n) {
        int rem = n; // salvez n
        StringBuilder str = new StringBuilder("Query result: [");
        Map<String, Double> sorted;
        // sortez
        if (sortType.equals("desc")) {
            sorted = Sorting.sortDesc(data.getAverageRatingMovies());
        } else {
            sorted = Sorting.sortAsc(data.getAverageRatingMovies());
        }
        // caut filme
        for (Map.Entry<String, Double> it : sorted.entrySet()) {
            for (Movie x : data.getMovie()) {
                if (x.getTitle().equals(it.getKey())) {
                    // verific daca respecta criteriile
                    if ((x.hasYear(year) || year == 0) && (x.hasGenre(genre) || genre == null)) {
                        str.append(x.getTitle());
                        str.append(", ");
                        rem--;
                        // verific daca am gasit n filme
                        if (rem == 0) {
                            str = new StringBuilder(str.substring(0, str.length() - 2));
                            return str.toString();
                        }
                    }
                }
            }
        }
        if (n != rem) {
            str = new StringBuilder(str.substring(0, str.length() - 2));
        }
        str.append("]");
        return str.toString();

    }

    /**
     * Favourite Query:
     * Cauta maxim n cele mai favorite filme ale utilizatorilor. Filmele sunt sortate dupa tipul
     * dat ca si parametru. Dupa sortare filmele sunt parcurse, verificand daca acestea respecta
     * criteriile de cautare
     *
     * @param data obiect de tip Database
     * @param sortType tipul sortarii
     * @param year criteriu an
     * @param genre criteriu gen
     * @param n numarul maxim de filme cautate
     * @return filmele favorite sortate ce respecta criteriile
     */
    public static String queryFavoriteMovies(final Database data, final String sortType,
                                             final int year, final String genre, final int n) {
        StringBuilder str = new StringBuilder("Query result: [");
        int rem = n; // salvez n
        Map<String, Double> map;
        // sortez
        if (sortType.equals("desc")) {
            map = Sorting.sortDesc(data.getFavoriteMoviesOfUsers());
        } else {
            map = Sorting.sortAsc(data.getFavoriteMoviesOfUsers());
        }
        // caut film
        for (Map.Entry<String, Double> it : map.entrySet()) {
            for (Movie x : data.getMovie()) {
                if (x.getTitle().equals(it.getKey())) {
                    // verific criteriile
                    if ((x.hasYear(year) || year == 0) && (x.hasGenre(genre) || genre == null)) {
                        str.append(x.getTitle());
                        str.append(", ");
                        rem--;
                        // verific daca am gasit n filme
                        if (rem == 0) {
                            str = new StringBuilder(str.substring(0, str.length() - 2));
                            return str.toString();
                        }
                    }
                }
            }
        }
        str = new StringBuilder(str.substring(0, str.length() - 2));
        str.append("]");
        return str.toString();
    }

    /**
     * Longest Query:
     * Cauta maxim n cele mai lungi filme. Filmele si durata lor sunt
     * salvate in longestMovie. Sortarea se face in functie de tipul ei, ascendent sau descendent.
     * Dupa sortare sunt parcurse filmele si se verifica daca acestea indeplinesc criteriile de
     * cautare.
     *
     * @param data obiect de tip Database
     * @param sortType tipul sortarii
     * @param year criteriu pentru an
     * @param genre criteriu pentru gen
     * @param n numarul maxim de filme cautate
     * @return filmele sortate ce respacte criteriile
     */
    public static String queryLongestMovie(final Database data, final String sortType,
                                           final int year, final String genre, final int n) {
        StringBuilder str = new StringBuilder("Query result: [");
        int rem = n; // salvez n
        Map<String, Double> map;
        // sortez
        if (sortType.equals("desc")) {
            map = Sorting.sortDesc(data.getLongestMovies());
        } else {
            map = Sorting.sortAsc(data.getLongestMovies());
        }
        // caut filme
        for (Map.Entry<String, Double> it : map.entrySet()) {
            for (Movie x : data.getMovie()) {
                if (x.getTitle().equals(it.getKey())) {
                    // verific criteriile
                    if ((x.hasYear(year) || year == 0) && (x.hasGenre(genre) || genre == null)) {
                        str.append(x.getTitle());
                        str.append(", ");
                        rem--;
                        // am gasit n seriale
                        if (rem == 0) {
                            str = new StringBuilder(str.substring(0, str.length() - 2));
                            str.append("]");
                            return str.toString();
                        }
                    }
                }
            }
        }
        if (n != rem) {
            str = new StringBuilder(str.substring(0, str.length() - 2));
        }
        str.append("]");
        return str.toString();
    }

    /**
     * Most Viewd Query:
     * Cauta maxim n cele mai vizionate filme sortate. Toate filmele si numarul lor total de
     * vizionari sunt salvate in viewedMovies din Database. Sortarea este dupa tipul data ca si
     * parametru.
     * Dupa sortare sunt parcurse filmele, verificand daca sunt respectate criteriile de cautare.
     *
     * @param data obiect de tip Database
     * @param sortType tipul sortarii, ascendent sau descendent
     * @param year criteriu  an
     * @param genre criteriu gen
     * @param n numarul maxim de filme cautate
     * @return filmele cele mai vizionate sortate si care indeplinesc criteriile de cautare
     */
    public static String queryViewedMovies(final Database data, final String sortType,
                                           final int year, final String genre, final int n) {
        String str;
        int rem = n; // salvez n
        Map<String, Double> map;
        // sortez
        if (sortType.equals("desc")) {
            map = Sorting.sortDesc(data.getViewedMovies());
        } else {
            map = Sorting.sortAsc(data.getViewedMovies());
        }
        StringBuilder strBuilder = new StringBuilder("Query result: [");
        // caut film
        for (Map.Entry<String, Double> it : map.entrySet()) {
            for (Movie x : data.getMovie()) {
                if (x.getTitle().equals(it.getKey())) {
                    // verific daca filmul indeplineste criteriile
                    if ((x.hasYear(year) || year == 0) && (x.hasGenre(genre) || genre == null)) {
                        strBuilder.append(x.getTitle());
                        strBuilder.append(", ");
                        rem--;
                        if (rem == 0) {
                            strBuilder = new StringBuilder(strBuilder.substring(0,
                                    strBuilder.length() - 2));
                            strBuilder.append("]");
                            return strBuilder.toString();
                        }
                    }
                }
            }
        }
        str = strBuilder.toString();
        if (n != rem) {
            str = str.substring(0, str.length() - 2);
        }
        str += "]";
        return str;
    }
}
