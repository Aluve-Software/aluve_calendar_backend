package tech.aluve.calendar.validatorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.aluve.calendar.validator.ResponseMessage;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseMessageTest {

    private ResponseMessage responseMessage;

    @BeforeEach
    public void setUp() {
        responseMessage = new ResponseMessage("200", "Success");
    }

    @Test
    public void testGetResultCode() {
        assertEquals("200", responseMessage.getResult_Code());
    }

    @Test
    public void testSetResultCode() {
        responseMessage.setResult_Code("404");
        assertEquals("404", responseMessage.getResult_Code());
    }

    @Test
    public void testGetMessage() {
        assertEquals("Success", responseMessage.getMessage());
    }

    @Test
    public void testSetMessage() {
        responseMessage.setMessage("Not Found");
        assertEquals("Not Found", responseMessage.getMessage());
    }
}
