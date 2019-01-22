package at.sentiment.report.sentiment_analysis;

import at.sentiment.report.sentiment_analysis.classification.*;
import at.sentiment.report.sentiment_analysis.features.FeatureVector;
import at.sentiment.report.sentiment_analysis.preprocessing.IPreprocessor;
import at.sentiment.report.sentiment_analysis.preprocessing.Preprocessor;
import at.sentiment.report.sentiment_analysis.preprocessing.PreprocessorException;
import at.sentiment.report.sentiment_analysis.resultsAggregation.AggregateResults;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import weka.classifiers.bayes.NaiveBayes;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SentimentAnalyzer {

    private final static Logger logger = LogManager.getLogger(SentimentAnalyzer.class);

    private IPreprocessor preprocessor = new Preprocessor();


    public AggregateResults aggregateResults(TweetsCollectorResponse tweetsResponse) throws SentimentAnalyzerException{

        AggregateResults aggregateResults = new AggregateResults();

        try{

            ISentimentClassifier classifier = naiveBayes();

            List<Tweet> tweets = tweetsResponse.getTweets();
            if(tweets.size() == 0){
                return new AggregateResults();
            }

            for(Tweet tweet : tweets){
                FeatureVector featureVector = null;
                featureVector = preprocessor.conductPreprocessing(tweet.getText());

                Sentiment sentiment = classifier.classify(featureVector);

                /* For testing purposes */
                System.out.print(tweet.getText() + "  ");
                System.out.println(sentiment + ";");
                /*-------------*/

                aggregateResults.putTweetSentiment(tweet, sentiment);
            }
            return aggregateResults;

        } catch (PreprocessorException e){
            throw new SentimentAnalyzerException(e.getMessage());
        } catch (ClassificationException e){
            throw new SentimentAnalyzerException(e.getMessage());
        } catch (FileNotFoundException e){
            throw new SentimentAnalyzerException(e.getMessage());
        } catch (URISyntaxException e){
            throw new SentimentAnalyzerException(e.getMessage());
        }
    }


    public List<Sample> getTraining() throws URISyntaxException, FileNotFoundException, PreprocessorException{
        ICSVTrainingLoader sampleLoader = new CSVTrainingLoader();
        InputStream trainingSetUri = getClass().getClassLoader().
                getResourceAsStream("trainingSet.csv");
        List<Sample> samples = sampleLoader.loadTraining(trainingSetUri);
        return samples;
    }


    public ISentimentClassifier naiveBayes() throws FileNotFoundException, PreprocessorException,
            URISyntaxException, ClassificationException {
        return new SentimentClassifier(getTraining(), new NaiveBayes());
    }

}
