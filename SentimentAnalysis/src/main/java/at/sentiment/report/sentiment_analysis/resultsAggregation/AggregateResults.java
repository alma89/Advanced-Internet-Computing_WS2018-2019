package at.sentiment.report.sentiment_analysis.resultsAggregation;

import at.sentiment.report.sentiment_analysis.Tweet;
import at.sentiment.report.sentiment_analysis.classification.Sentiment;

import java.util.HashMap;
import java.util.Map;

public class AggregateResults {

    private Map<Tweet,Sentiment> tweetSentimentMap;
    private int positiveTweetsCount;

    public AggregateResults(){
        this.tweetSentimentMap = new HashMap<>();
        this.positiveTweetsCount = 0;
    }

    public void putTweetSentiment(Tweet tweet, Sentiment sentiment){
        this.tweetSentimentMap.put(tweet,sentiment);

        if(sentiment == Sentiment.POSITIVE){
            positiveTweetsCount++;
        }
    }

    public Map<Tweet, Sentiment> getTweetSentimentMap(){
        return this.tweetSentimentMap;
    }

    public double getPositiveTweetsRatio(){
        return ((double) this.positiveTweetsCount / (double) this.tweetSentimentMap.size());
    }

    public double getNegativeTweetsRatio(){
        return (1 - getPositiveTweetsRatio());
    }

    public int getPositiveTweetsCount(){ return this.positiveTweetsCount; }

}
