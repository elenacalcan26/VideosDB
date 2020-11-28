package action;

import database.Database;
import entertainment.Serial;

import java.util.Map;

/**
 * Query pentru seriale
 */
public final  class QueryShow {
    private QueryShow() { }

    /**
     * Favourite Query:
     * Cauta maxim n seriale favorite ale utilizatorilor. Serialele sunt sortate dupa tipul dat ca
     * parametru. Serialele sunt parcurse, verificandu-se daca sunt respectet criteriile.
     *
     * @param data obiect de tip Database
     * @param sortType tipul
     * @param year criteriu an
     * @param genre criteriu gen
     * @param n numarul maxim de seriale cautate
     * @return serialele favorite sortate ce respecta criteriile
     */
    public static String queryFavoriteSerials(final Database data, final String sortType,
                                              final int year, final String genre, final int n) {
        String str;
        int rem = n; // salvez n
        Map<String, Double> map;
        // sortez
        if (sortType.equals("desc")) {
            map = Sorting.sortDesc(data.getFavoriteSerialsOfUsers());
        } else {
            map = Sorting.sortAsc(data.getFavoriteSerialsOfUsers());
        }
        StringBuilder strBuilder = new StringBuilder("Query result: [");
        // caut serialele
        for (Map.Entry<String, Double> it : map.entrySet()) {
            for (Serial x : data.getSerial()) {
                // verific criteriile
                if (x.getTitle().equals(it.getKey())) {
                    if ((x.hasYear(year) || year == 0) && (x.hasGenre(genre) || genre == null)) {
                        strBuilder.append(x.getTitle());
                        strBuilder.append(", ");
                        rem--;
                        if (rem == 0) {
                            strBuilder = new StringBuilder(strBuilder.substring(0,
                                    strBuilder.length() - 2));
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

    /**
     * Favourite Query:
     * Cauta maxim n cele mai lungi seriale sortate. Sortarea este in functie de tipul sortarii dat
     * ca parametru. Se parcurg seralele sortate si se verifica daca sunt respectate criteriile
     * de cautare
     * @param data obiect de tip Database
     * @param sortType tipul sortarii
     * @param year criteriu an
     * @param genre criteriu gen
     * @param n numarul maxim de seriale cautate
     * @return filmele sortate ce respecta criteriile
     */
    public static String queryLongestSerials(final Database data, final String sortType,
                                             final int year, final String genre, final int n) {
        String str;
        int rem = n; // salvez n
        Map<String, Double> map;
        // sortez
        if (sortType.equals("desc")) {
            map = Sorting.sortDesc(data.getLongestSerial());
        } else {
            map = Sorting.sortAsc(data.getLongestSerial());
        }
        StringBuilder strBuilder = new StringBuilder("Query result: [");
        // caut serialele
        for (Map.Entry<String, Double> it : map.entrySet()) {
            for (Serial x : data.getSerial()) {
                if (x.getTitle().equals(it.getKey())) {
                    // verific criteriile
                    if ((x.hasYear(year) || year == 0) && (x.hasGenre(genre) || genre == null)) {
                        strBuilder.append(x.getTitle());
                        strBuilder.append(", ");
                        rem--;
                        // am gasit n seriale
                        if (rem == 0) {
                            strBuilder = new StringBuilder(strBuilder.substring(0,
                                    strBuilder.length() - 2));
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

    /**
     *  Viewed Query:
     *  Cauta maxim n cele mai vizionate seriale de catre utilizatori. Sortarea este in functie de
     *  tipul sortarii dat ca parametru. Se parcurg serialele si se verifica daca acestea respecta
     *  criteriile de cautare.
     *
     * @param data obiect de tip Database
     * @param sortType tipul sortarii
     * @param year criteriu an
     * @param genre criteriu gen
     * @param n numarul maxim de seriale cautate
     * @return cele mai vizionate seriale sortate ce respecta criteriile
     */
    public static String queryViewedSerial(final Database data, final String sortType,
                                           final int year, final String genre, final int n) {
        StringBuilder str = new StringBuilder("Query result: [");
        int rem = n; // salvez n
        Map<String, Double> map;
        // sortez
        if (sortType.equals("desc")) {
            map = Sorting.sortDesc(data.getViewedSerials());
        } else {
            map = Sorting.sortAsc(data.getViewedSerials());
        }
        // caut serialele
        for (Map.Entry<String, Double> it : map.entrySet()) {
            for (Serial x : data.getSerial()) {
                if (x.getTitle().equals(it.getKey())) {
                    // verific criteriile
                    if ((x.hasYear(year) || year == 0) && (x.hasGenre(genre) || genre == null)) {
                        str.append(x.getTitle());
                        str.append(", ");
                        rem--;
                        // am gasit maxim n seriale
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
     * Rating Query:
     * Cauta serialele in functie de rating-ul mediu. Serialele sunt sortate in functie de tipul
     * sortarii dat ca parametru. Se parcurg serialele in ordinea sortarii si se verifica daca sunt
     * respectate criteriile de cautare.
     *
     * @param data obiect de tip Database
     * @param sortType tipul sortarii
     * @param year criteriu an
     * @param genre criteriu gen
     * @param n maxim n seriale cautate
     * @return serialele sortate ce verifica criteriile
     */
    public static String queryRatingSerials(final Database data, final String sortType,
                                            final int year, final String genre, final int n) {
        String str;
        int rem = n; // salvez n
        Map<String, Double> map;
        // sortez
        if (sortType.equals("desc")) {
            map = Sorting.sortDesc(data.getAverageRatingSerials());
        } else {
            map = Sorting.sortAsc(data.getAverageRatingSerials());
        }
        StringBuilder strBuilder = new StringBuilder("Query result: [");
        // caut serialele
        for (Map.Entry<String, Double> it : map.entrySet()) {
            for (Serial x : data.getSerial()) {
                if (x.getTitle().equals(it.getKey())) {
                    // verific criteriile de cautare
                    if ((x.hasYear(year) || year == 0) && (x.hasGenre(genre) || genre == null)) {
                        strBuilder.append(x.getTitle());
                        strBuilder.append(", ");
                        rem--;
                        // am gasit maxim n seriale
                        if (rem == 0) {
                            strBuilder = new StringBuilder(strBuilder.substring(0,
                                    strBuilder.length() - 2));
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

