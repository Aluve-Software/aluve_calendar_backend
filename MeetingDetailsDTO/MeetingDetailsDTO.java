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
    public MeetingDetailsDTO(Lond id, String title, LocalDateTime dateTime, List<String> participants, String location, String description, MeetingStatus status) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.participants = participants;
        this.location = location;
        this.description = description;
        this.status = status;
    }
}