package cscc43.assignment.repository;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.Movie;

public class MovieRepository extends Repository<Movie> {
    public Movie findOneByName(String name) {
        String where = String.format("`%s`=?", columnName(Movie.class, "name"));
        return (Movie) populate(findOneWhere(where, name), null);
    }

    public boolean deleteOneByName(String name) {
        String where = String.format("`%s`=?", columnName(Movie.class, "name"));
        return deleteOneWhere(where, name);
    }
}
