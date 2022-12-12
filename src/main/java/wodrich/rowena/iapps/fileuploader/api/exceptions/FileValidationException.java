package wodrich.rowena.iapps.fileuploader.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "file is not valid")
public class FileValidationException extends RuntimeException {
}
