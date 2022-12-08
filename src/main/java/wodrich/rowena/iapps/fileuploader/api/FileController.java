package wodrich.rowena.iapps.fileuploader.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wodrich.rowena.iapps.fileuploader.api.responses.FileUploadResponse;
import wodrich.rowena.iapps.fileuploader.domain.dto.FileData;
import wodrich.rowena.iapps.fileuploader.services.FileDeSerializationService;
import wodrich.rowena.iapps.fileuploader.services.FileStorageService;
import wodrich.rowena.iapps.fileuploader.validation.FileValidator;
import wodrich.rowena.iapps.fileuploader.validation.XMLFileValidator;

import java.io.IOException;


@RestController
public class FileController {

    private final FileValidator fileValidator;
    private final FileDeSerializationService fileDeSerializationService;
    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(FileValidator fileValidator, FileDeSerializationService fileDeSerializationService, FileStorageService fileStorageService) {
        this.fileValidator = fileValidator;
        this.fileDeSerializationService = fileDeSerializationService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("files/pages/{pageNumber}")
    public int getFiles(@PathVariable Integer pageNumber) {
        return pageNumber;
    }

    @PostMapping("files")
    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        boolean isValid = fileValidator.isValidAgainstSchema(file, XMLFileValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH);
        if (!isValid) {
            return new FileUploadResponse(false, "validation");
        }
        FileData fileData;
        try {
            fileData = fileDeSerializationService.getFileData(file.getOriginalFilename(), file.getBytes());
        } catch (IOException e) {
            return new FileUploadResponse(false, "internal");
        }
        fileStorageService.storeFileDate(fileData);
        return new FileUploadResponse(true);
    }
}
