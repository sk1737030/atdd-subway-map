package subway.web.response;

import subway.domain.Line;
import subway.domain.Station;

import java.util.List;
import java.util.Objects;

public class LineResponse {

    private Long id;
    private String name;
    private String color;
    private List<StationResponse> stations;

    public LineResponse(Long id, String name, String color, List<StationResponse> stations) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.stations = stations;
    }

    public static LineResponse from(Line loadLine) {
        Station upStation = loadLine.getUpStation();
        Station downStation = loadLine.getDownStation();

        List<StationResponse> stations = List.of(new StationResponse(upStation.getId(), upStation.getName()), new StationResponse(downStation.getId(), downStation.getName()));
        return new LineResponse(loadLine.getId(), loadLine.getName(), loadLine.getColor(), stations);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineResponse that = (LineResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
