package entertainment;

import java.util.ArrayList;

public final class Serial extends Show {
    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;
    /**
     * rating-ul mediu
     */
    private Double averageRating;

    public Serial(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Season> seasons,
                  final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.averageRating = 0.0;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    /**
     * Calculeaza rating-ul mediu
     */
    public void setAverageRating() {
        double avg = 0.0;
        // parcurg sezoanele
        for (Season season : seasons) {
            // parcurg ratingurile sezoanelor
            Double sum = 0.0;
            for (int j = 0; j < season.getRatings().size(); j++) {
                sum += season.getRatings().get(j);
            }
            if (sum != 0.0) {
                avg += sum / season.getRatings().size();
            }
        }
        if (avg != 0) {
            averageRating = avg / seasons.size();
        } else {
            averageRating = 0.0;
        }
    }
}
