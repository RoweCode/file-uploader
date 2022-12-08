package wodrich.rowena.iapps.fileuploader.domain;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
public class FileData {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    @Column(columnDefinition = "TIMESTAMP")
    private ZonedDateTime uploadedAt;
    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;
    @ManyToOne
    @JoinColumn(name = "newspaper_id")
    private Newspaper newspaper;


    public FileData(String name, ZonedDateTime uploadedAt, Screen screen, Newspaper newspaper) {
        this.name = name;
        this.uploadedAt = uploadedAt;
        this.screen = screen;
        this.newspaper = newspaper;
    }

    public FileData() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(ZonedDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Newspaper getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(Newspaper newspaper) {
        this.newspaper = newspaper;
    }
}
