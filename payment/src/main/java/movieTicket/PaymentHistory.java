package movieTicket;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="PaymentHistory_table")
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @PrePersist
    public void onPrePersist(){
        PaymentSuccess paymentSuccess = new PaymentSuccess();
        BeanUtils.copyProperties(this, paymentSuccess);
        paymentSuccess.publishAfterCommit();


        PaymentCancelSuccess paymentCancelSuccess = new PaymentCancelSuccess();
        BeanUtils.copyProperties(this, paymentCancelSuccess);
        paymentCancelSuccess.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




}
