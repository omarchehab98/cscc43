package cscc43.assignment.repository;

import java.util.List;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.Award;
import cscc43.assignment.model.Movie;

public class AwardRepository extends Repository<Award> {
    public List<Award> findByMovie(Movie movie) {
        return findByForeignEntity("movie", movie);
    }

    public boolean deleteByMovie(Movie movie) {
        return deleteByForeignEntity("movie", movie);
    }
}
