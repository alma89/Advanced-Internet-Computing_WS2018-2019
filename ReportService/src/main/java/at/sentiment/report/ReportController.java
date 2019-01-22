package at.sentiment.report;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class ReportController {

    private ResponseEntity<byte[]> response;


    public ReportService service = new ReportService();


    @RequestMapping(value="/report",method= RequestMethod.GET, produces="application/pdf")
    public ResponseEntity createReport() throws IOException {
        if(response != null) {
            return response;
        } else {
            return ResponseEntity.ok().build();
        }
    }


    @RequestMapping(value="/report",method= RequestMethod.POST, produces="application/pdf")
    @ResponseBody
    // TO DO @RequestBody PDFContent json PDFContent
    public ResponseEntity<byte[]> createReport(@RequestBody PDFContent PDFcontent) throws IOException {

        PDDocument pdfReport = service.createPDFReport(PDFcontent);

        // Save file
        pdfReport.save("reportPDF.pdf");
        pdfReport.close();

        // get path
        String path = new File("reportPDF.pdf").getAbsolutePath();
        File pdfFile =  new File(path);

        byte[] contents = Files.readAllBytes(Paths.get(pdfFile.getPath()));

        // nun holen wir uns das erstelle PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "reportPDF.pdf";
        headers.add("content-disposition", "inline;filename=" + filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);

        this.response = response;

        return response;

    }

}
