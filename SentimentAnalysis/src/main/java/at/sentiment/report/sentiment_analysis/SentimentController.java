package at.sentiment.report.sentiment_analysis;

import at.sentiment.report.sentiment_analysis.resultsAggregation.AggregateResults;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SentimentController {

    @RequestMapping(value="/getAnalysis", method=RequestMethod.POST, produces="application/json", consumes="application/json")
    public PDFContent status(@RequestBody TweetsCollectorResponse[] resps) {

        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        PDFContent content = new PDFContent();
        List<TermAnalysis> termAnalysisList = new ArrayList<>();

        try {

            for (int i = 0; i < resps.length; i++) {
                AggregateResults results = sentimentAnalyzer.aggregateResults(resps[i]);

                if(results.getTweetSentimentMap().isEmpty()){

                    TermAnalysis term = new TermAnalysis();
                    term.setPositiveValue(0.0);
                    term.setNegativeValue(0.0);
                    term.setSearchTerm(resps[i].getkeyword());

                    termAnalysisList.add(term);

                } else {

                    System.out.println(results.getTweetSentimentMap().size());
                    System.out.println(results.getPositiveTweetsCount());
                    System.out.println(results.getPositiveTweetsRatio());

                    TermAnalysis term = new TermAnalysis();
                    term.setPositiveValue(results.getPositiveTweetsRatio());
                    term.setNegativeValue(results.getNegativeTweetsRatio());
                    term.setSearchTerm(resps[i].getkeyword());

                    termAnalysisList.add(term);
                }
            }

            content.setContent(termAnalysisList);

        } catch(SentimentAnalyzerException e){
            System.out.println("Exception!");
        }

        return content;
    }

}