package cscc43.assignment.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cscc43.assignment.App;
import cscc43.assignment.database.Database;
import cscc43.assignment.store.AppState;
import cscc43.assignment.store.AppStateBuilder;
import cscc43.assignment.store.Store;
import cscc43.assignment.throwable.DatabaseException;
import cscc43.assignment.viewmodel.SearchResult;

public class ViewController {
    private static final ViewController instance = new ViewController();
    private static final Store<AppState> store = App.getStore();

    private ViewController() {}

    public SearchResult search(String type, String term) {
        Connection connection = Database.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                "SELECT " +
                "    `Name`, " +
                "    `Year`, " +
                "    `Type`, " +
                "    `Person` " +
                "FROM ( " +
                "    ( " +
                "        SELECT " +
                "            `Title` AS `Name`, " +
                "            `YearOfPublication` AS `Year`, " +
                "            'Book' AS `Type`, " +
                "            CONCAT(`FamilyName`, ', ', `FirstName`) AS `Person` " +
                "        FROM " +
                "            `Book` " +
                "        JOIN " +
                "            `BookAuthor` " +
                "            ON `BookAuthor`.`ISBN`=`Book`.`ISBN` " +
                "        JOIN " +
                "            `PeopleInvolved` " +
                "            ON `PeopleInvolved`.`ID`=`BookAuthor`.`Author_ID` " +
                "        WHERE " +
                "            `Title` LIKE ? " +
                "        ORDER BY " +
                "            `FamilyName`, `FirstName` " +
                "            DESC " +
                "        LIMIT 1 " +
                "    ) UNION ( " +
                "        SELECT " +
                "            `Movie`.`MovieName` AS `Name`, " +
                "            `Movie`.`Year` AS `Year`, " +
                "            'Movie' AS `Type`, " +
                "            CONCAT(`FamilyName`, ', ', `FirstName`) AS `Person` " +
                "        FROM " +
                "            `Movie` " +
                "        JOIN " +
                "            `CrewMember` " +
                "            ON `CrewMember`.`MovieName`=`Movie`.`MovieName` " +
                "            AND `CrewMember`.`ReleaseYear`=`Movie`.`Year` " +
                "        JOIN " +
                "            `Role` " +
                "            ON `CrewMember`.`Role_ID`=`Role`.`ID` " +
                "        JOIN " +
                "            `PeopleInvolved` " +
                "            ON `PeopleInvolved`.`ID`=`CrewMember`.`PeopleInvolved_ID` " +
                "        WHERE " +
                "            `Movie`.`MovieName` LIKE ? " +
                "            AND `Role`.`Description`='Director' " +
                "        ORDER BY " +
                "            `FamilyName`, `FirstName` " +
                "            DESC " +
                "        LIMIT 1 " +
                "    ) UNION ( " +
                "        SELECT " +
                "            `Music`.`AlbumName` AS `Name`, " +
                "            `Music`.`Year` AS `Year`, " +
                "            'Music' AS `Type`, " +
                "            CONCAT(`FamilyName`, ', ', `FirstName`) AS `Person` " +
                "        FROM " +
                "            `Music` " +
                "        JOIN " +
                "            `MusicSinger` " +
                "            ON `MusicSinger`.`AlbumName`=`Music`.`AlbumName` " +
                "            AND `MusicSinger`.`Year`=`Music`.`Year` " +
                "            AND `MusicSinger`.`MusicName`=`Music`.`MusicName` " +
                "        JOIN " +
                "            `PeopleInvolved` " +
                "            ON `PeopleInvolved`.`ID`=`MusicSinger`.`PeopleInvolved_ID` " +
                "        WHERE " +
                "            `Music`.`AlbumName` LIKE ? " +
                "        ORDER BY " +
                "            `FamilyName`, `FirstName` " +
                "            DESC " +
                "        LIMIT 1 " +
                "    ) " +
                ") AS `Query` " +
                "WHERE " +
                "    `Type`=? "
            );
            String searchQuery = "%" + term + "%";
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            preparedStatement.setString(3, searchQuery);
            preparedStatement.setString(4, type);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                SearchResult searchResult = new SearchResult(
                    resultSet.getString("Name"),
                    resultSet.getInt("Year"),
                    resultSet.getString("Type"),
                    resultSet.getString("Person")
                );
                store.setState(new AppStateBuilder(store.getState())
                    .setSearchResult(searchResult)
                    .build());
                return searchResult;
            }
            return null;
        } catch (SQLException|DatabaseException err) {
            err.printStackTrace();
            Database.rollback();
            throw new DatabaseException(err.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException err) {
                err.printStackTrace();
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException err) {
                err.printStackTrace();
            }
            Database.disconnect();
        }
    }

    public static ViewController getInstance() {
        return instance;
    }
}
