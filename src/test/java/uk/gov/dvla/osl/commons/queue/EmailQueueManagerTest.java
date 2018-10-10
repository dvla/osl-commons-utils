package uk.gov.dvla.osl.commons.queue;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.junit.Test;
import uk.gov.dvla.osl.commons.encryption.Encryption;
import uk.gov.dvla.osl.commons.encryption.EncryptionUtil;

import java.util.HashSet;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * simple test to simulate sending message to a queue to check the
 * manager logic.
 */
public class EmailQueueManagerTest {

    private static final String URL = "http://localhost:9200/reset-passwords/reset?token=";

    private static final String MESSAGE = "0123456789012345";

    private static final String EMAIL = "test@email.com";

    @Test
    public void testSendEmailToQueue() throws Exception  {
        EmailQueue emailQueue = mock(EmailQueue.class);

        Encryption encryption = new EncryptionUtil();

        // creating the account set up token
        String passwordResetToken = UUID.randomUUID().toString();

        AmazonSQS mockSQSClient = mock(AmazonSQS.class);

        when(emailQueue.getAmazonSQS()).thenReturn(mockSQSClient);
        when(emailQueue.getPasswordResetUrl()).thenReturn(URL + passwordResetToken);
        when(emailQueue.getQueueUrl()).thenReturn(URL);
        when(emailQueue.getEmailSubject()).thenReturn("test subject");

        when(mockSQSClient
                .sendMessage(any(SendMessageRequest.class))).thenReturn(mock(SendMessageResult.class));

        EmailQueueManager emailQueueManager = new EmailQueueManager();
        emailQueueManager.sendUserMessageToQueue(emailQueue, encryption, MESSAGE, EMAIL);

        verify(mockSQSClient).sendMessage(any(SendMessageRequest.class));
    }
}
