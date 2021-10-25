package ru.itis.kpfu.facescan.models.dto;

import com.fasterxml.jackson.annotation.*;
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
public class AnalyzeSpecsWrapper {

    private List<AnalyzeSpecs> analyzeSpecs;


    @JsonValue
    public List<AnalyzeSpecs> getAnalyzeSpecs() {
        return analyzeSpecs;
    }
}
