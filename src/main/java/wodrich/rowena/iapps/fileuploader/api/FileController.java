package wodrich.rowena.iapps.fileuploader.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class FileController {

    @GetMapping("files/pages/{pageNumber}")
    public int getFiles(@PathVariable Integer pageNumber) {
        return pageNumber;
    }

    @PostMapping("files")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return "{\"name\":" + file.getOriginalFilename() + "}";
    }
}
