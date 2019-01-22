package at.sentiment.report.sentiment_analysis.classification;

import at.sentiment.report.sentiment_analysis.features.FeatureVector;

public class Sample {

    private FeatureVector featureVector;
    private Sentiment sentiment;

    public Sample(FeatureVector featureVector, Sentiment sentiment){
        this.featureVector = featureVector;
        this.sentiment = sentiment;
    }

    public Sample(){

    }

    public FeatureVector getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(FeatureVector featureVector) {
        this.featureVector = featureVector;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "featureVector=" + featureVector +
                ", sentiment=" + sentiment +
                '}';
    }
}
