package wodrich.rowena.iapps.fileuploader.repositories;

import org.springframework.data.repository.CrudRepository;
import wodrich.rowena.iapps.fileuploader.domain.Newspaper;

public interface NewspaperRepository extends CrudRepository<Newspaper, Long> {
}
