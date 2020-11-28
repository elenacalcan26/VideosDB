package main;

import action.QueryActor;
import action.QueryMovie;
import action.QueryShow;
import action.QueryUser;
import action.Recommendation;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import database.Database;
import entertainment.Movie;
import entertainment.Serial;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        Database data = new Database();
        data.setActor(input.getActors());
        data.setUser(input.getUsers());
        data.setMovie(input.getMovies());
        data.setSerial(input.getSerials());
        data.setLongestMovies(input.getMovies());
        data.setLongestSerial(input.getSerials());
        String message = null;
        User user;
        Movie movie;
        Serial serial;
        List<ActionInputData> comm = input.getCommands();
        // parcurg comenzile
        for (int i = 0; i < comm.size(); i++) {
            switch (comm.get(i).getActionType()) {
                // verific tipul actiunii
                case "command":
                    switch (comm.get(i).getType()) {
                        // verific tipul comenzii
                        case "favorite" -> {
                            user = data.extractUser(comm.get(i).getUsername());
                            if (user != null) {
                                message = user.favorite(comm.get(i).getTitle());
                            }
                            //noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                    "", message));
                        }
                        case "view" -> {
                            user = data.extractUser(comm.get(i).getUsername());
                            if (user != null) {
                                message = user.view(comm.get(i).getTitle());
                            }
                            //noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(), "",
                                    message));
                        }
                        case "rating" -> {
                            if (data.isMvoie(comm.get(i).getTitle())) {
                                movie = data.extractMovie(comm.get(i).getTitle());
                                user = data.extractUser(comm.get(i).getUsername());
                                if (user != null) {
                                    message = user.ratingMovie(comm.get(i).getTitle(),
                                            comm.get(i).getGrade(), movie);
                                }
                            } else {
                                serial = data.extractSerial(comm.get(i).getTitle());
                                user = data.extractUser(comm.get(i).getUsername());
                                if (user != null) {
                                    message = user.ratingSerial(comm.get(i).getTitle(),
                                            input.getCommands().get(i).getGrade(), serial,
                                            comm.get(i).getSeasonNumber());
                                }
                            }
                            //noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(), "",
                                    message));
                        }
                    }
                    break;
                case "query":
                    int year = 0;
                    if (comm.get(i).getFilters().get(0).get(0) != null) {
                        year = Integer.parseInt(comm.get(i).getFilters().get(0).get(0));
                    }
                    String gen = comm.get(i).getFilters().get(1).get(0);
                    switch (comm.get(i).getObjectType()) {
                        // verific daca query-ul e pentru filme sau seriale
                        case "movies":
                            // verific criteriul
                            switch (comm.get(i).getCriteria()) {
                                case "ratings" -> {
                                    data.setAverageRatingMovies();
                                    message = QueryMovie.queryRatingMovies(data,
                                            comm.get(i).getSortType(), year, gen,
                                            comm.get(i).getNumber());
                                    //noinspection unchecked
                                    arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                            "", message));
                                    data.getAverageRatingMovies().clear();
                                }
                                case "favorite" -> {
                                    data.setFavoriteMoviesOfUsers();
                                    data.setFavoriteSerialsOfUsers();
                                    message = QueryMovie.queryFavoriteMovies(data,
                                            comm.get(i).getSortType(), year, gen,
                                            comm.get(i).getNumber());
                                    //noinspection unchecked
                                    arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                            "", message));
                                    data.getFavoriteMoviesOfUsers().clear();
                                }
                                case "longest" -> {
                                    message = QueryMovie.queryLongestMovie(data,
                                            comm.get(i).getSortType(), year, gen,
                                            comm.get(i).getNumber());
                                    //noinspection unchecked
                                    arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                            "", message));
                                }
                                case "most_viewed" -> {
                                    data.setViewedMovies();
                                    message = QueryMovie.queryViewedMovies(data,
                                            comm.get(i).getSortType(), year, gen,
                                            comm.get(i).getNumber());
                                    //noinspection unchecked
                                    arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                            "", message));
                                    data.getViewedMovies().clear();
                                }
                            }
                            break;
                        case "shows":
                            switch (comm.get(i).getCriteria()) {
                                case "favorite" -> {
                                    data.setFavoriteSerialsOfUsers();
                                    message = QueryShow.queryFavoriteSerials(data,
                                            comm.get(i).getSortType(), year, gen,
                                            comm.get(i).getNumber());
                                    //noinspection unchecked
                                    arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                            "", message));
                                }
                                case "longest" -> {
                                    message = QueryShow.queryLongestSerials(data,
                                            comm.get(i).getSortType(), year, gen,
                                            comm.get(i).getNumber());
                                    //noinspection unchecked
                                    arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                            "", message));
                                }
                                case "most_viewed" -> {
                                    data.setViewedSerials();
                                    message = QueryShow.queryViewedSerial(data,
                                            comm.get(i).getSortType(), year, gen,
                                            comm.get(i).getNumber());
                                    //noinspection unchecked
                                    arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                            "", message));
                                    data.getViewedSerials().clear();
                                }
                                case "ratings" -> {
                                    data.setAverageRatingSerials();
                                    message = QueryShow.queryRatingSerials(data,
                                            comm.get(i).getSortType(), year, gen,
                                            comm.get(i).getNumber());
                                    //noinspection unchecked
                                    arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                            "", message));
                                    data.getAverageRatingSerials().clear();
                                }
                            }
                            break;
                        case "users":
                            data.setActiveUser();
                            message = QueryUser.queryActiveUser(data, comm.get(i).getSortType(),
                                    comm.get(i).getNumber());
                            //noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                    "", message));
                            data.getActiveUser().clear();
                            break;
                        case "actors":
                            switch (comm.get(i).getCriteria()) {
                                // verific criteriul
                                case "awards" -> {
                                    data.setAwardedActors(comm.get(i).getFilters().get(3));
                                    message = QueryActor.awardsActors(data,
                                            comm.get(i).getSortType());
                                    //noinspection unchecked
                                    arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                            "", message));
                                    data.getAwardedActors().clear();
                                }
                                case "filter_description" -> {
                                    message = QueryActor.filterDescriptionQuery(data,
                                            comm.get(i).getFilters().get(2),
                                            comm.get(i).getSortType());
                                    //noinspection unchecked
                                    arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                            "", message));
                                }
                                case "average" -> {
                                    data.setAverageRatingSerials();
                                    data.setAverageRatingMovies();
                                    data.setRatingActor();
                                    message = QueryActor.averageQuery(data,
                                            comm.get(i).getSortType(),
                                            comm.get(i).getNumber());
                                    //noinspection unchecked
                                    arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(),
                                            "", message));
                                    data.getAverageRatingSerials().clear();
                                    data.getAverageRatingMovies().clear();
                                    data.getRatingActor().clear();
                                }
                            }
                            break;
                    }
                    break;
                case "recommendation":
                    switch (comm.get(i).getType()) {
                        // verific tipul recomandarii
                        case "best_unseen" -> {
                            data.setAverageRatingSerials();
                            data.setAverageRatingMovies();
                            data.setTotalShowsRating();
                            user = data.extractUser(comm.get(i).getUsername());
                            message = Recommendation.bestUnseenRecommendation(data, user);
                            //noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(), "",
                                    message));
                            data.getAverageRatingMovies().clear();
                            data.getAverageRatingSerials().clear();
                            data.getTotalShowsRating().clear();
                        }
                        case "standard" -> {
                            user = data.extractUser(comm.get(i).getUsername());
                            message = Recommendation.standardRecommendation(data, user);
                            //noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(), "",
                                    message));
                        }
                        case "favorite" -> {
                            data.setMostFavouriteShows();
                            user = data.extractUser((comm.get(i).getUsername()));
                            if (user != null) {
                                message = Recommendation.favouriteRecommendation(data, user);
                            }
                            //noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(), "",
                                    message));
                            data.getMostFavouriteShows().clear();
                        }
                        case "popular" -> {
                            data.setPopularGenres();
                            user = data.extractUser(comm.get(i).getUsername());
                            assert user != null;
                            message = Recommendation.popularRecommendation(data, user);
                            //noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(), "",
                                    message));
                            data.getPopularGenres().clear();
                        }
                        case "search" -> {
                            data.setAverageRatingMovies();
                            data.setAverageRatingSerials();
                            user = data.extractUser(comm.get(i).getUsername());
                            if (user != null) {
                                message = Recommendation.searchRecommendation(data, user,
                                        comm.get(i).getGenre());
                            }
                            //noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(comm.get(i).getActionId(), "",
                                    message));
                            data.getAverageRatingMovies().clear();
                            data.getAverageRatingSerials().clear();
                        }
                    }
                    break;
            }
        }

        fileWriter.closeJSON(arrayResult);
    }
}
