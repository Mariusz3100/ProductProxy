package mariusz.ambroziak.kassistant.hibernate.parsing.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("dependency")
public class DependencyPhraseConsidered extends  PhraseConsidered{



    @OneToOne
    private SavedToken head;

    @OneToOne
    private SavedToken child;


    public SavedToken getHead() {
        return head;
    }

    public void setHead(SavedToken head) {
        this.head = head;
    }

    public SavedToken getChild() {
        return child;
    }

    public void setChild(SavedToken child) {
        this.child = child;
    }
}
