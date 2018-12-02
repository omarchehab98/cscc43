package cscc43.assignment.repository;

import java.util.List;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.CrewMember;
import cscc43.assignment.model.Movie;

public class CrewMemberRepository extends Repository<CrewMember> {
    public List<CrewMember> findByMovie(Movie movie) {
        return findByForeignEntity("movie", movie);
    }

    public boolean deleteByMovie(Movie movie) {
        return deleteByForeignEntity("movie", movie);
    }
}
