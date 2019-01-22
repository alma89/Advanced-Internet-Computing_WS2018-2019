package at.sentiment.report.sentiment_analysis.classification;

import at.sentiment.report.sentiment_analysis.features.FeatureVector;
import at.sentiment.report.sentiment_analysis.preprocessing.IPreprocessor;
import at.sentiment.report.sentiment_analysis.preprocessing.Preprocessor;
import at.sentiment.report.sentiment_analysis.preprocessing.PreprocessorException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVTrainingLoader implements ICSVTrainingLoader {

    @Override
    public List<Sample> loadTraining(InputStream uri) throws FileNotFoundException, PreprocessorException{
        List<Sample> training = new ArrayList<>();
        IPreprocessor preprocessor = new Preprocessor();
        Scanner scanner = new Scanner(uri);

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] columns = line.substring(0, line.length() - 1).split(",");

            String sentimentString = columns[1];
            Sentiment sentiment;
            if (sentimentString.equals("neg")) {
                sentiment = Sentiment.NEGATIVE;
            } else if (sentimentString.equals("pos")) {
                sentiment = Sentiment.POSITIVE;
            } else {
                continue;
            }

            String tweet = columns[columns.length - 1];
            FeatureVector featureVector = preprocessor.conductPreprocessing(tweet);

            Sample trainingSample = new Sample(featureVector, sentiment);
            training.add(trainingSample);
        }

        scanner.close();
        return training;

    }

}
