package module5.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Project {

    private int id;
    private LocalDate startDate;
    private LocalDate finishDate;

    public Project(int id, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.startDate = startDate;
        this.finishDate = endDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + finishDate +
                '}';
    }

}
