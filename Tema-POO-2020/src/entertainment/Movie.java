package entertainment;

import java.util.ArrayList;

public final class Movie extends Show {
    private final int duration;
    /**
     * rating-urile filmului date de utilizatori
     */
    private final ArrayList<Double> ratings;
    /**
     * rating-ul mediu a filmului
     */
    private Double averageRating;

    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        ratings = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    /**
     * @param rating rating-ul filmului
     */
    public void setRatings(final double rating) {
        this.ratings.add(rating);
    }

    /**
     *  Calculeaza rating-ul mediu
     */
    public void setAverageRating() {
        Double avg = 0.0;
        if (!ratings.isEmpty()) {
            for (Double rating : ratings) {
                avg += rating;
            }
            averageRating = avg / ratings.size();
        } else {
            averageRating = 0.0;
        }
    }
}
