package module5.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import module5.Database;
import module5.dto.Client;
import module5.dto.Project;
import module5.dto.ProjectWorker;
import module5.dto.Worker;
import module5.utils.FileUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class DatabasePopulateService {

    @SneakyThrows
    public static void main(String[] args) {
        try (Connection connection = Database.getInstance().getConnection()) {
            insertWorkerTable(connection, "csv/workers.csv");
            insertClientTable(connection, "csv/clients.csv");
            insertProjectTable(connection, "csv/projects.csv");
            insertProjectWorkerTable(connection, "csv/project_workers.csv");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Wrong query, please review the statement", e);

        }
    }

    private static void insertWorkerTable(Connection connection, String fileName) throws SQLException {
        String insertWorkers = "INSERT INTO worker (NAME, BIRTHDAY, LEVEL, SALARY) VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertWorkers);
        List<Worker> workers = FileUtils.getWorkerFromFile(fileName);
        for (Worker worker : workers) {
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setDate(2, Date.valueOf(worker.getBirthday()));
            preparedStatement.setString(3, worker.getLevel());
            preparedStatement.setInt(4, worker.getSalary());
            preparedStatement.execute();
            log.info("Worker {} - is successfully added to database", worker);
        }
    }

    private static void insertClientTable(Connection connection, String fileName) throws SQLException {
        String insertClients = "INSERT INTO client (NAME) VALUES (?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertClients);
        List<Client> clients = FileUtils.getClientFromFile(fileName);
        for (Client client : clients) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.execute();
            log.info("Client {} - is successfully added to database", client);
        }
    }

    private static void insertProjectTable(Connection connection, String fileName) throws SQLException {
        String insertProjects = "INSERT INTO project (CLIENT_ID, START_DATE, FINISH_DATE) VALUES (?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertProjects);
        List<Project> projects = FileUtils.getProjectFromFile(fileName);
        for (Project project : projects) {
            preparedStatement.setInt(1, project.getId());
            preparedStatement.setDate(2, Date.valueOf(project.getStartDate()));
            preparedStatement.setDate(3, Date.valueOf(project.getFinishDate()));
            preparedStatement.execute();
            log.info("Project {} - is successfully added to database", project);
        }
    }

    private static void insertProjectWorkerTable(Connection connection, String fileName) throws SQLException {
        String insertProjectWorkers = "INSERT INTO project_worker (PROJECT_ID, WORKER_ID) VALUES (?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertProjectWorkers);
        List<ProjectWorker> projectWorkers = FileUtils.getProjectWorkerFromFile(fileName);
        for (ProjectWorker projectWorker : projectWorkers) {
            preparedStatement.setInt(1, projectWorker.getProjectId());
            preparedStatement.setInt(2, projectWorker.getWorkerId());
            preparedStatement.execute();
            log.info("ProjectWorker {} - is successfully added to database", projectWorker);
        }
    }

}


