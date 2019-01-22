package at.sentiment.report.sentiment_analysis;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TweetsCollectorResponse {

    private List<Tweet> tweets;
    private String keyword;

    public TweetsCollectorResponse(List<Tweet> tweets, String keyword) {
        this.tweets = tweets;
        this.keyword = keyword;
    }

    public TweetsCollectorResponse() {
        this.tweets = new ArrayList<>();
        this.keyword = "unknown";
    }

    public List<Tweet> getTweets() {
        return this.tweets;
    }

    public String getkeyword() {
        return this.keyword;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}