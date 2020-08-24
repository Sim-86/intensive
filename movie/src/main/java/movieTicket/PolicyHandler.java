package movieTicket;

import movieTicket.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyHandler{

    @Autowired
    MovieRepository movieRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Long insert(MovieSaveRequestDto movieSaveRequestDto) {
        return Long.valueOf(1);
    }
}
