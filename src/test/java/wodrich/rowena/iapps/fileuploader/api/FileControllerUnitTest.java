package wodrich.rowena.iapps.fileuploader.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import wodrich.rowena.iapps.fileuploader.deserialization.FileDeSerializationService;
import wodrich.rowena.iapps.fileuploader.domain.FileData;
import wodrich.rowena.iapps.fileuploader.services.FileStorageService;
import wodrich.rowena.iapps.fileuploader.validation.FileValidator;
import wodrich.rowena.iapps.fileuploader.validation.XMLFileValidator;

import java.io.File;
import java.nio.file.Files;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FileController.class)
class FileControllerUnitTest {

    public static final String VALID_TEST_FILE_XML = "valid-test-file.xml";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    FileValidator fileValidator;

    @MockBean
    FileDeSerializationService fileDeSerializationService;

    @MockBean
    FileStorageService fileStorageService;

    @Test
    void testGetFilesWithPaginationSuccess() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/files/pages/100");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("100"));
    }

    @Test
    void testUploadValidFileSuccess() throws Exception {
        when(fileValidator.isValidAgainstSchema(
                any(MultipartFile.class),
                eq(XMLFileValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH)))
                .thenReturn(true);

        File resource = new ClassPathResource("testFiles/" + VALID_TEST_FILE_XML).getFile();
        byte[] fileBytes = Files.readAllBytes(resource.toPath());
        MockMultipartFile file
                = new MockMultipartFile("file", VALID_TEST_FILE_XML,
                MediaType.TEXT_PLAIN_VALUE, fileBytes);

        when(fileDeSerializationService.getFileData(
                anyString(), eq(fileBytes))).thenReturn(new FileData());

        mockMvc.perform(multipart("/files").file(file))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"success\":true}"));
    }

    @Test
    void testUploadValidFile_ValidationFails() throws Exception {
        when(fileValidator.isValidAgainstSchema(
                any(MultipartFile.class),
                eq(XMLFileValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH)))
                .thenReturn(false);

        File resource = new ClassPathResource("testFiles/" + VALID_TEST_FILE_XML).getFile();
        byte[] fileBytes = Files.readAllBytes(resource.toPath());
        MockMultipartFile file
                = new MockMultipartFile("file", VALID_TEST_FILE_XML,
                MediaType.TEXT_PLAIN_VALUE, fileBytes);

        mockMvc.perform(multipart("/files").file(file))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"success\":false, \"error\": \"validation\"}"));
    }
}