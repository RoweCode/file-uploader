package wodrich.rowena.iapps.fileuploader.services;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wodrich.rowena.iapps.fileuploader.domain.FileData;
import wodrich.rowena.iapps.fileuploader.domain.Screen;
import wodrich.rowena.iapps.fileuploader.repositories.FileRepository;
import wodrich.rowena.iapps.fileuploader.repositories.NewspaperRepository;
import wodrich.rowena.iapps.fileuploader.repositories.ScreenRepository;

import java.util.List;

@Service
public class FileService {

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

    public List<FileData> getFileData(int page) {
        if (page <= 0) return null;
        Pageable pageable = PageRequest.of(page - 1, 2);
        return fileRepository.findAllBy(pageable);
    }
}
