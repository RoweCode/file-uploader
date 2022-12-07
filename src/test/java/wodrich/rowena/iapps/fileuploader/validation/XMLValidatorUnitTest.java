package wodrich.rowena.iapps.fileuploader.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class XMLValidatorUnitTest {

    public static final String VALID_TEST_FILE_XML = "valid-test-file.xml";
    public static final String INVALID_ORDER_TEST_FILE_XML = "invalid-order.xml";

    private final XMLValidator xmlValidator = new XMLValidator();

    @Test
    void testIsValidAgainstXSDSchema_Success() throws IOException {
        File resource = new ClassPathResource("testFiles/" + VALID_TEST_FILE_XML).getFile();
        byte[] fileBytes = Files.readAllBytes(resource.toPath());
        MockMultipartFile file
                = new MockMultipartFile("file", VALID_TEST_FILE_XML,
                MediaType.TEXT_PLAIN_VALUE, fileBytes);

        // when
        boolean isValid = xmlValidator.isValidAgainstXSDSchema(file, XMLValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH);

        // then
        assertTrue(isValid);
    }

    @Test
    void testIsValidAgainstXSDSchema_InValidOrderOfElements() throws IOException {
        File resource = new ClassPathResource("testFiles/" + INVALID_ORDER_TEST_FILE_XML).getFile();
        byte[] fileBytes = Files.readAllBytes(resource.toPath());
        MockMultipartFile file
                = new MockMultipartFile("file", INVALID_ORDER_TEST_FILE_XML,
                MediaType.TEXT_PLAIN_VALUE, fileBytes);

        // when
        boolean isValid = xmlValidator.isValidAgainstXSDSchema(file, XMLValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH);

        // then
        assertFalse(isValid);
    }
}