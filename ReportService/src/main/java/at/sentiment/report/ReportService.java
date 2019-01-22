package at.sentiment.report;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import java.io.IOException;
import java.io.*;
import org.jfree.chart.ChartUtilities;
import org.springframework.stereotype.Component;

@Component
public class ReportService {

    public PDDocument createPDFReport(PDFContent pdfContent) {

        PDDocument pdfDocument = new PDDocument();

        try {
            // loop through Tweets
            for(int i=0;i<pdfContent.getContent().size();i++) {

                PDPage firstPage = new PDPage();
                //Adding page to the document
                pdfDocument.addPage(firstPage);

                //Adding Content Stream
                PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, firstPage);

                //Setting font
                PDFont font = PDType1Font.TIMES_ROMAN;
                float fontSize = 14;
                contentStream.setFont(font, fontSize);
                PDRectangle mediabox = firstPage.getMediaBox();
                float margin = 72;
                float startX = mediabox.getLowerLeftX() + margin;
                float startY = mediabox.getUpperRightY() - margin;
                contentStream.setLeading(20);

                // Write Text
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, startY);
                contentStream.showText("Term: " + pdfContent.getContent().get(i).getSearchTerm());
                contentStream.newLine();

                // check if analysis was successful
                if((pdfContent.getContent().get(i).getNegativeValue() == 0) &&
                        (pdfContent.getContent().get(i).getPositiveValue() == 0)){
                    contentStream.showText("Term written incorrectly/no tweets could be found. ");
                    contentStream.endText();
                }
                else {
                    contentStream.showText("Negative sentiment: " + pdfContent.getContent().get(i).getNegativeValue());

                    contentStream.newLine();
                    contentStream.showText("Positive sentiment: " + pdfContent.getContent().get(i).getPositiveValue());
                    contentStream.newLine();

                    // create PieChart
                    DefaultPieDataset dataset = new DefaultPieDataset();
                    dataset.setValue("negative", pdfContent.getContent().get(i).getNegativeValue());
                    dataset.setValue("positive", pdfContent.getContent().get(i).getPositiveValue());
                    // TO DO ?  dataset.setValue("neutral", 40);

                    JFreeChart chart = ChartFactory.createPieChart(
                            "Sentiment Analysis: " + pdfContent.getContent().get(i).getSearchTerm(),   // chart title
                            dataset,          // data
                            true,             // include legend
                            true,
                            false);

                    int width = 500;   /* Width of the image */
                    int height = 350;  /* Height of the image */
                    File pieChart = new File("PieChart" + (i + 1) + ".jpeg");
                    ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);
                    contentStream.endText();

                    // Draw PieChart
                    PDImageXObject pdImage = PDImageXObject.createFromFile("PieChart" + (i + 1) + ".jpeg", pdfDocument);
                    contentStream.drawImage(pdImage, 70, 250);
                }
                contentStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfDocument;
    }
}