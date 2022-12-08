package wodrich.rowena.iapps.fileuploader.validation;

import org.springframework.web.multipart.MultipartFile;

public interface FileValidator {

    boolean isValidAgainstSchema(MultipartFile file, String schemaPath);
}
