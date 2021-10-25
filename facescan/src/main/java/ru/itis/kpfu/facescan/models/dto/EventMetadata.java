
package ru.itis.kpfu.facescan.models.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "event_id",
    "event_type",
    "created_at",
    "tracing_context",
    "cloud_id",
    "folder_id"
})
@Generated("jsonschema2pojo")
public class EventMetadata {

    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("event_type")
    private String eventType;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("tracing_context")
    private TracingContext tracingContext;
    @JsonProperty("cloud_id")
    private String cloudId;
    @JsonProperty("folder_id")
    private String folderId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
