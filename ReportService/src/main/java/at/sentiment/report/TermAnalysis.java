package at.sentiment.report;

public class TermAnalysis {

    private String searchTerm;
    private double positiveValue;
    private double negativeValue;


    public double getPositiveValue() {
        return positiveValue;
    }

    public double getNegativeValue() {
        return negativeValue;
    }


    public void setPositiveValue(double positiveValue) {
        this.positiveValue = positiveValue;
    }

    public void setNegativeValue(double negativeValue) {
        this.negativeValue = negativeValue;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return searchTerm;
    }
}

