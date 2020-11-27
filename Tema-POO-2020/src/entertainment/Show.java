package entertainment;

import java.util.ArrayList;

public class Show {
    private final String title;
    private final int year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;

    public Show(final String title, final int year,
                final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }


    /**
     * Verifica daca show-ul a aparut in anul dat ca parametru
     * @param year anul verificat
     * @return true daca a aparut in anul respectiv, false in caz contrar
     */
    public boolean hasYear(final int year) {
        return this.year == year;
    }

    /**
     * Verifica daca show-ul contine un anumit gen
     * @param genre genul show-ului
     * @return true daca show-ul este de genul respectiv, false in caz contrar
     */
    public boolean hasGenre(final String genre) {
        for (String s : genres) {
            if (s.equals(genre)) {
                return true;
            }
        }
        return false;
    }
}
