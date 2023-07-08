package module5.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProjectWorker {

    private int projectId;
    private int workerId;

    public ProjectWorker(int projectId, int workerId) {
        this.projectId = projectId;
        this.workerId = workerId;
    }

    @Override
    public String toString() {
        return "ProjectWorker{" +
                "projectId=" + projectId +
                ", workerId=" + workerId +
                '}';
    }

}
