package subway.application.service;

import org.springframework.stereotype.Service;
import subway.application.repository.LineRepositoryPort;
import subway.domain.LineCreateDomain;

@Service
class LineService implements LineUseCase {

    private final LineRepositoryPort lineRepositoryPort;

    LineService(LineRepositoryPort lineRepositoryPort) {
        this.lineRepositoryPort = lineRepositoryPort;
    }

    @Override
    public Long createLine(LineCreateDomain lineCreateDomain) {
        return lineRepositoryPort.createLine(lineCreateDomain);
    }

}
