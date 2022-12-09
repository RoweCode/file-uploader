package wodrich.rowena.iapps.fileuploader.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import wodrich.rowena.iapps.fileuploader.domain.FileData;

import java.util.List;

@Component
public interface FileRepository extends PagingAndSortingRepository<FileData, Long> {

    List<FileData> findAllBy(Pageable pageable);
    List<FileData> findAllByName(String name, Pageable pageable);
    List<FileData> findAllByNewspaper_Name(String newspaperName, Pageable pageable);
    List<FileData> findAllByScreen_Width(Integer width, Pageable pageable);
    List<FileData> findAllByScreen_Height(Integer height, Pageable pageable);
    List<FileData> findAllByScreen_Dpi(Integer dpi, Pageable pageable);
}
