package pl.questionansweringsystem.recording;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordingRepository extends JpaRepository<Recording, Long> {

    Page<Recording> findAllByIdUser(Long idUser, Pageable pageable);
    Optional<Recording> findByIdRecordingAndIdUser(Long idRecording, Long idUser);
}
