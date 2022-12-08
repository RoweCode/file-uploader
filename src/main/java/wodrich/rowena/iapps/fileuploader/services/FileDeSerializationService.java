package wodrich.rowena.iapps.fileuploader.services;

import wodrich.rowena.iapps.fileuploader.domain.dto.FileData;

import java.io.IOException;

public interface FileDeSerializationService {

    FileData getFileData(String fileName, byte[] fileBytes)  throws IOException;
}
