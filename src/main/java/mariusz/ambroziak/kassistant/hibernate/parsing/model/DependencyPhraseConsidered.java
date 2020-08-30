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

    public boolean checkCompatibility(String text1,String text2){
       return  isChildTextOrLemmaEqualTo(text1)||isHeadTextOrLemmaEqualTo(text2)
               ||isChildTextOrLemmaEqualTo(text2)||isHeadTextOrLemmaEqualTo(text1);

    }


    public boolean isHeadTextOrLemmaEqualTo(String text){
        return getHead().getText().equals(text)||getHead().getLemma().equals(text);

    }

    public boolean isChildTextOrLemmaEqualTo(String text){
        return getChild().getText().equals(text)||getChild().getLemma().equals(text);

    }

    @Override
    public String toString(){
        return "["+getHead().getText()+"]("+getChild().getText()+")";
    }

}

