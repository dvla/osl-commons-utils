package uk.gov.dvla.osl.commons.queue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor( access = AccessLevel.PUBLIC )
@NoArgsConstructor
@Builder
public class EmailQueueConfiguration {

    @NotEmpty
    @JsonProperty("region")
    private String region;
    @NotEmpty
    @JsonProperty("queueUrl")
    private String queueUrl;
    @NotEmpty
    @JsonProperty("sqsEndpoint")
    private String sqsEndpoint;
    @NotNull
    @JsonProperty("sqsProxy")
    private SQSProxyConfiguration sqsProxy;

}
