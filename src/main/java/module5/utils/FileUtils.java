package module5.utils;

import lombok.extern.slf4j.Slf4j;
import module5.dto.Client;
import module5.dto.Project;
import module5.dto.ProjectWorker;
import module5.dto.Worker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class FileUtils {

    private static final StringBuilder builder = new StringBuilder();

    public static String getSQLQueryFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(getFilePath(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line.trim()).append(" ");
            }
            log.info("Query successfully get from file - [{}]", fileName);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static List<Worker> getWorkerFromFile(String workerFile) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(getFilePath(workerFile)))) {
            String line;
            ArrayList<Worker> workers = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parameter = line.split(",");
                workers.add(new Worker(parameter[0], LocalDate.parse(parameter[1]),
                        parameter[2], Integer.parseInt(parameter[3])));
            }
            return workers;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Client> getClientFromFile(String clientFile) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(getFilePath(clientFile)))) {
            String line;
            ArrayList<Client> clients = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parameter = line.split(",");
                clients.add(new Client(Long.getLong(parameter[0]), parameter[1]));
            }
            return clients;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Project> getProjectFromFile(String projectFile) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(getFilePath(projectFile)))) {
            String line;
            ArrayList<Project> projects = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parameter = line.split(",");
                projects.add(new Project(Integer.parseInt(parameter[0]),
                        LocalDate.parse(parameter[1]),
                        LocalDate.parse(parameter[2])));
            }
            return projects;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ProjectWorker> getProjectWorkerFromFile(String projectWorkerFile) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(getFilePath(projectWorkerFile)))) {
            String line;
            ArrayList<ProjectWorker> projectWorkers = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parameter = line.split(",");
                projectWorkers.add(new ProjectWorker(Integer.parseInt(parameter[0]),
                        Integer.parseInt(parameter[1])));
            }
            return projectWorkers;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFilePath(String file) throws URISyntaxException {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL resource = classLoader.getResource(file);
        Path path = Paths.get(Objects.requireNonNull(resource).toURI());
        return path.toString();
    }

}