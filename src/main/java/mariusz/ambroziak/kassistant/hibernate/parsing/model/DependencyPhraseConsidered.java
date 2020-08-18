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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DependencyPhraseConsidered)) {
            return false;
        } else {
            DependencyPhraseConsidered that = (DependencyPhraseConsidered) o;
            if ((getChild().equals(that.getChild()) && getHead().equals(that.getHead()))
                    || (getChild().getText().equals(that.getChild().getText()) && getHead().getText().equals(that.getHead().getText()))
            ) {
                return true;
            } else {
                return false;
            }
        }

    }


    public boolean lemmasEqualOrBetter(DependencyPhraseConsidered that) {

        if ((getChild().equals(that.getChild()) && getHead().equals(that.getHead()))
                || (getChild().getText().equals(that.getChild().getText()) && getHead().getText().equals(that.getHead().getText()))
                || (getChild().getLemma().equals(that.getChild().getLemma()) && getHead().getLemma().equals(that.getHead().getLemma()))
        ) {
            return true;
        } else {
            return false;
        }


    }


    public boolean equalsWithHeadAndChildReversedOrBetter(DependencyPhraseConsidered that) {

        if ((getChild().equals(that.getChild()) && getHead().equals(that.getHead()))
                || (getChild().getText().equals(that.getChild().getText()) && getHead().getText().equals(that.getHead().getText()))
                || (getChild().getLemma().equals(that.getChild().getLemma()) && getHead().getLemma().equals(that.getHead().getLemma()))
                || (getHead().getText().equals(that.getChild().getText()) && getChild().getText().equals(that.getHead().getText()))
                || (getHead().getLemma().equals(that.getChild().getLemma()) && getChild().getLemma().equals(that.getHead().getLemma()))
        ) {
            return true;
        } else {
            return false;
        }


    }

}

