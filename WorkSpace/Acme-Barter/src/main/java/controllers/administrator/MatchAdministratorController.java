package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MatchService;
import controllers.AbstractController;
import domain.Match;

@Controller
@RequestMapping("/match/administrator")
public class MatchAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MatchService matchService;

	// Constructors -----------------------------------------------------------

	public MatchAdministratorController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Match> matches;

		matches = matchService.findAllNotSignedOneMonthSinceCreation();
		
		result = new ModelAndView("match/list");
		result.addObject("requestURI", "match/administrator/list.do");
		result.addObject("matches", matches);

		return result;
	}
	
	// Cancellation ---------------------------------------------------------------
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel() {
		ModelAndView result;

		matchService.cancelEveryMatchNotSignedOneMonthSinceCreation();
		
		result = this.list();
		
//		result = new ModelAndView("match/list");
//		result.addObject("match", match); // Devolver mensaje de error/confirmación

		return result;
	}
	
}
