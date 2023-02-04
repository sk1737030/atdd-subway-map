package subway.application.service.output;

import subway.domain.Section;

import java.util.List;

public interface SectionLoadRepository {

    Section loadSection(Long sectionId);

    List<Section> loadLineSection(Long loadLineId);

}
