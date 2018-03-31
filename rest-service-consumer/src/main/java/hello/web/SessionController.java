package hello.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/session")
public class SessionController {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);
	
	@GetMapping("/")
	protected String session(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam(value = "name", required = false) String name)
			throws ServletException, IOException {
		if(name != null){
			req.getSession().setAttribute("name", name);
			logger.info("set session attribute: " + name);
		}
		String nameInSession = (String) req.getSession().getAttribute("name");
		return nameInSession;
		
	}
}
