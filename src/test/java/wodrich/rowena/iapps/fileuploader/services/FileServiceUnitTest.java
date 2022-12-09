package wodrich.rowena.iapps.fileuploader.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wodrich.rowena.iapps.fileuploader.domain.FileData;
import wodrich.rowena.iapps.fileuploader.domain.Newspaper;
import wodrich.rowena.iapps.fileuploader.domain.Screen;
import wodrich.rowena.iapps.fileuploader.repositories.FileRepository;
import wodrich.rowena.iapps.fileuploader.repositories.NewspaperRepository;
import wodrich.rowena.iapps.fileuploader.repositories.ScreenRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class FileServiceUnitTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    FileService fileService;

    @MockBean
    ScreenRepository screenRepository;

    @MockBean
    NewspaperRepository newspaperRepository;

    @MockBean
    FileRepository fileRepository;

    @Test
    void testStoreFileDateSuccess() {

        when(screenRepository.findByWidthAndHeightAndDpi(1,2,3)).thenReturn(null);

        // when
        fileService.storeFileDate(getFileDataList().get(0));

        // then
        verify(screenRepository).findByWidthAndHeightAndDpi(1,2,3);
        verify(screenRepository).save(any());
        verify(newspaperRepository).save(any());
        verify(fileRepository).save(any());
    }

    @Test
    void testGetFileDataSuccess() {
        when(fileRepository.findAllBy(PageRequest.of(0, 2)))
                .thenReturn(getFileDataList());

        // when
        List<FileData> fileDataList = fileService.getFileData(1, null, null);

        // then
        assertNotNull(fileDataList);
        assertEquals(1, fileDataList.size());
        assertNotNull(fileDataList.get(0).getScreen());
        assertEquals(3, fileDataList.get(0).getScreen().getDpi());
    }

    @Test
    void testGetFileData_PageNumberTooLow() {

        // when
        List<FileData> fileDataList = fileService.getFileData(0, null, null);

        // then
        assertNull(fileDataList);
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