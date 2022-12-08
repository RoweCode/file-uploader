package wodrich.rowena.iapps.fileuploader.deserialization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wodrich.rowena.iapps.fileuploader.deserialization.XMLFileDeSerializationService;
import wodrich.rowena.iapps.fileuploader.deserialization.xml.EpaperRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class XMLFileDeSerializationServiceUnitTest {

    private final XMLFileDeSerializationService xmlFileDeSerializationService = new XMLFileDeSerializationService();

    @Test
    void testDeserializeObjectSuccess() throws IOException {
        byte[] fileBytes = getXMLFileBytes("valid-test-file.xml");

        // when
        EpaperRequest epaperRequest = xmlFileDeSerializationService.deserializeObject(fileBytes);

        // then
        assertNotNull(epaperRequest);
        assertNotNull(epaperRequest.getDeviceInfo());
        assertNotNull(epaperRequest.getGetPages());
        assertNotNull(epaperRequest.getDeviceInfo().getOsInfo());
        assertNotNull(epaperRequest.getDeviceInfo().getAppInfo());
        assertNotNull(epaperRequest.getDeviceInfo().getScreenInfo());
        assertEquals(1280, epaperRequest.getDeviceInfo().getScreenInfo().getWidth());
        assertEquals(752, epaperRequest.getDeviceInfo().getScreenInfo().getHeight());
        assertEquals(160, epaperRequest.getDeviceInfo().getScreenInfo().getDpi());
        assertEquals("abb", epaperRequest.getDeviceInfo().getAppInfo().getNewspaperName());
    }

    private byte[] getXMLFileBytes(String filePath) throws IOException {
        File resource = new ClassPathResource("testFiles/" + filePath).getFile();
        return Files.readAllBytes(resource.toPath());
    }
}