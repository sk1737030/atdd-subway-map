package subway.infrastructor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<StationJpaEntity, Long> {
}