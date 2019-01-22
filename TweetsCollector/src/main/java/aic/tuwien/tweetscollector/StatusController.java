package aic.tuwien.tweetscollector;

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
