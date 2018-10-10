package uk.gov.dvla.osl.commons.queue;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.osl.commons.email.EmailHyperLinkConfig;
import uk.gov.dvla.osl.commons.email.EmailConfiguration;

public class EmailQueue {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailQueue.class);
    private final EmailQueueConfiguration emailQueueConfiguration;

    public AmazonSQS getAmazonSQS() {
        return amazonSQS;
    }

    private final AmazonSQS amazonSQS;
    private final String queueUrl;
    private final String passwordResetUrl;
    private final String fromEmailAddress;
    private final String emailSubject;
    private final String userHtmlTemplateName;
    private final String userTextTemplateName;

    public EmailQueue(EmailQueueConfiguration emailQueueConfiguration, EmailConfiguration emailConfiguration) {
        this.emailQueueConfiguration = emailQueueConfiguration;
        this.amazonSQS = getEmailQueueClient();
        this.queueUrl = emailQueueConfiguration.getQueueUrl();
        this.passwordResetUrl = constructUserEmailHyperlink(emailConfiguration);
        this.fromEmailAddress = emailConfiguration.getFromEmailAddress();
        this.emailSubject = emailConfiguration.getSubject();
        this.userHtmlTemplateName = emailConfiguration.getHtmlTemplateName();
        this.userTextTemplateName = emailConfiguration.getTextTemplateName();
    }

    private AmazonSQS getEmailQueueClient() {
        LOGGER.debug("Getting Email Queue Client - Started.");
        AwsClientBuilder.EndpointConfiguration endpointConfigurationSQS =
                new AwsClientBuilder.EndpointConfiguration(
                        emailQueueConfiguration.getSqsEndpoint(),
                        emailQueueConfiguration.getRegion());

        AmazonSQSClientBuilder sqsClientBuilder = AmazonSQSClientBuilder.standard();
        ClientConfiguration clientConfigurationSQS = new ClientConfiguration();
        clientConfigurationSQS.setProxyHost(emailQueueConfiguration.getSqsProxy().getHost());
        clientConfigurationSQS.setProxyPort(emailQueueConfiguration.getSqsProxy().getPort());
        sqsClientBuilder.setClientConfiguration(clientConfigurationSQS);
        sqsClientBuilder.setEndpointConfiguration(endpointConfigurationSQS);

        LOGGER.debug("Getting Email Queue Client - Completed with credentials :{}.", sqsClientBuilder.getCredentials());
        return sqsClientBuilder.build();
    }

    public String getQueueUrl() {
        return queueUrl;
    }

    public  String getPasswordResetUrl() {
        return passwordResetUrl;
    }

    public String getFromEmailAddress() {
        return fromEmailAddress;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public String getUserHtmlTemplateName() {
        return userHtmlTemplateName;
    }

    public String getUserTextTemplateName() {
        return userTextTemplateName;
    }

    private String constructUserEmailHyperlink(EmailConfiguration emailConfiguration){

        EmailHyperLinkConfig emailHyperLinkConfig = emailConfiguration.getEmailHyperLinkConfig();
        return  emailHyperLinkConfig.getScheme()
                +"://"
                + emailHyperLinkConfig.getHost()
                +":"
                + emailHyperLinkConfig.getPort()
                + emailHyperLinkConfig.getPartialUrl();
    }
}
