package mariusz.ambroziak.kassistant.webclients.spacy.tokenization;

import java.util.Objects;

public class ConnectionEntry {

	Token head;
	Token child;
	
	
	public ConnectionEntry(Token head, Token child) {
		super();
		this.head = head;
		this.child = child;
	}
	public Token getHead() {
		return head;
	}
	public void setHead(Token head) {
		this.head = head;
	}
	public Token getChild() {
		return child;
	}
	public void setChild(Token child) {
		this.child = child;
	}
	@Override
	public String toString() {
		return child.getText() + "("+ head.getText()+")";
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ConnectionEntry)) return false;
		ConnectionEntry that = (ConnectionEntry) o;
		return Objects.equals(getHead(), that.getHead()) &&
				Objects.equals(getChild(), that.getChild());
	}

	public boolean permissiveEquals(ConnectionEntry e) {
		if (this == e) return true;
		return (Objects.equals(getHead().getLemma(), e.getHead().getLemma()) ||Objects.equals(getHead().getLemma(), e.getChild().getLemma()))&&
				(Objects.equals(getChild().getLemma(), e.getChild().getLemma()) ||Objects.equals(getChild().getLemma(), e.getHead().getLemma()))
				;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getHead(), getChild());
	}
}
