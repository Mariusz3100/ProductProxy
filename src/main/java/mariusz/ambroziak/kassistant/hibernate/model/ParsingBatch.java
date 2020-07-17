package mariusz.ambroziak.kassistant.hibernate.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Parsing_Batch",schema = "parsing")
public class ParsingBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pb_id;
    private LocalDateTime executionStart;
    private String description;


    public int getPb_id() {
        return pb_id;
    }

    public void setPb_id(int pb_id) {
        this.pb_id = pb_id;
    }

    public LocalDateTime getExecutionStart() {
        return executionStart;
    }

    public void setExecutionStart(LocalDateTime executionStart) {
        this.executionStart = executionStart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParsingBatch() {
        this.executionStart=LocalDateTime.now();
    }
}
