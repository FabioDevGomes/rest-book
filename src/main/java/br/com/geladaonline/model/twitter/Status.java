package br.com.geladaonline.model.twitter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {
	
	@XmlElement(name = "created_at")
	private String createdAt;
	
	@XmlElement(name = "text")
	private String text;
	
	@XmlElement(name = "user")
	private User user;
	
	public String getCreatedAt() {
		return createdAt;
	}
//	public void setCreatedAt(String createdAt) {
//		this.createdAt = createdAt;
//	}
	public String getText() {
		return text;
	}
//	public void setText(String text) {
//		this.text = text;
//	}
	public User getUser() {
		return user;
	}
//	public void setUser(User user) {
//		this.user = user;
//	}
}
