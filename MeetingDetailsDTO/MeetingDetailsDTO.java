public class MeetingDetailsDTO {
    private Long id;
    private String title;
    private LocalDateTime datetime;
    private List<String> participants;
    private String location;
    private String description;
    private MeetingStatus status;

    // Default Constructor
    public MeetingDetailsDTO() {
    }
}