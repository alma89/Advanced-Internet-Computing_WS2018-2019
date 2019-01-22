package aic.tuwien.tweetscollector;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class TwitterController {

	private static Twitter twitter;

	@Value("${twitter.api_key}")
	private String TWITTER_API_KEY = "[unknown]";

	@Value("${twitter.api_secret}")
	private String TWITTER_API_SECRET = "[unknown]";

	@Value("${twitter.access_token}")
	private String TWITTER_ACCESS_TOKEN = "[unknown]";

	@Value("${twitter.access_token_secret}")
	private String TWITTER_ACCESS_TOKEN_SECRET = "[unknown]";

	@Value("${twitter.obtain_url}")
	private String TWITTER_OBTAIN_URL = "[unknown]";

	@Value("${twitter.app_only_authentication}")
	private String TWITTER_APP_ONLY_AUTHENTICATION = "[unknown]";

	@Value("${twitter.request_token_url}")
	private String TWITTER_REQUEST_TOKEN_URL = "[unknown]";

	@Value("${twitter.authorize_url}")
	private String TWITTER_AUTHORIZE_URL = "[unknown]";

	@Value("${twitter.access_token_url}")
	private String TWITTER_ACCESS_TOKEN_URL = "[unknown]";

	private static Log logger = LogFactory.getLog(TwitterController.class);

	@PostConstruct
	public void init() {
		if (twitter == null) {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setOAuthConsumerKey(TWITTER_API_KEY);
			cb.setOAuthConsumerSecret(TWITTER_API_SECRET);
			cb.setOAuthAccessToken(TWITTER_ACCESS_TOKEN);
			cb.setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
			twitter = new TwitterFactory(cb.build()).getInstance();
		}
	}

	public TwitterController() {

	}

	public List<Status> getTweets(String keyword, int count) {
		ArrayList<Status> tweets = new ArrayList<Status>();

		if (count <= 0 || count > 500) {
			count = 10;
		}

		try {
			Query query = new Query(keyword);
			query.setLang("en");
			query.setCount(count);
			QueryResult result = twitter.search(query);
			tweets.addAll(result.getTweets());

		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return tweets;
	}

}
