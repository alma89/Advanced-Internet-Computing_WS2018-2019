package at.sentiment.report.sentiment_analysis.classification;

public class ClassificationException extends Exception {

    public ClassificationException(String message){
        super(message);
    }

    public ClassificationException(Throwable exception){
        super(exception);
    }

}
