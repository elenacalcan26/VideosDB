package database;

import action.Sorting;
import actor.Actor;
import entertainment.Movie;
import entertainment.Serial;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Database {
    private final ArrayList<Actor> actor;
    private final ArrayList<User> user;
    private final ArrayList<Movie> movie;
    private final ArrayList<Serial> serial;
    /**
     * cele mai favortite filme ale utilizatorilor
     */
    private final HashMap<String, Double> favoriteMoviesOfUsers;

    /**
     * cele mai favorite seriale ale utilizatorilor
     */
    private final HashMap<String, Double> favoriteSerialsOfUsers;
    /**
     * cele mai lungi filme
     */
    private final HashMap<String, Double> longestMovies;
    /**
     * cele mai lungi seriale
     */
    private final HashMap<String, Double> longestSerial;
    /**
     * cele mai vizionate filme
     */
    private final HashMap<String, Double> viewedMovies;
    /**
     * cele mai vizionate seriale
     */
    private final HashMap<String, Double> viewedSerials;
    /**
     * rating-urile medii ale serialelor
     */
    private final HashMap<String, Double> averageRatingSerials;
    /**
     * rating-urile medii ale filmelor
     */
    private final HashMap<String, Double> averageRatingMovies;
    /**
     * toate show-urile cu rating-urile medii
     */
    private final HashMap<String, Double> totalShowsRating;
    /**
     * cei mai activi utilizatori
     */
    private final HashMap<String, Double> activeUser;
    /**
     * cei mai premiati actori
     */
    private final HashMap<String, Double> awardedActors;
    /**
     * cele mai populare genuri
     */
    private final HashMap<String, Double> popularGenres;
    /**
     * cele mai favorite show-uri ale utilizatorilor
     */
    private final HashMap<String, Double> mostFavouriteShows;
    /**
     * actorii cu rating-urile show-urilor in care a jucat
     */
    private final HashMap<String, Double> ratingActor;

    public Database() {
        actor = new ArrayList<>();
        user = new ArrayList<>();
        movie = new ArrayList<>();
        serial = new ArrayList<>();
        favoriteMoviesOfUsers = new HashMap<>();
        favoriteSerialsOfUsers = new HashMap<>();
        longestMovies = new HashMap<>();
        longestSerial = new HashMap<>();
        viewedMovies = new HashMap<>();
        viewedSerials = new HashMap<>();
        averageRatingSerials = new HashMap<>();
        averageRatingMovies = new HashMap<>();
        activeUser = new HashMap<>();
        awardedActors = new HashMap<>();
        totalShowsRating = new HashMap<>();
        popularGenres = new HashMap<>();
        mostFavouriteShows = new HashMap<>();
        ratingActor = new HashMap<>();
    }

    /**
     *  salveaza in baza de date show-urile favorite ale utilizatorilor
     */
    public void setMostFavouriteShows() {
        for (User u : user) {
            ArrayList<String> fav = u.getFavoriteMovies();
            for (String s : fav) {
                if (mostFavouriteShows.containsKey(s)) {
                    mostFavouriteShows.put(s, mostFavouriteShows.get(s) + 1.0);
                } else {
                    mostFavouriteShows.put(s, 1.0);
                }
            }
        }
    }

    /**
     *  salveaza serialele si rating-urile lor in database
     */
    public void setAverageRatingSerials() {
        for (Serial value : serial) {
            value.setAverageRating();
            if (value.getAverageRating() != 0.0) {
                averageRatingSerials.put(value.getTitle(),
                        value.getAverageRating());
            }
        }
    }

    /**
     *  salveaza filmele si rating-urile
     */
    public void setAverageRatingMovies() {
        for (Movie value : movie) {
            value.setAverageRating();
            if (value.getAverageRating() != 0.0) {
                averageRatingMovies.put(value.getTitle(),
                        value.getAverageRating());
            }
        }
    }

    /**
     *  salveaza cele mai vizionate filme
     */
    public void setViewedMovies() {
        for (Movie m : movie) {
            for (User u : user) {
                if (u.getHistory().containsKey(m.getTitle())) {
                    if (viewedMovies.containsKey(m.getTitle())) {
                        viewedMovies.put(m.getTitle(), viewedMovies.get(m.getTitle())
                                + Double.valueOf(u.getHistory().get(m.getTitle())));
                    } else {
                        viewedMovies.put(m.getTitle(),
                                Double.valueOf(u.getHistory().get(m.getTitle())));
                    }
                }
            }
        }
    }

    /**
     *  salveaza cele mai vizionate seriale
     */
    public void setViewedSerials() {
        for (Serial s : serial) {
            for (User u : user) {
                if (u.getHistory().containsKey(s.getTitle())) {
                    if (viewedSerials.containsKey(s.getTitle())) {
                        viewedSerials.put(s.getTitle(), viewedSerials.get(s.getTitle())
                                + Double.valueOf(u.getHistory().get(s.getTitle())));
                    } else {
                        viewedSerials.put(s.getTitle(),
                                Double.valueOf(u.getHistory().get(s.getTitle())));
                    }
                }
            }
        }
    }

    /**
     *  salveaza cele mai favorite filme ale utilizatorilor
     */
    public void setFavoriteMoviesOfUsers() {
        // parcurg filmele din data base
        for (int i = 0; i < movie.size(); i++) {
            // parcurg userii din data base
            for (User value : user) {
                // extrag lista de filme favorite ale user-ului
                if (value.getFavoriteMovies() != null) {
                    List<String> fav = value.getFavoriteMovies();
                    for (String x : fav) {
                        if (favoriteMoviesOfUsers.containsKey(x)) {
                            favoriteMoviesOfUsers.put(x, favoriteMoviesOfUsers.get(x) + 1.0);
                        } else {
                            favoriteMoviesOfUsers.put(x, 1.0);
                        }
                    }
                }
            }
        }
    }

    /**
     *  salveaza cele mai favorite seriale ale utilizatorilor
     */
    public void setFavoriteSerialsOfUsers() {
        for (int i = 0; i < serial.size(); i++) {
            for (User value : user) {
                if (value.getFavoriteMovies() != null) {
                    List<String> fav = value.getFavoriteMovies();
                    for (String x : fav) {
                        if (favoriteSerialsOfUsers.containsKey(x)) {
                            favoriteSerialsOfUsers.put(x, favoriteSerialsOfUsers.get(x) + 1.0);
                        } else {
                            favoriteSerialsOfUsers.put(x, 1.0);
                        }
                    }
                }
            }
        }
    }

    /**
     *  salveaza cei mai activi utilizatori
     */
    public void setActiveUser() {
        for (User x : user) {
            if (x.getRatedMovies().size() != 0) {
                if (activeUser.containsKey(x.getUsername())) {
                    activeUser.put(x.getUsername(), activeUser.get(x.getUsername())
                            + (double) x.getRatedMovies().size());
                } else {
                    activeUser.put(x.getUsername(), (double) x.getRatedMovies().size());
                }
            }
            if (x.getRatedSeries().size() != 0) {
                for (Map.Entry<String, ArrayList<Integer>> it : x.getRatedSeries().entrySet()) {
                    if (activeUser.containsKey(x.getUsername())) {
                        activeUser.put(x.getUsername(), activeUser.get(x.getUsername())
                                + (double) it.getValue().size());
                    } else {
                        activeUser.put(x.getUsername(), (double) it.getValue().size());
                    }
                }
            }
        }
    }

    /**
     *
     * @param list lista cu seriale din input
     */
    public void setSerial(final List<SerialInputData> list) {
        for (SerialInputData serialInputData : list) {
            this.serial.add(new Serial(serialInputData.getTitle(), serialInputData.getCast(),
                    serialInputData.getGenres(), serialInputData.getNumberSeason(),
                    serialInputData.getSeasons(), serialInputData.getYear()));
        }
    }

    /**
     *  salveaza actorii cu suma premiilor date din lista
     * @param awards lista cu premii
     */
    public void setAwardedActors(final List<String> awards) {
        for (Actor x : actor) {
            if (x.hasAwards(awards)) {
                awardedActors.put(x.getName(), (double) x.totalAwards(awards));
            }
        }
    }

    /**
     *
     * @param list lista cu actorii din input
     */
    public void setActor(final List<ActorInputData> list) {
        for (ActorInputData actorInputData : list) {
            this.actor.add(new Actor(actorInputData.getName(),
                    actorInputData.getCareerDescription(), actorInputData.getFilmography(),
                    actorInputData.getAwards()));
        }
    }

    /**
     *  salveaza toate show-urile si rating-urile lor
     */
    public void setTotalShowsRating() {
        for (Map.Entry<String, Double> it : averageRatingMovies.entrySet()) {
            totalShowsRating.put(it.getKey(), it.getValue());
        }
        Map<String, Double> sortedSerials = Sorting.sortAsc(averageRatingSerials);
        for (Map.Entry<String, Double> it : sortedSerials.entrySet()) {
            totalShowsRating.put(it.getKey(), it.getValue());
        }
    }

    /**
     *  salveaza cele mai populare genuri vazute de catre utilizatori
     */
    public void setPopularGenres() {
        for (User u : user) {
            for (Map.Entry<String, Integer> it : u.getHistory().entrySet()) {
                if (isMvoie(it.getKey())) {
                    Movie m = extractMovie(it.getKey());
                    if (m != null) {
                        for (int i = 0; i < m.getGenres().size(); i++) {
                            if (popularGenres.containsKey(m.getGenres().get(i))) {
                                popularGenres.put(m.getGenres().get(i),
                                        popularGenres.get(m.getGenres().get(i))
                                                + Double.valueOf(u.getHistory().get(m.getTitle())));
                            } else {
                                popularGenres.put(m.getGenres().get(i),
                                        Double.valueOf(u.getHistory().get(m.getTitle())));
                            }
                        }
                    }
                } else {
                    Serial s = extractSerial(it.getKey());
                    for (int i = 0; i < (s != null ? s.getGenres().size() : 0); i++) {
                        if (popularGenres.containsKey(s.getGenres().get(i))) {
                            popularGenres.put(s.getGenres().get(i),
                                    popularGenres.get(s.getGenres().get(i))
                                            + Double.valueOf(u.getHistory().get(s.getTitle())));
                        } else {
                            popularGenres.put(s.getGenres().get(i),
                                    Double.valueOf(u.getHistory().get(s.getTitle())));
                        }
                    }
                }
            }
        }
    }

    /**
     *  salveaza serialele si durata lor
     * @param list lista de seriale din input
     */
    public void setLongestSerial(final List<SerialInputData> list) {
        for (SerialInputData serialInputData : list) {
            for (int j = 0; j < serialInputData.getSeasons().size(); j++) {
                if (longestSerial.containsKey(serialInputData.getTitle())) {
                    longestSerial.put(serialInputData.getTitle(),
                            longestSerial.get(serialInputData.getTitle())
                                    + (double) serialInputData.getSeasons().get(j).getDuration());
                } else {
                    longestSerial.put(serialInputData.getTitle(),
                            (double) serialInputData.getSeasons().get(j).getDuration());
                }
            }
        }
    }

    /**
     *
     * @param list lista cu utilizatori din input
     */
    public void setUser(final List<UserInputData> list) {
        for (UserInputData userInputData : list) {
            this.user.add(new User(userInputData.getUsername(), userInputData.getSubscriptionType(),
                    userInputData.getHistory(), userInputData.getFavoriteMovies()));
        }
    }

    /**
     *
     * @param list lista cu filme din input
     */
    public void setMovie(final List<MovieInputData> list) {
        for (MovieInputData movieInputData : list) {
            this.movie.add(new Movie(movieInputData.getTitle(), movieInputData.getCast(),
                    movieInputData.getGenres(), movieInputData.getYear(),
                    movieInputData.getDuration()));
        }
    }

    /**
     *  salveaza filmele si durata lor
     * @param list lista cu filme din input
     */
    public void setLongestMovies(final List<MovieInputData> list) {
        for (MovieInputData movieInputData : list) {
            this.longestMovies.put(movieInputData.getTitle(),
                    (double) movieInputData.getDuration());
        }
    }

    /**
     *  salveaza actorii cu rating-urile show-urilo in care au jucat
     */
    public void setRatingActor() {
        for (Actor x : actor) {
            int cnt = 0;
            Double sumRatings = 0.0;
            for (Map.Entry<String, Double> it : averageRatingMovies.entrySet()) {
                Movie m = extractMovie(it.getKey());
                if (m != null && m.getCast().contains(x.getName())) {
                    sumRatings += it.getValue();
                    cnt++;
                }
            }
            for (Map.Entry<String, Double> it : averageRatingSerials.entrySet()) {
                Serial s = extractSerial(it.getKey());
                if (s != null && s.getCast().contains(x.getName())) {
                    sumRatings += it.getValue();
                    cnt++;
                }
            }
            if (cnt != 0 && sumRatings != 0.0) {
                ratingActor.put(x.getName(), sumRatings / cnt);
            }
        }
    }

    public ArrayList<Actor> getActor() {
        return actor;
    }

    public ArrayList<User> getUser() {
        return user;
    }

    public ArrayList<Movie> getMovie() {
        return movie;
    }

    public ArrayList<Serial> getSerial() {
        return serial;
    }

    public HashMap<String, Double> getFavoriteMoviesOfUsers() {
        return favoriteMoviesOfUsers;
    }

    public HashMap<String, Double> getLongestMovies() {
        return longestMovies;
    }

    public HashMap<String, Double> getViewedMovies() {
        return viewedMovies;
    }

    public HashMap<String, Double> getFavoriteSerialsOfUsers() {
        return favoriteSerialsOfUsers;
    }

    public HashMap<String, Double> getLongestSerial() {
        return longestSerial;
    }

    public HashMap<String, Double> getViewedSerials() {
        return viewedSerials;
    }

    public HashMap<String, Double> getAverageRatingSerials() {
        return averageRatingSerials;
    }

    public HashMap<String, Double> getActiveUser() {
        return activeUser;
    }

    public HashMap<String, Double> getAverageRatingMovies() {
        return averageRatingMovies;
    }

    public HashMap<String, Double> getAwardedActors() {
        return awardedActors;
    }

    public HashMap<String, Double> getTotalShowsRating() {
        return totalShowsRating;
    }

    public HashMap<String, Double> getPopularGenres() {
        return popularGenres;
    }

    public HashMap<String, Double> getMostFavouriteShows() {
        return mostFavouriteShows;
    }

    public HashMap<String, Double> getRatingActor() {
        return ratingActor;
    }

    /**
     *  cauta un utilizatorin baza de date
     * @param name numele utilizatorului
     * @return utilizatorul din baza de date, null in caz contrar
     */
    public User extractUser(final String name) {
        for (User x : user) {
            if (x.getUsername().equals(name)) {
                return x;
            }
        }
        return null;
    }

    /**
     *  cauta un film in baza de date
     * @param title numele filmului
     * @return filmul din baza de date, null in caz contrar
     */
    public Movie extractMovie(final String title) {
        for (Movie x : movie) {
            if (x.getTitle().equals(title)) {
                return x;
            }
        }
        return null;
    }

    /**
     *  cauta un serial din baza de date
     * @param title numele serialului
     * @return serialul din baza de date, null in caz contrar
     */
    public Serial extractSerial(final String title) {
        for (Serial x : serial) {
            if (x.getTitle().equals(title)) {
                return x;
            }
        }
        return null;
    }

    /**
     * verifica daca video-ul este film sau nu
     * @param movie video-ul ppe care vrem sa il cautam
     * @return true daca se film, false in caz contrar
     */
    public boolean isMvoie(final String movie) {
        for (Movie x : this.movie) {
            if (movie.equals(x.getTitle())) {
                return true;
            }
        }
        return false;
    }
}
