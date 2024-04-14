package pl.questionansweringsystem.recording;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordingService {

    private final RecordingRepository repository;

    @Value("${files.path}")
    private String filesPath;

    public List<RecordingDTO> getAll(Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Recording> recordings = repository.findAll(pageRequest);
        if (recordings.isEmpty()) throw new EntityNotFoundException("Any entity of type Recording does not exist.");
        return recordings.stream()
                .map(RecordingDTO::new)
                .collect(Collectors.toList());
    }

    public RecordingDTO getById(Long id) {
        Optional<Recording> recording = repository.findById(id);
        if (recording.isEmpty()) throw new EntityNotFoundException("Entity Recording with ID " + id + " not found.");
        return new RecordingDTO(recording.get());
    }

    public void add(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new FileNotFoundException("Uploaded file is empty.");
        Path path = Paths.get(filesPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        byte[] fileBytes = file.getBytes();
        path = Paths.get(path.toFile().getAbsolutePath() + "\\" + file.getOriginalFilename());
        Path savedFile = Files.write(path, fileBytes);
        if (!Files.exists(savedFile)) throw new IOException("Error during file save.");
        Recording recording = new Recording(savedFile.toFile().getAbsolutePath(), LocalDateTime.now());
        repository.save(recording);
    }

    public void deleteById(Long id) {
        Optional<Recording> recording = repository.findById(id);
        if (recording.isEmpty()) throw new EntityNotFoundException("Entity Recording with ID " + id + " not found.");
        repository.delete(recording.get());
    }
}
