package pl.questionansweringsystem.recording;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordingRepository extends JpaRepository<Recording, Long> {
}
