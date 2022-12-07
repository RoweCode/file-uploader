package wodrich.rowena.iapps.fileuploader.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FileController {

    @GetMapping("files/pages/{pageNumber}")
    public int getFiles(@PathVariable Integer pageNumber) {
        return pageNumber;
    }
}
