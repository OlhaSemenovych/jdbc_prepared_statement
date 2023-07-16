package module5.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import module5.Database;
import module5.dto.Client;
import module5.dto.Project;
import module5.dto.ProjectWorker;
import module5.dto.Worker;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class DatabasePopulateServiceNewObjects {

    @SneakyThrows
    public static void main(String[] args) {
        try (Connection connection = Database.getInstance().getConnection()) {
            insertWorkerTable(connection, new Worker("User 1", LocalDate.parse("1997-01-11"), "Trainee", 700));
            insertClientTable(connection, new Client(7L, "Client 7"));
            insertProjectTable(connection, new Project(1, LocalDate.parse("2023-02-01"), LocalDate.parse("2023-03-01")));
            insertProjectWorkerTable(connection, new ProjectWorker(1, 1));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Wrong query, please review the statement", e);

        }
    }

    private static void insertWorkerTable(Connection connection, Worker worker) throws SQLException {
        String insertWorkers = "INSERT INTO worker (NAME, BIRTHDAY, LEVEL, SALARY) VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertWorkers);
        preparedStatement.setString(1, worker.getName());
        preparedStatement.setDate(2, Date.valueOf(worker.getBirthday()));
        preparedStatement.setString(3, worker.getLevel());
        preparedStatement.setInt(4, worker.getSalary());
        preparedStatement.execute();
        log.info("Worker {} - is successfully added to database", worker);
    }

    private static void insertClientTable(Connection connection, Client client) throws SQLException {
        String insertClients = "INSERT INTO client (NAME) VALUES (?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertClients);
        preparedStatement.setString(1, client.getName());
        preparedStatement.execute();
        log.info("Client {} - is successfully added to database", client);
    }

    private static void insertProjectTable(Connection connection, Project project) throws SQLException {
        String insertProjects = "INSERT INTO project (CLIENT_ID, START_DATE, FINISH_DATE) VALUES (?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertProjects);
        preparedStatement.setInt(1, project.getId());
        preparedStatement.setDate(2, Date.valueOf(project.getStartDate()));
        preparedStatement.setDate(3, Date.valueOf(project.getFinishDate()));
        preparedStatement.execute();
        log.info("Project {} - is successfully added to database", project);
    }

    private static void insertProjectWorkerTable(Connection connection, ProjectWorker projectWorker) throws SQLException {
        String insertProjectWorkers = "INSERT INTO project_worker (PROJECT_ID, WORKER_ID) VALUES (?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertProjectWorkers);
        preparedStatement.setInt(1, projectWorker.getProjectId());
        preparedStatement.setInt(2, projectWorker.getWorkerId());
        preparedStatement.execute();
        log.info("ProjectWorker {} - is successfully added to database", projectWorker);
    }

}



