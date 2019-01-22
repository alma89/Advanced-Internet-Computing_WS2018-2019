package at.sentiment.report.sentiment_analysis;

public class SentimentAnalyzerException extends Exception {

    public SentimentAnalyzerException(String message){
        super(message);
    }


    public SentimentAnalyzerException(Throwable exception){
        super(exception);
    }
}
