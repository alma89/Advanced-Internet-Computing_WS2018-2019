package aic.tuwien.tweetscollector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.Status;

@RestController
public class SubmitController {
	private static Log logger = LogFactory.getLog(TwitterController.class);

	@RequestMapping("/submit")
	public List<TweetsCollectorResponse> getkeywords(@RequestParam("keywords") String kws, @RequestParam(value = "count", required = false) String strCount) {
		if (strCount == null) {
			strCount = "0";
		}

		List<TweetsCollectorResponse> results = new ArrayList<>();
		
		TwitterController twitter = new TwitterController();
		
		for (String kw : kws.split(",")) {
		
			TweetsCollectorResponse resp = new TweetsCollectorResponse();
			resp.setKeyword(kw);
			
			List<Tweet> resp_tweets=new ArrayList<>();
						
			List<Status> tweets = twitter.getTweets(kw, Integer.parseInt(strCount));
			for (Status st : tweets) {
				Tweet tw = new Tweet();
				tw.setId(st.getId());
				tw.setText(st.getText());
				tw.setLanguage(st.getLang());
				tw.setUser(st.getUser().getName());
				tw.setTimestamp(st.getCreatedAt());
				resp_tweets.add(tw);
			}
			resp.setTweets(resp_tweets);
			
			results.add(resp);
		}

		return results;
	}

}