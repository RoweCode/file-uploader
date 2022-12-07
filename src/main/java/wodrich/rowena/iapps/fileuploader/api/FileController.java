package wodrich.rowena.iapps.fileuploader.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wodrich.rowena.iapps.fileuploader.api.responses.FileUploadResponse;
import wodrich.rowena.iapps.fileuploader.validation.XMLValidator;

import java.io.File;


@RestController
public class FileController {

    private final XMLValidator xmlValidator;

    @Autowired
    public FileController(XMLValidator xmlValidator) {
        this.xmlValidator = xmlValidator;
    }

    @GetMapping("files/pages/{pageNumber}")
    public int getFiles(@PathVariable Integer pageNumber) {
        return pageNumber;
    }

    @PostMapping("files")
    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        boolean isValid = xmlValidator.isValidAgainstXSDSchema(file, XMLValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH);
        return isValid ? new FileUploadResponse(true)
                : new FileUploadResponse(false, "validation");
    }
}
