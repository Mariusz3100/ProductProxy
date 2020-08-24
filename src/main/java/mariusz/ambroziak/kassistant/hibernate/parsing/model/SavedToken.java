package mariusz.ambroziak.kassistant.hibernate.parsing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import mariusz.ambroziak.kassistant.enums.WordType;
import mariusz.ambroziak.kassistant.pojos.QualifiedToken;
import mariusz.ambroziak.kassistant.webclients.spacy.tokenization.Token;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SavedToken",schema = "parsing",uniqueConstraints=
@UniqueConstraint(columnNames={"text", "lemma","tag","pos"}))

public class SavedToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int token_id;

	private WordType wordType;


	private String text;
	private String lemma;
	private String tag;
//	private String relationToParentType;
	private String pos;
	//private String head;


	public int getToken_id() {
		return token_id;
	}

	public void setToken_id(int token_id) {
		this.token_id = token_id;
	}

	public WordType getWordType() {
		return wordType;
	}

	public void setWordType(WordType wordType) {
		this.wordType = wordType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLemma() {
		return lemma;
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

//	public String getRelationToParentType() {
//		return relationToParentType;
//	}
//
//	public void setRelationToParentType(String relationToParentType) {
//		this.relationToParentType = relationToParentType;
//	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

//	public String getHead() {
//		return head;
//	}

//	public void setHead(String head) {
//		this.head = head;
//	}

	public SavedToken() {
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SavedToken)) return false;
		SavedToken that = (SavedToken) o;
		return	Objects.equals(getText(), that.getText()) &&
				Objects.equals(getLemma(), that.getLemma()) &&
				Objects.equals(getTag(), that.getTag()) &&
		//		Objects.equals(getRelationToParentType(), that.getRelationToParentType()) &&
				Objects.equals(getPos(), that.getPos());
		//		Objects.equals(getHead(), that.getHead());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getWordType(), getText(), getLemma(), getTag(),  getPos());
	}

	public SavedToken(Token originalToken) {

		setText(originalToken.getText());
		setLemma(originalToken.getLemma());
		setTag(originalToken.getTag());
		setWordType(wordType);
	//	setHead(originalToken.getHead());
	//	setRelationToParentType(originalToken.getRelationToParentType());
		setPos(originalToken.getPos());



	}

	public SavedToken(QualifiedToken originalToken) {
		setText(originalToken.getText());
		setLemma(originalToken.getLemma());
		setTag(originalToken.getTag());
		setWordType(originalToken.getWordType());
	//	setHead(originalToken.getHead());
	//	setRelationToParentType(originalToken.getRelationToParentType());
		setPos(originalToken.getPos());



	}

	public boolean equalsToken(Token token) {

		return	Objects.equals(getText(), token.getText()) &&
				Objects.equals(getLemma(), token.getLemma());
			//	Objects.equals(getTag(), token.getTag()) &&
			//	Objects.equals(getRelationToParentType(), token.getRelationToParentType()) &&
			//	Objects.equals(getPos(), token.getPos());
			//	Objects.equals(getHead(), token.getHead());
	}

	public boolean lemmasEqual(Token token) {

		return	Objects.equals(getLemma(), token.getLemma());

	}

}