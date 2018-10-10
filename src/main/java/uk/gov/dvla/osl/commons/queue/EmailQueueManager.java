package uk.gov.dvla.osl.commons.queue;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.osl.commons.encryption.Encryption;

import java.util.Base64;

public class EmailQueueManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailQueueManager.class);

    public void sendUserMessageToQueue(EmailQueue emailQueue, Encryption encryption, String emailMessage, String email) {
        SendMessageRequest sendMessageRequest = null;

        try {
            // Don't log the whole message for security reasons.
            LOGGER.debug("Email message created for email {}", email);
            //Encrypt and encode Data before putting onto the queue
            byte[] encryptedData = encryption.encrypt(emailMessage.getBytes());
            String encryptedDataAsBase64 = Base64.getEncoder().encodeToString(encryptedData);

            sendMessageRequest = new SendMessageRequest(emailQueue.getQueueUrl(), encryptedDataAsBase64);

            LOGGER.debug("SendMessageRequest created for email {}", email);
            emailQueue.getAmazonSQS().sendMessage(sendMessageRequest);
        } catch (Exception ex) {
            LOGGER.error("Unable to send user creation email message to rave email queue for email {}. Exception message is: {}.", email, ex.getMessage(), ex);
        }
    }
}
