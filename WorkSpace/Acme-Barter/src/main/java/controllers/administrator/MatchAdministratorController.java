package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MatchService;
import controllers.AbstractController;

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
	
	// Cancellation ---------------------------------------------------------------
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel() {
		ModelAndView result;

		matchService.cancelEveryMatchNotSignedOneMonthSinceCreation();
		
		result = new ModelAndView("match/list");
//		result.addObject("match", match); // Devolver mensaje de error/confirmación

		return result;
	}
	
}
