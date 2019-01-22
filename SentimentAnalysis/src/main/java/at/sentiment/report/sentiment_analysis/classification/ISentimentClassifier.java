package at.sentiment.report.sentiment_analysis.classification;

import at.sentiment.report.sentiment_analysis.features.FeatureVector;

public interface ISentimentClassifier {

    /**
     * @brief assignment of the sentiment (positive/negative) to the tweet represented as a feature vector.
     * @author Gruzdev Eugen
     * @date 02.12.2017
     * @param featureVector
     * @return the sentiment of the feature vector
     * @throws ClassificationException
     */

    public Sentiment classify(FeatureVector featureVector) throws ClassificationException;

}
