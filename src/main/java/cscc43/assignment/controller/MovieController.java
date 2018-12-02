package cscc43.assignment.controller;

import cscc43.assignment.model.Movie;
import cscc43.assignment.service.MovieService;
import cscc43.assignment.throwable.MovieUpsertException;
import cscc43.assignment.throwable.DatabaseException;

public class MovieController {
    private static final MovieController instance = new MovieController();

    private MovieController() {}

    public void insert(Movie movie) throws MovieUpsertException {
        try {
            MovieService.insertOne(movie);
        } catch (DatabaseException err) {
            throw new MovieUpsertException(err.toString());
        }
    }

    public void update(Movie movie) throws MovieUpsertException {
        try {
            MovieService.updateOne(movie);
        } catch (DatabaseException err) {
            throw new MovieUpsertException(err.toString());
        }
    }

    public static MovieController getInstance() {
        return instance;
    }
}
