package ru.itis.kpfu.facescan.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zagir Dingizbaev
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vertices {
    @JsonProperty("vertices")
    private List<Coordinates> coordinates;

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }
}
