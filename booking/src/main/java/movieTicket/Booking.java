package movieTicket;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Booking_table")
public class Booking {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @PrePersist
    public void onPrePersist(){
        Booked booked = new Booked();
        BeanUtils.copyProperties(this, booked);
        booked.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        movieTicket.external.PaymentHistory paymentHistory = new movieTicket.external.PaymentHistory();
        // mappings goes here
        BookingApplication.applicationContext.getBean(movieTicket.external.PaymentHistoryService.class)
            .makePayment(paymentHistory);


        Unbooked unbooked = new Unbooked();
        BeanUtils.copyProperties(this, unbooked);
        unbooked.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




}
