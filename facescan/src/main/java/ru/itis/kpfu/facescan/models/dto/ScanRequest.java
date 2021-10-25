package ru.itis.kpfu.facescan.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zagir Dingizbaev
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScanRequest {

    @JsonProperty("folderId")
    private String folder;
    @JsonProperty("analyze_specs")
    private AnalyzeSpecsWrapper analyzeSpecsWrapper;

}
