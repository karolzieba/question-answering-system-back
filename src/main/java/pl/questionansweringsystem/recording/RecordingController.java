package pl.questionansweringsystem.recording;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.questionansweringsystem.recording.dto.RecordingResponse;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/recording")
@RequiredArgsConstructor
public class RecordingController {

    private final RecordingService service;

    @GetMapping("/all")
    public ResponseEntity<List<RecordingResponse>> getAll(@RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {
        try {
            return new ResponseEntity<>(service.getAll(page, size), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<RecordingResponse> get(@RequestParam("id") Long id) {
        try {
            return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestParam("file") MultipartFile file) {
        try {
            service.add(file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestParam("id") Long id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}