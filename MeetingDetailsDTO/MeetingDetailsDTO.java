import java.time.LocalDateTime;
import java.util.List;

public class MeetingDetailsDTO {
    private Long id;
    private String title;
    private LocalDateTime dateTime;
    private List<String> participants;
    private String location;
    private String description;
    private MeetingStatus status;

    // Default Constructor
    public MeetingDetailsDTO() {
    }

    // Parameterized constructor
    public MeetingDetailsDTO(Long id, String title, LocalDateTime dateTime, List<String> participants, String location, String description, MeetingStatus status) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.participants = participants;
        this.location = location;
        this.description = description;
        this.status = status;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public MeetingStatus getStatus() {
        return status;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(MeetingStatus status) {
        this.status = status;
    }
}