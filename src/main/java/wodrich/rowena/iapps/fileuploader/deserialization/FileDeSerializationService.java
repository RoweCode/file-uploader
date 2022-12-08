package wodrich.rowena.iapps.fileuploader.deserialization;

import wodrich.rowena.iapps.fileuploader.domain.FileData;

import java.io.IOException;

public interface FileDeSerializationService {

    FileData getFileData(String fileName, byte[] fileBytes)  throws IOException;
}
