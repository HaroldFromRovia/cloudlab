package ru.itis.kpfu.facescan.models.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zagir Dingizbaev
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature {
    private String type;

//    @JsonValue
//    public String getFeature() {
//        return "{" + feature + "}";
//    }
}
