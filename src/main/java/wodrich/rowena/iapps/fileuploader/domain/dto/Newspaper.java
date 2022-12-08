package wodrich.rowena.iapps.fileuploader.domain.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Newspaper {
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    public Newspaper(String name) {
        this.name = name;
    }

    public Newspaper() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
