package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.BarterService;
import domain.Barter;

@Controller
@RequestMapping("/barter/administrator")
public class BarterAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BarterService barterService;

	// Constructors -----------------------------------------------------------

	public BarterAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Barter> barters;

		barters = barterService.findAll();
		result = new ModelAndView("barter/list");
		result.addObject("requestURI", "barter/administrator/list.do");
		result.addObject("barters", barters);

		return result;
	}
}
