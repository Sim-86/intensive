package movieTicket;

import movieTicket.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentSuccess_SendNotification(@Payload PaymentSuccess paymentSuccess){

        if(paymentSuccess.isMe()){
            System.out.println("##### listener SendNotification : " + paymentSuccess.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCancelSuccess_SendNotification(@Payload PaymentCancelSuccess paymentCancelSuccess){

        if(paymentCancelSuccess.isMe()){
            System.out.println("##### listener SendNotification : " + paymentCancelSuccess.toJson());
        }
    }

}
