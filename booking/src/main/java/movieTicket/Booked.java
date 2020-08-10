package movieTicket;

public class Booked extends AbstractEvent {

    private Long id;

    public Booked(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
