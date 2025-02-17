package subway.application.service.output;

import subway.domain.Line;

import java.util.List;
import java.util.Optional;

public interface LineLoadRepository {

    Optional<Line> loadLine(Long createdLineId);

    List<Line> loadLines();

}
