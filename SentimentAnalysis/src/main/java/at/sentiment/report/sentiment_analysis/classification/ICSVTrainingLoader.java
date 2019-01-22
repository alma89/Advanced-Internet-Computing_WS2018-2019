package at.sentiment.report.sentiment_analysis.classification;

import at.sentiment.report.sentiment_analysis.preprocessing.PreprocessorException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public interface ICSVTrainingLoader {


    /**
     * @brief function loads training data for weka classification step
     * @param uri the path to csv file
     * @return List of training samples
     * @throws FileNotFoundException if the file is not found
     * @throws PreprocessorException in case of other exceptions
     */
    public List<Sample> loadTraining(InputStream uri) throws FileNotFoundException, PreprocessorException;
}
