package at.sentiment.report.sentiment_analysis;
import java.util.Date;

public class Tweet {

	private long id;
	private String text;
	private String user;
	private String language;
	private Date timestamp;

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getUser() {
		return user;
	}

	public String getLanguage() {
		return language;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Tweet() {

	}
}