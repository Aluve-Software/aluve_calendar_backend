import org.springframework.web.bind.annotation.PathVariable;

@GetMapping("/{meetingId}")
public ResponseEntity<?> getMeetingDetails(@PathVariable Long meetingId) {
    try {
        MeetingDetailsDTO meetingDetailsDTO = meetingService.getMeetingDetails(meetingId);
        return ResponseEntity.ok(meetingDetailsDTO);
    } catch (MeetingNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve meeting details: " + e.getMessage());
    }
}