package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BarterService;
import domain.Barter;

@Controller
@RequestMapping("/barter")
public class BarterController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BarterService barterService;

	// Constructors -----------------------------------------------------------

	public BarterController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Barter> barters;

		barters = barterService.findAllNotCancelled();
		result = new ModelAndView("barter/list");
		result.addObject("requestURI", "barter/list.do");
		result.addObject("barters", barters);

		return result;
	}
}
