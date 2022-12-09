package wodrich.rowena.iapps.fileuploader.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wodrich.rowena.iapps.fileuploader.api.exceptions.FileDeserializationException;
import wodrich.rowena.iapps.fileuploader.api.exceptions.ValidationException;
import wodrich.rowena.iapps.fileuploader.api.exceptions.FileValidationException;
import wodrich.rowena.iapps.fileuploader.deserialization.FileDeSerializationService;
import wodrich.rowena.iapps.fileuploader.domain.FileData;
import wodrich.rowena.iapps.fileuploader.services.FileService;
import wodrich.rowena.iapps.fileuploader.validation.FileValidator;
import wodrich.rowena.iapps.fileuploader.validation.XMLFileValidator;

import java.io.IOException;
import java.util.List;


@RestController
public class FileController {

    private final FileValidator fileValidator;
    private final FileDeSerializationService fileDeSerializationService;
    private final FileService fileService;

    private final List<String> filterNames = List.of(new String[]{
            "name", "newspaperName", "screenWidth", "screenHeight", "screenDpi"});
    private final List<String> sortNames = List.of(new String[]{
            "name", "uploadedAt", "newspaperName", "screenWidth", "screenHeight", "screenDpi"});

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public FileController(FileValidator fileValidator,
                          FileDeSerializationService fileDeSerializationService,
                          FileService fileService
    ) {
        this.fileValidator = fileValidator;
        this.fileDeSerializationService = fileDeSerializationService;
        this.fileService = fileService;
    }

    @GetMapping("files/pages/{pageNumber}")
    public List<FileData> getFiles(
            @PathVariable Integer pageNumber,
            @RequestParam(required = false) String filterBy,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Boolean asc
    ) {
        if (pageNumber <= 0) {
            throw new ValidationException();
        }
        if (Strings.isNotEmpty(filterBy)) {
            if (!filterNames.contains(filterBy) || Strings.isEmpty(filter))
                throw new ValidationException();
        }
        if (Strings.isNotEmpty(sortBy) && !sortNames.contains(sortBy)) {
            throw new ValidationException();
        }
        return fileService.getFileData(pageNumber, filterBy, filter, sortBy, asc);
    }

    @PostMapping("files")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        boolean isValid = fileValidator.isValidAgainstSchema(file, XMLFileValidator.EPAPER_REQUEST_XSD_SCHEMA_PATH);
        if (!isValid) {
            throw new FileValidationException();
        }
        FileData fileData;
        try {
            fileData = fileDeSerializationService.getFileData(file.getOriginalFilename(), file.getBytes());
        } catch (IOException e) {
            throw new FileDeserializationException();
        }
        fileService.storeFileDate(fileData);
    }
}
