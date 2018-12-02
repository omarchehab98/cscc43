package cscc43.assignment.service;

import java.sql.Connection;
import java.sql.SQLException;

import cscc43.assignment.database.Database;
import cscc43.assignment.model.CrewMember;
import cscc43.assignment.model.Movie;
import cscc43.assignment.model.Person;
import cscc43.assignment.model.Award;
import cscc43.assignment.repository.CrewMemberRepository;
import cscc43.assignment.repository.MovieRepository;
import cscc43.assignment.repository.PersonRepository;
import cscc43.assignment.repository.AwardRepository;
import cscc43.assignment.throwable.DatabaseException;

public class MovieService {
    private static final MovieRepository movieRepository = new MovieRepository();
    private static final CrewMemberRepository crewMemberRepository = new CrewMemberRepository();
    private static final AwardRepository awardRepository = new AwardRepository();
    private static final PersonRepository personRepository = new PersonRepository();

    public static Movie insertOne(Movie movie) {
        Connection connection = Database.connect();
        try {
            connection.setAutoCommit(false);
            Movie newMovie = movieRepository.insertOne(movie);
            insertForeign(movie, newMovie);
            Database.commit();
            return newMovie;
        } catch (SQLException|DatabaseException err) {
            err.printStackTrace();
            Database.rollback();
            throw new DatabaseException(err.getMessage());
        } finally {
            Database.disconnect();
        }
    }

    public static Movie updateOne(Movie movie) {
        Connection connection = Database.connect();
        try {
            connection.setAutoCommit(false);
            Movie newMovie = movieRepository.updateOne(movie);
            crewMemberRepository.deleteByMovie(movie);
            awardRepository.deleteByMovie(movie);
            insertForeign(movie, newMovie);
            Database.commit();
            return newMovie;
        } catch (SQLException|DatabaseException err) {
            err.printStackTrace();
            Database.rollback();
            throw new DatabaseException(err.getMessage());
        } finally {
            Database.disconnect();
        }
    }

    public static boolean deleteByName(String name) {
        Connection connection = Database.connect();
        try {
            connection.setAutoCommit(false);
            Movie movie = movieRepository.findOneByName(name);
            if (movie == null) {
                return false;
            }
            crewMemberRepository.deleteByMovie(movie);
            awardRepository.deleteByMovie(movie);
            boolean isDeleted = movieRepository.deleteOne(movie);
            Database.commit();
            return isDeleted;
        } catch (SQLException|DatabaseException err) {
            err.printStackTrace();
            Database.rollback();
            throw new DatabaseException(err.getMessage());
        } finally {
            Database.disconnect();
        }
    }

    private static void insertForeign(Movie movie, Movie newMovie) {
        for (CrewMember crewMember : movie.getCrewMembers()) {
            Person newPerson = personRepository.upsertByName(crewMember.getPerson());
            crewMemberRepository.insertOne(new CrewMember(newPerson, newMovie, crewMember.getRole()));
        }
        for (Award award : movie.getAwards()) {
            Person newPerson = personRepository.upsertByName(award.getPerson());
            awardRepository.insertOne(new Award(award.getAward(), newPerson, newMovie));
        }
    }
}
