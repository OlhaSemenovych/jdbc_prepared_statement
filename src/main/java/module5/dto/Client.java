package module5.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {

    private String name;

    public Client(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "client='" + name + '\'' +
                '}';
    }
}
