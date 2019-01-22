package at.sentiment.report.sentiment_analysis;

import java.util.List;

public class PDFContent {
    private  List<TermAnalysis> content;

    public List<TermAnalysis> getContent() {
        return content;
    }

    public void setContent(List<TermAnalysis> content) {
        this.content = content;
    }

}