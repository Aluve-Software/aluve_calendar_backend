import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

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
        Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

        if (optionalMeeting.isPresent()) {
            Meeting meeting = optionalMeeting.get();
            MeetingDetailsDTO meetingDetailsDTO = new MeetingDetailsDTO();
            
            // Map the meeting properties to the DTO
            meetingDetailsDTO.setId(meeting.getId());
            meetingDetailsDTO.setTitle((String) meeting.getTitle());
            meetingDetailsDTO.setDateTime((LocalDateTime) meeting.getDateTime());
            meetingDetailsDTO.setParticipants(meeting.getParticipants());
            meetingDetailsDTO.setLocation(meeting.getLocation());
            meetingDetailsDTO.setDescription(meeting.getDescription());
            meetingDetailsDTO.setStatus(meeting.getStatus());

            return meetingDetailsDTO;
        } else {
            throw new MeetingNotFoundException("Meeting with ID " + meetingId + " not found.");
        }
    } 
}
