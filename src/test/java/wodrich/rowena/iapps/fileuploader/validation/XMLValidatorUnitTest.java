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
    public static final String MISSING_ELEMENT_TEST_FILE_XML = "missing-element.xml";
    public static final String MISSING_REQUIRED_ATTRIBUTE_TEST_FILE_XML = "missing-required-attribute.xml";
    public static final String WRONG_ATTRIBUTE_DATATYPE_TEST_FILE_XML = "wrong-attribute-datatype.xml";

    private final XMLValidator xmlValidator = new XMLValidator();

    @Test
    void testIsValidAgainstXSDSchema_Success() throws IOException {
        MockMultipartFile file = getMockMultipartFile(VALID_TEST_FILE_XML);
        // when
        boolean isValid = xmlValidator.isValidAgainstXSDSchema(file, XMLValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH);

        // then
        assertTrue(isValid);
    }

    @Test
    void testIsValidAgainstXSDSchema_InValidOrderOfElements() throws IOException {
        MockMultipartFile file = getMockMultipartFile(INVALID_ORDER_TEST_FILE_XML);

        // when
        boolean isValid = xmlValidator.isValidAgainstXSDSchema(file, XMLValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH);

        // then
        assertFalse(isValid);
    }

    @Test
    void testIsValidAgainstXSDSchema_MissingElement() throws IOException {
        MockMultipartFile file = getMockMultipartFile(MISSING_ELEMENT_TEST_FILE_XML);

        // when
        boolean isValid = xmlValidator.isValidAgainstXSDSchema(file, XMLValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH);

        // then
        assertFalse(isValid);
    }

    @Test
    void testIsValidAgainstXSDSchema_MissingRequiredAttributes() throws IOException {
        MockMultipartFile file = getMockMultipartFile(MISSING_REQUIRED_ATTRIBUTE_TEST_FILE_XML);

        // when
        boolean isValid = xmlValidator.isValidAgainstXSDSchema(file, XMLValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH);

        // then
        assertFalse(isValid);
    }

    @Test
    void testIsValidAgainstXSDSchema_WrongAttributeDatatype() throws IOException {
        MockMultipartFile file = getMockMultipartFile(WRONG_ATTRIBUTE_DATATYPE_TEST_FILE_XML);

        // when
        boolean isValid = xmlValidator.isValidAgainstXSDSchema(file, XMLValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH);

        // then
        assertFalse(isValid);
    }

    private MockMultipartFile getMockMultipartFile(String filePath) throws IOException {
        File resource = new ClassPathResource("testFiles/" + filePath).getFile();
        byte[] fileBytes = Files.readAllBytes(resource.toPath());
        MockMultipartFile file
                = new MockMultipartFile("file", filePath,
                MediaType.TEXT_PLAIN_VALUE, fileBytes);
        return file;
    }
}