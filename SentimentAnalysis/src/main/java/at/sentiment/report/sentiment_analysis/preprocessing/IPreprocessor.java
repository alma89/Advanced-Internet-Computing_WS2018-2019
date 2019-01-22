package at.sentiment.report.sentiment_analysis.preprocessing;

/*
 * Preprocessor is used for normalization of tweets.
 * This step is required to create feature vectors.
 */

import at.sentiment.report.sentiment_analysis.features.FeatureVector;

public interface IPreprocessor {

    /**
     * @brief Extract feature vector from the given textual message
     * @author Gruzdev Ievgenii
     * @date 28.11.2017
     * @param message : String
     * @return FeatureVector
     * @throws PreprocessorException
     */
    public FeatureVector conductPreprocessing(String message) throws PreprocessorException;

}
