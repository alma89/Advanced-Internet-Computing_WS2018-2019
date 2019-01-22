package at.sentiment.report.sentiment_analysis.preprocessing;

public class PreprocessorException extends Exception {

    public PreprocessorException(String message){
        super(message);
    }

    public PreprocessorException(Throwable exception){
        super(exception);
    }
}
