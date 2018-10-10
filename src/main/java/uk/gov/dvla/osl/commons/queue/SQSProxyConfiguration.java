package uk.gov.dvla.osl.commons.queue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@AllArgsConstructor( access = AccessLevel.PUBLIC )
@NoArgsConstructor
@Builder
public class SQSProxyConfiguration {
    @NotEmpty
    @JsonProperty
    private String host;
    @NotEmpty
    @JsonProperty
    private Integer port;
}
