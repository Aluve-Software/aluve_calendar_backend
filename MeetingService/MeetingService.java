import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MeetingService {

    @Autowired
    private Object meetingRepository;

    public Meeting createMeeting(Meeting meeting) {
        if (meeting.getTitle() == null || meeting.getDateTime() == null) {
            throw new IllegalArgumentException("Title and date/time are required fields.");
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        if (((LocalDateTime) meeting.getDateTime()).isBefore(currentDateTime)) {
            throw new IllegalArgumentException("Meeting date must be in the future.");
        }

        // save meeting to the database
        return meetingRepository.save(meeting);
    }

    public MeetingDetailsDTO getMeetingDetails(Long meetingId) {
        Optional<Meeting> optionalMeeting = ((Object) meetingRepository).findById(meetingId);

        if (optionalMeeting.isPresent()) {
            Meeting meeting = optionalMeeting.get();
        }
    }
    
}
