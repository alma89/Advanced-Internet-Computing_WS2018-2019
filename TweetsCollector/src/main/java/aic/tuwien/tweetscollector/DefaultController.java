package aic.tuwien.tweetscollector;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

	@Value("${build.title}")
	private String TITLE = "[unknown]";
		
	@Value("${build.version}")
	private String VERSION = "[unknown]";
		
	@RequestMapping("/")
	public String fillVariable(Map<String, Object> model) {
		model.put("TITLE", TITLE);
		model.put("VERSION", VERSION);
		return "default";
	}

	
}