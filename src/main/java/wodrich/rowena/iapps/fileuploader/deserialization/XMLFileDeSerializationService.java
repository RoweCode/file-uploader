package wodrich.rowena.iapps.fileuploader.deserialization;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;
import wodrich.rowena.iapps.fileuploader.deserialization.xml.EpaperRequest;
import wodrich.rowena.iapps.fileuploader.deserialization.xml.ScreenInfo;
import wodrich.rowena.iapps.fileuploader.domain.FileData;
import wodrich.rowena.iapps.fileuploader.domain.Newspaper;
import wodrich.rowena.iapps.fileuploader.domain.Screen;

import java.io.IOException;
import java.time.ZonedDateTime;

@Service
public class XMLFileDeSerializationService implements FileDeSerializationService {

    public FileData getFileData(String fileName, byte[] fileBytes) throws IOException {
        EpaperRequest epaperRequest = deserializeObject(fileBytes);

        ScreenInfo screenInfo = epaperRequest.getDeviceInfo().getScreenInfo();
        Screen screen = new Screen(screenInfo.getWidth(), screenInfo.getHeight(), screenInfo.getDpi());
        String newspaperName = epaperRequest.getDeviceInfo().getAppInfo().getNewspaperName();

        return new FileData(fileName, ZonedDateTime.now(), screen, new Newspaper(newspaperName));
    }

    public EpaperRequest deserializeObject(byte[] xmlBytes) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xmlBytes, EpaperRequest.class);
    }
}
