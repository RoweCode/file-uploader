package wodrich.rowena.iapps.fileuploader.services;


import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wodrich.rowena.iapps.fileuploader.domain.FileData;
import wodrich.rowena.iapps.fileuploader.domain.Screen;
import wodrich.rowena.iapps.fileuploader.repositories.FileRepository;
import wodrich.rowena.iapps.fileuploader.repositories.NewspaperRepository;
import wodrich.rowena.iapps.fileuploader.repositories.ScreenRepository;

import java.util.List;

@Service
public class FileService {
    @Value( "${pagination.numberOfElements}" )
    private Integer NUMBER_OF_ELEMENTS_PER_PAGE;
    private final FileRepository fileRepository;
    private final ScreenRepository screenRepository;
    private final NewspaperRepository newspaperRepository;

    public FileService(FileRepository fileRepository,
                       ScreenRepository screenRepository,
                       NewspaperRepository newspaperRepository
    ) {
        this.fileRepository = fileRepository;
        this.screenRepository = screenRepository;
        this.newspaperRepository = newspaperRepository;
    }

    public void storeFileDate(FileData fileData) {
        Screen screen = fileData.getScreen();
        Screen foundScreen = screenRepository.findByWidthAndHeightAndDpi(
                screen.getWidth(), screen.getHeight(), screen.getDpi());
        if (foundScreen != null) {
            fileData.setScreen(foundScreen);
        } else {
            screenRepository.save(fileData.getScreen());
        }
        newspaperRepository.save(fileData.getNewspaper());
        fileRepository.save(fileData);
    }

    public List<FileData> getFileData(int page, String filterBy, String filter, String sortBy, Boolean asc) {

        if (page <= 0) return fileRepository.findAllBy(null);

        Pageable pageable = getPageable(page, sortBy, asc);
        if (Strings.isEmpty(filterBy)) {
            return fileRepository.findAllBy(pageable);
        }

        return getFilteredFileData(pageable, filterBy, filter);
    }

    private Pageable getPageable(int page, String sortBy, Boolean asc) {
        if (Strings.isNotEmpty(sortBy)) {
            Sort sorting = (asc == null || asc) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            return PageRequest.of(page - 1, NUMBER_OF_ELEMENTS_PER_PAGE, sorting);
        } else {
            return PageRequest.of(page - 1, NUMBER_OF_ELEMENTS_PER_PAGE);
        }
    }

    private List<FileData> getFilteredFileData(Pageable pageable, String filterBy, String filter) {
        return switch (filterBy) {
            case "name" -> fileRepository.findAllByName(filter, pageable);
            case "newspaperName" -> fileRepository.findAllByNewspaper_Name(filter, pageable);
            case "screenWidth" -> fileRepository.findAllByScreen_Width(Integer.valueOf(filter), pageable);
            case "screenHeight" -> fileRepository.findAllByScreen_Height(Integer.valueOf(filter), pageable);
            case "screenDpi" -> fileRepository.findAllByScreen_Dpi(Integer.valueOf(filter), pageable);
            default -> fileRepository.findAllBy(pageable);
        };
    }
}
