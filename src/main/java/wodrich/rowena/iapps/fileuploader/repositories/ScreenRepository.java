package wodrich.rowena.iapps.fileuploader.repositories;

import org.springframework.data.repository.CrudRepository;
import wodrich.rowena.iapps.fileuploader.domain.Screen;

public interface ScreenRepository extends CrudRepository<Screen, Long> {

    Screen findByWidthAndHeightAndDpi(Integer width, Integer height, Integer dpi);
}
