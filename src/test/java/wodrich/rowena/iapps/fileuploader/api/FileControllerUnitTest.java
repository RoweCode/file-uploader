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
import wodrich.rowena.iapps.fileuploader.domain.Newspaper;
import wodrich.rowena.iapps.fileuploader.domain.Screen;
import wodrich.rowena.iapps.fileuploader.services.FileService;
import wodrich.rowena.iapps.fileuploader.validation.FileValidator;
import wodrich.rowena.iapps.fileuploader.validation.XMLFileValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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
    FileService fileService;

    @Test
    void testGetFilesWithPaginationSuccess() throws Exception {
        List<FileData> fileDataList = getFileDataList();
        when(fileService.getFileData(100, null, null, null, null))
                .thenReturn(fileDataList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/files/pages/100");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetFilesWithPagination_InvalidPageNumberReturnsAllFiles() throws Exception {
        List<FileData> fileDataList = getFileDataList();
        when(fileService.getFileData(0, null, null, null, null))
                .thenReturn(fileDataList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/files/pages/0");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
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
                .andExpect(status().isOk());
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
                .andExpect(status().is(400));
    }

    @Test
    void testUploadValidFile_DeserializationFails() throws Exception {
        when(fileValidator.isValidAgainstSchema(
                any(MultipartFile.class),
                eq(XMLFileValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH)))
                .thenReturn(true);

        when(fileDeSerializationService.getFileData(anyString(), any()))
                .thenThrow(IOException.class);

        File resource = new ClassPathResource("testFiles/" + VALID_TEST_FILE_XML).getFile();
        byte[] fileBytes = Files.readAllBytes(resource.toPath());
        MockMultipartFile file
                = new MockMultipartFile("file", VALID_TEST_FILE_XML,
                MediaType.TEXT_PLAIN_VALUE, fileBytes);

        mockMvc.perform(multipart("/files").file(file))
                .andExpect(status().is(500));
    }

    private List<FileData> getFileDataList() {
        List<FileData> fileDataList = new ArrayList<>();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2022, 2,2,
                12,0,0,0, ZoneId.of("Europe/Berlin"));
        Screen screen = new Screen(1,2,3);
        Newspaper newspaper = new Newspaper("taz");
        FileData fileData = new FileData("test-1", zonedDateTime, screen, newspaper);
        fileDataList.add(fileData);
        return fileDataList;
    }
}