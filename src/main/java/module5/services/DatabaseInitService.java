package module5.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import module5.Database;
import module5.utils.FileUtils;

import java.sql.*;
import java.time.LocalDate;

@Slf4j
public class DatabaseInitService {

    @SneakyThrows
    public static void main(String[] args) {
        createWorkerTable(2, 1000,
                LocalDate.of(1900, 1, 1),
                new String[]{"Trainee", "Junior", "Middle", "Senior"},
                100, 100000);
    }

    private static void createWorkerTable(int lowerLimitOfCharsInName,
                                          int upperLimitOfCharsInName,
                                          LocalDate minBirthdayData,
                                          String[] levelList,
                                          int lowerLimitOfSalary,
                                          int upperLimitOfSalary) throws SQLException {
        try (Connection connection = Database.getInstance().getConnection()) {
            String query = FileUtils.getSQLQueryFromFile("sql/init_db.sql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            try {
                preparedStatement.setInt(1, lowerLimitOfCharsInName);
                preparedStatement.setInt(2, upperLimitOfCharsInName);
                preparedStatement.setTimestamp(3, Timestamp.valueOf(minBirthdayData.atStartOfDay()));
                preparedStatement.setString(4, levelList[0]);
                preparedStatement.setString(5, levelList[1]);
                preparedStatement.setString(6, levelList[2]);
                preparedStatement.setString(7, levelList[3]);
                preparedStatement.setInt(8, lowerLimitOfSalary);
                preparedStatement.setInt(9, upperLimitOfSalary);
                preparedStatement.execute();
                log.info("Initialized successful!");
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("Wrong query - [{}], please review the statement", query, e);
            }
        }
    }

}
