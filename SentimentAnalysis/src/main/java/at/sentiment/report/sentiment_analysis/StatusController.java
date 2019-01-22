package at.sentiment.report.sentiment_analysis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

	@RequestMapping("/status")
	@SuppressWarnings("static-method")
	public String status() {

		return "yes";
	}

}
