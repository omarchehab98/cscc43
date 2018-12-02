package cscc43.assignment.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import cscc43.assignment.App;
import cscc43.assignment.database.Database;
import cscc43.assignment.store.AppState;
import cscc43.assignment.store.AppStateBuilder;
import cscc43.assignment.store.Store;
import cscc43.assignment.throwable.DatabaseException;
import cscc43.assignment.viewmodel.Report;

public class ReportController {
    private static final ReportController instance = new ReportController();
    private static final Store<AppState> store = App.getStore();

    private ReportController() {}

    public void authorsPublications(String firstName, String middleName, String lastName) {
        report(
            "SELECT " +
            "    `Book`.`ISBN`, " +
            "    `Book`.`Title` AS `Book's Name`, " +
            "    `Book`.`YearOfPublication` AS `Published year` " +
            "FROM `Book` " +
            "JOIN `BookAuthor` " +
            "    ON `BookAuthor`.`ISBN`=`Book`.`ISBN` " +
            "JOIN `PeopleInvolved` " +
            "    ON `PeopleInvolved`.`ID`=`BookAuthor`.`Author_ID` " +
            "WHERE " +
            "    `FirstName`=?" +
            "    AND `MiddleName`=?" +
            "    AND `FamilyName`=?" +
            "ORDER BY `Book`.`ISBN` ",
            firstName,
            middleName,
            lastName
        );
    }

    public void publicationsInOneYear(Integer year) {
        report(
            "SELECT " +
            "    `Book`.`ISBN`, " +
            "    `Book`.`Title` AS `Book's Name`, " +
            "    `Book`.`YearOfPublication` AS `Published year`, " +
            "    `PeopleInvolved`.`FamilyName` AS `Family Name`, " +
            "    LEFT(`PeopleInvolved`.`FirstName`, 1) AS `Initial of First Name` " +
            "FROM `Book` " +
            "JOIN `BookAuthor` " +
            "    ON `BookAuthor`.`ISBN`=`Book`.`ISBN` " +
            "JOIN `PeopleInvolved` " +
            "    ON `PeopleInvolved`.`ID`=`BookAuthor`.`Author_ID` " +
            "WHERE " +
            "    `Book`.`YearOfPublication`=? " +
            "ORDER BY `Book`.`Title` ",
            year
        );
    }
    
    public void booksWithSimilarTopic(String subject) {
        String search = "%" + subject + "%";
        report(
            "SELECT DISTINCT " +
            "    `Book`.`ISBN`, " +
            "    `Book`.`Title` AS `Book's Name`, " +
            "    `Book`.`YearOfPublication` AS `Published year` " +
            "FROM `Book` " +
            "JOIN `BookKeyword` " +
            "    ON `BookKeyword`.`ISBN`=`Book`.`ISBN` " +
            "JOIN `Keyword` " +
            "    ON `Keyword`.`ID`=`BookKeyword`.`Keyword_ID` " +
            "WHERE " +
            "    `Book`.`Title` LIKE ? " +
            "    OR `Book`.`Abstract` LIKE ? " +
            "    OR `Keyword`.`Tag` LIKE ? " +
            "ORDER BY `Book`.`ISBN` ",
            search,
            search,
            search
        );
    }
    
    public void frequentPublishers() {
        report(
            "SELECT DISTINCT " +
            "    `Book`.`ISBN` AS `ISBN`, " +
            "    `Book`.`Title` AS `Book's Name`, " +
            "    CONCAT(`PeopleInvolved`.`FamilyName`, ', ', `PeopleInvolved`.`FirstName`) AS `Author's Name`, " +
            "    `Book`.`YearOfPublication` AS `Published year` " +
            "FROM `Book` " +
            "JOIN `Book` AS `OtherBook` " +
            "    ON `OtherBook`.`ISBN`!=`Book`.`ISBN` " +
            "JOIN `BookAuthor` " +
            "    ON `BookAuthor`.`ISBN`=`Book`.`ISBN` " +
            "JOIN `PeopleInvolved` " +
            "    ON `PeopleInvolved`.`ID`=`BookAuthor`.`Author_ID` " +
            "JOIN `BookAuthor` AS `OtherBookAuthor` " +
            "    ON `OtherBookAuthor`.`ISBN`=`OtherBook`.`ISBN` " +
            "JOIN `PeopleInvolved` AS `OtherPeopleInvolved` " +
            "    ON `OtherPeopleInvolved`.`ID`=`OtherBookAuthor`.`Author_ID` " +
            "WHERE " +
            "    (`Book`.`YearOfPublication` + 1=`OtherBook`.`YearOfPublication` " +
            "    OR `Book`.`YearOfPublication`=`OtherBook`.`YearOfPublication` + 1) " +
            "    AND `PeopleInvolved`.`ID`=`OtherPeopleInvolved`.`ID` " +
            "ORDER BY " +
            "    `PeopleInvolved`.`FamilyName`, " +
            "    `Book`.`YearOfPublication` "
        );
    }
    
    public void mostPopularSubjects() {
        report(
            "SELECT " +
            "    `Keyword`.`Tag` AS `Tag`, " +
            "    COUNT(`Keyword`.`Tag`) AS `Frequency` " +
            "FROM `BookKeyword` " +
            "JOIN `Keyword` " +
            "    ON `Keyword`.`ID`=`BookKeyword`.`Keyword_ID` " +
            "GROUP BY `Keyword`.`ID` " +
            "HAVING `Frequency` > 1 " +
            "ORDER BY `Frequency` DESC, `Keyword`.`Tag` "
            // "LIMIT 1 "
        );
    }
    
    public void multiSkillsMovieCrew() {
        report(
            "SELECT " +
            "    `PeopleInvolved`.`FamilyName` AS `Family Name`, " +
            "    `Role`.`Description` AS `Role`, " +
            "    `CrewMember`.`MovieName` AS `Movie Name` " +
            "FROM `CrewMember` " +
            "JOIN `CrewMember` AS `OtherCrewMember` " +
            "    ON `OtherCrewMember`.`PeopleInvolved_ID`=`CrewMember`.`PeopleInvolved_ID` " +
            "JOIN `PeopleInvolved` " +
            "    ON `PeopleInvolved`.`ID`=`CrewMember`.`PeopleInvolved_ID` " +
            "JOIN `PeopleInvolved` AS `OtherPeopleInvolved` " +
            "    ON `OtherPeopleInvolved`.`ID`=`OtherCrewMember`.`PeopleInvolved_ID` " +
            "JOIN `Role` " +
            "    ON `Role`.`ID`=`CrewMember`.`Role_ID` " +
            "JOIN `Role` AS `OtherRole` " +
            "    ON `OtherRole`.`ID`=`OtherCrewMember`.`Role_ID` " +
            "WHERE " +
            "    `CrewMember`.`Role_ID`!=`OtherCrewMember`.`Role_ID` " +
            "ORDER BY `PeopleInvolved`.`FamilyName` "
        );
    }
    
    public void awardWinningMovies() {
        report(
            "SELECT  " +
            "    `Movie`.`MovieName` AS `Movie Name`, " +
            "    CONCAT(`PeopleInvolved`.`FamilyName`, ', ', `PeopleInvolved`.`FirstName`) AS `Director Name`, " +
            "    COUNT(`Award`.`Award`) AS `# of Awards` " +
            "FROM `Movie` " +
            "JOIN `Movie` AS `OtherMovie` " +
            "    ON `OtherMovie`.`MovieName`!=`Movie`.`MovieName` " +
            "    AND `OtherMovie`.`Year`!=`Movie`.`Year` " +
            "JOIN `CrewMember` " +
            "    ON `CrewMember`.`MovieName`=`Movie`.`MovieName` " +
            "    AND `CrewMember`.`ReleaseYear`=`Movie`.`Year` " +
            "JOIN `CrewMember` AS `OtherCrewMember` " +
            "    ON `OtherCrewMember`.`MovieName`=`OtherMovie`.`MovieName` " +
            "    AND `OtherCrewMember`.`ReleaseYear`=`OtherMovie`.`Year` " +
            "JOIN `Role` " +
            "    ON `Role`.`ID`=`CrewMember`.`Role_ID` " +
            "JOIN `PeopleInvolved` " +
            "    ON `PeopleInvolved`.`ID`=`CrewMember`.`PeopleInvolved_ID` " +
            "JOIN `Award` " +
            "    ON `Award`.`PeopleInvolved_ID`=`CrewMember`.`PeopleInvolved_ID` " +
            "    AND `Award`.`MovieName`=`Movie`.`MovieName` " +
            "    AND `Award`.`Year`=`Movie`.`Year` " +
            "WHERE " +
            "    `Role`.`Description`='Director' " +
            "    AND `CrewMember`.`PeopleInvolved_ID`=`OtherCrewMember`.`PeopleInvolved_ID` " +
            "    AND `Award`.`Award` > 0 " +
            "GROUP BY `Movie`.`MovieName`, `Movie`.`Year` " +
            "ORDER BY `PeopleInvolved`.`FamilyName`, `Movie`.`MovieName` "  
        );
    }
    
    public void musicWithSimilarName() {
        report(
            "SELECT " +
            "    `Music`.`AlbumName` AS `Album Name`, " +
            "    `Music`.`MusicName` AS `Music Name`, " +
            "    CONCAT(`PeopleInvolved`.`FamilyName`, ', ', `PeopleInvolved`.`FirstName`) AS `Singers`, " +
            "    `MusicSinger`.`Year` AS `Year` " +
            "FROM `Music` " +
            "JOIN `Music` AS `OtherMusic` " +
            "    ON `Music`.`MusicName`=`OtherMusic`.`MusicName` " +
            "    AND `Music`.`AlbumName`!=`OtherMusic`.`AlbumName` " +
            "    AND `Music`.`Year`!=`OtherMusic`.`Year` " +
            "JOIN `MusicSinger` " +
            "    ON `MusicSinger`.`MusicName`=`Music`.`MusicName` " +
            "    AND `MusicSinger`.`AlbumName`=`Music`.`AlbumName` " +
            "    AND `MusicSinger`.`Year`=`Music`.`Year` " +
            "JOIN `PeopleInvolved` " +
            "    ON `PeopleInvolved`.`ID`=`MusicSinger`.`PeopleInvolved_ID` " +
            "ORDER BY `Music`.`MusicName`, `MusicSinger`.`Year` "  
        );
    }
    
    public void multiSkillsMusicCrew() {
        report(
            "SELECT " +
            "    `Music`.`AlbumName` AS `Album Name`, " +
            "    `Music`.`MusicName` AS `Music Name`, " +
            "    CONCAT(`PeopleInvolved`.`FamilyName`, ', ', `PeopleInvolved`.`FirstName`) AS `Singers`, " +
            "    `Music`.`Year` AS `Year` " +
            "FROM `Music` " +
            "JOIN `PeopleInvolvedMusic` " +
            "    ON `PeopleInvolvedMusic`.`MusicName`=`Music`.`MusicName` " +
            "    AND `PeopleInvolvedMusic`.`AlbumName`=`Music`.`AlbumName` " +
            "    AND `PeopleInvolvedMusic`.`Year`=`Music`.`Year` " +
            "JOIN `PeopleInvolved` " +
            "    ON `PeopleInvolved`.`ID`=`PeopleInvolvedMusic`.`PeopleInvolved_ID` " +
            "WHERE " +
            "    `PeopleInvolvedMusic`.`IsComposer`!=0 " +
            "    AND `PeopleInvolvedMusic`.`IsSongwriter`!=0 " +
            "    AND `PeopleInvolvedMusic`.`IsArranger`=0 "
        );
    }
    
    public void similarNames() {
        report(
            "SELECT " +
            "    `FamilyName` AS `Family name`, " +
            "    GROUP_CONCAT(`Role` SEPARATOR ', ') AS `Roles` " +
            "FROM ( " +
            "    ( " +
            "        SELECT DISTINCT " +
            "            `FamilyName`, " +
            "            'Author' AS `Role` " +
            "        FROM `BookAuthor` " +
            "        JOIN `PeopleInvolved` " +
            "            ON `PeopleInvolved`.`ID`=`BookAuthor`.`Author_ID` " +
            "    ) UNION ( " +
            "        SELECT DISTINCT " +
            "            `FamilyName` AS `Family name`, " +
            "            `Role`.`Description` AS `Roles` " +
            "        FROM `CrewMember` " +
            "        JOIN `PeopleInvolved` " +
            "            ON `PeopleInvolved`.`ID`=`CrewMember`.`PeopleInvolved_ID` " +
            "        JOIN `Role` " +
            "            ON `Role`.`ID`=`CrewMember`.`Role_ID` " +
            "    ) UNION ( " +
            "        SELECT DISTINCT " +
            "            `FamilyName`, " +
            "            'Singer' AS `Role` " +
            "        FROM `MusicSinger` " +
            "        JOIN `PeopleInvolved` " +
            "            ON `PeopleInvolved`.`ID`=`MusicSinger`.`PeopleInvolved_ID` " +
            "    ) UNION ( " +
            "        SELECT DISTINCT " +
            "            `FamilyName` AS `Family name`, " +
            "            'Composer' AS `Roles` " +
            "        FROM `PeopleInvolvedMusic` " +
            "        JOIN `PeopleInvolved` " +
            "            ON `PeopleInvolved`.`ID`=`PeopleInvolvedMusic`.`PeopleInvolved_ID` " +
            "        WHERE `IsComposer`!=1 " +
            "    ) UNION ( " +
            "        SELECT DISTINCT " +
            "            `FamilyName`, " +
            "            'Arranger' AS `Role` " +
            "        FROM `PeopleInvolvedMusic` " +
            "        JOIN `PeopleInvolved` " +
            "            ON `PeopleInvolved`.`ID`=`PeopleInvolvedMusic`.`PeopleInvolved_ID` " +
            "        WHERE `IsArranger`!=1 " +
            "    ) UNION ( " +
            "        SELECT DISTINCT " +
            "            `FamilyName`, " +
            "            'Songwriter' AS `Role` " +
            "        FROM `PeopleInvolvedMusic` " +
            "        JOIN `PeopleInvolved` " +
            "            ON `PeopleInvolved`.`ID`=`PeopleInvolvedMusic`.`PeopleInvolved_ID` " +
            "        WHERE `IsSongwriter`!=1 " +
            "    ) " +
            ") AS `Query` " +
            "GROUP BY `FamilyName` " +
            "HAVING COUNT(`FamilyName`) > 1 "
        );
    }

    private void report(String query, Object... parameters) {
        Connection connection = Database.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        store.setState(new AppStateBuilder(store.getState())
            .setReport(new Report(new Object[][]{}, new String[]{}))
            .build());
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            String[] header = new String[metaData.getColumnCount()];
            for (int i = 0; i < header.length; i++) {
                header[i] = metaData.getColumnName(i + 1);
            }
            List<Object[]> data = new ArrayList<Object[]>();
            while (resultSet.next()) {
                Object[] row = new Object[header.length];
                for (int i = 0; i < header.length; i++) {
                    row[i] = resultSet.getObject(i + 1);
                }
                data.add(row);
            }
            Report report = new Report(data.toArray(new Object[][]{}), header);
            store.setState(new AppStateBuilder(store.getState())
                .setReport(report)
                .build());
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
    
    public static ReportController getInstance() {
        return instance;
    }
}
