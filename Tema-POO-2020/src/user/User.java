package user;

import entertainment.Movie;
import entertainment.Serial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class User {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;
    /**
     * filmele si rating-urile date de catre utilizator
     */
    private final Map<String, Double> ratedMovies;
    /**
     * serialele si sezoanele carora i-a dat utilizatorul rating
     */
    private final Map<String, ArrayList<Integer>> ratedSeries;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        ratedMovies = new HashMap<>();
        ratedSeries = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public Map<String, ArrayList<Integer>> getRatedSeries() {
        return ratedSeries;
    }

    public Map<String, Double> getRatedMovies() {
        return ratedMovies;
    }

    /**
     *  Executa comanada "favourite". Comanda adauga show-ul la favorite daca ea este vizionata de
     *  catre utilizator. Show-ul nu poate fi adaugat la favorite daca nu este vizionat de catre
     *  utiliator sau daca exista deja la favorite, rezultand un mesaj de eroare.
     * @param title numele filmului pe care vrem sa il adagam la favorite
     * @return mesajul aferent dupa aplicarea comenzii
     */
    public String favorite(final String title) {
        String str = "";
        if (history.containsKey(title)) {
            if (favoriteMovies.contains(title)) {
                str = str + "error -> " + title + " is already in favourite list";
            } else {
                favoriteMovies.add(title);
                str = str + "success -> " + title + " was added as favourite";
            }
        } else {
            str = str + "error -> " + title + " is not seen";
        }
        return str;
    }

    /**
     * Executa comanda "view". Show-ul este adaugat in istoric daca nu a mai fost vizionat de catre
     * utilizator si ii incrementeaza numarul de vizionari daca show-ul a fost vazut inainte
     *
     * @param show show-ul pe care il marchez ca vazut
     * @return mesajul aferent dupa actiune
     */
    public String view(final String show) {
        String str = "";
        if (history.containsKey(show)) {
            history.put(show, history.get(show) + 1);
        } else {
            history.put(show, 1);
        }
        str = str + "success -> " + show + " was viewed with total views of " + history.get(show);
        return str;
    }

    /**
     * Executa comanda "rating" pentru filme. Utilizatorul poate da un rating filmului daca acesta
     * a fost vizionat. Daca utilizatorul a dat inainte un rating filmului, se va afisa un mesaj de
     * eroare. Utilizatorul nu poate da rating filmului daca nu a fost vizionat.
     *
     * @param title numele filmului caruia utilizatorul vrea sa ii dearating
     * @param rating rating-ul utilizatorului
     * @param movie obiect de tip movie
     * @return mesajul aferent dupa aplicarea comenzii
     */
    public String ratingMovie(final String title, final double rating, final Movie movie) {
        String str = "";
        if (history.containsKey(title) && !ratedMovies.containsKey(title)) {
            ratedMovies.put(title, rating);
            movie.setRatings(rating);
            str = str + "success -> " + title + " was rated with " + rating + " by " + username;
        } else {
            // verific daca utilizatorul i-a pus un rating
            if (ratedMovies.containsKey(title)) {
                str = str + "error -> " + title + " has been already rated";
            } else {
                // filmul nu a fost vizionat
                str = str + "error -> " + title + " is not seen";
            }
        }
        return str;
    }

    /**
     *  Salveaza in hashmap sezonul caruia i-a dat utilizatorul rating
     *
     * @param key serialul
     * @param val sezonul caruia utilizatorul i-a dat rating
     */
    public synchronized void addToList(final String key, final Integer val) {
        ArrayList<Integer> list = ratedSeries.get(key);
        if (list == null) {
            list = new ArrayList<>();
            list.add(val);
            ratedSeries.put(key, list);
        } else {
            if (!list.contains(val)) {
                list.add(val);
            }
        }
    }

    /**
     *  Executa comanda "rating". Comanda se esxecuta daca utilizatorul nu a mai dat rating
     *  sezonului dat ca parametru. Comanda nu se executa daca utilizatorul nu a vizionat serialul
     *  sau daca a mai dat inainte un rating sezonului data ca parametru, rezultand eroare.
     *
     * @param title numele serialului
     * @param rating rating-ul
     * @param serial obiect de tip serial
     * @param season sezonul caruia utilizatorul ii da rating.
     * @return mesajul aferent
     */
    public String ratingSerial(final String title, final double rating, final Serial serial,
                               final int season) {
        String str = "";
        if (!history.containsKey(title)) {
            str = str + "error -> " + title + " is not seen";
        } else {
            if (!ratedSeries.containsKey(title)) {
                addToList(title, season);
                serial.getSeasons().get(season - 1).getRatings().add(rating);
                str = str + "success -> " + title + " was rated with " + rating + " by " + username;
            } else {
                if (ratedSeries.get(title).contains(season)) {
                    str = str + "error -> " + title + " has been already rated";
                } else {
                    addToList(title, season);
                    serial.getSeasons().get(season - 1).getRatings().add(rating);
                    str = str + "success -> " + title + " was rated with " + rating + " by "
                            + username;
                }
            }
        }
        return str;
    }
}
