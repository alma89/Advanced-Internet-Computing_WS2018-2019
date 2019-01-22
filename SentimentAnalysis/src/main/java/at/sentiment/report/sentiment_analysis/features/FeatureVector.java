package at.sentiment.report.sentiment_analysis.features;

/* Represents a collection of features */

import java.util.List;

public class FeatureVector {

    private List<Feature> features;
    private String originalMessage;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    @Override
    public String toString(){
        return features.toString();
    }
}
