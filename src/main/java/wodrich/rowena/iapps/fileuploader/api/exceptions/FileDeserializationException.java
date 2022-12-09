package wodrich.rowena.iapps.fileuploader.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "deserialization failed")
public class FileDeserializationException extends RuntimeException {
}
