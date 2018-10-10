package uk.gov.dvla.osl.commons.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor( access = AccessLevel.PUBLIC )
@NoArgsConstructor
@Builder
public class EmailConfiguration {

    @NotNull
    @JsonProperty("emailHyperLinkConfig")
    private EmailHyperLinkConfig emailHyperLinkConfig;

    @NotNull
    @JsonProperty("fromEmailAddress")
    private String fromEmailAddress;

    @NotNull
    @JsonProperty("subject")
    private String subject;

    @NotNull
    @JsonProperty("htmlTemplateName")
    private String htmlTemplateName;

    @NotNull
    @JsonProperty("textTemplateName")
    private String textTemplateName;

}
