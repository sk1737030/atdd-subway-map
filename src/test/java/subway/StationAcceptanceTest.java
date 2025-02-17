package subway;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철역 관련 기능")
class StationAcceptanceTest extends BaseAcceptance {

    private static final String 강남역 = "강남역";
    private static final String 논현역 = "논현역";

    /**
     * When 지하철역을 생성하면
     * Then 지하철역이 생성된다
     * Then 지하철역 목록 조회 시 생성한 역을 찾을 수 있다
     */
    @DisplayName("지하철역을 생성한다.")
    @Test
    void createStation() {
        // when
        ExtractableResponse<Response> response = createStation(강남역);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        // then
        List<String> stationNames =
            RestAssured.given().spec(REQUEST_SPEC).log().all()
                .when().get("/stations")
                .then().log().all()
                .extract().jsonPath().getList("name", String.class);
        assertThat(stationNames).containsAnyOf(강남역);
    }

    /**
     * Given 2개의 지하철역을 생성하고
     * When 지하철역 목록을 조회하면
     * Then 2개의 지하철역을 응답 받는다
     */
    @DisplayName("지하철역 목록조회 할 수 있다")
    @Test
    void getStations() {
        // Given
        createStation(강남역);
        createStation(논현역);

        // When
        ExtractableResponse<Response> response =
            RestAssured.given().spec(REQUEST_SPEC).log().all()
                .when().get("/stations")
                .then().log().all()
                .extract();

        // then
        List<String> stationNames = response.jsonPath().getList("name", String.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(stationNames).containsExactlyInAnyOrder(강남역, 논현역);
    }

    /**
     * Given 지하철역을 생성하고
     * When 그 지하철역을 삭제하면
     * Then 그 지하철역 목록 조회 시 생성한 역을 찾을 수 없다
     */
    @DisplayName("지하철역을 삭제 할 수 있다")
    @Test
    void deleteStation() {
        // Given
        ExtractableResponse<Response> givenResponse = createStation(강남역);
        Integer givenStationId = givenResponse.body().jsonPath().get("id");

        // When
        ExtractableResponse<Response> deleteResponse = RestAssured.given().spec(REQUEST_SPEC).log().all()
            .pathParam("stationId", givenStationId)
            .when().delete("/stations/{stationId}")
            .then().log().all()
            .extract();

        // then
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        ExtractableResponse<Response> response = RestAssured.given().spec(REQUEST_SPEC).log().all()
            .when().get("/stations")
            .then().log().all()
            .extract();

        List<String> stationNames = response.jsonPath().getList("name", String.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(stationNames).isEmpty();
    }

    private static ExtractableResponse<Response> createStation(String stationName) {
        Map<String, String> params = new HashMap<>();
        params.put("name", stationName);

        ExtractableResponse<Response> response = RestAssured.given().spec(REQUEST_SPEC).log().all()
            .body(params)
            .when().post("/stations")
            .then().log().all()
            .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        return response;
    }

}
