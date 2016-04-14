package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.MatchService;
import domain.Match;

@Controller
@RequestMapping("/match/user")
public class MatchUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MatchService matchService;

	// Constructors -----------------------------------------------------------

	public MatchUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Collection<Match> matches;

		matches = matchService.findAll();
		//matches = matchService.findAllByFollowedUser();
		
		result = new ModelAndView("match/display");
		result.addObject("requestURI", "match/user/display.do");
		result.addObject("matches", matches);

		return result;
	}
}
