package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.BarterService;
import domain.Barter;

@Controller
@RequestMapping("/barter/user")
public class BarterUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BarterService barterService;

	// Constructors -----------------------------------------------------------

	public BarterUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=false, defaultValue="") String keyword) {
		ModelAndView result;
		Collection<Barter> barters;
		String keywordToFind;

		barters = barterService.findAllNotCancelled();
		
		if (!keyword.equals("")) {
			String[] keywordComoArray = keyword.split(" ");
			for (int i = 0; i < keywordComoArray.length; i++) {
				if (!keywordComoArray[i].equals("")) {
					keywordToFind = keywordComoArray[i];
					barters = barterService.findBySingleKeywordNotCancelled(keywordToFind);
					break;
				}
			}
		}
		
		result = new ModelAndView("barter/list");
		result.addObject("requestURI", "barter/user/list.do");
		result.addObject("barters", barters);

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Barter barter;
		
		barter = barterService.create();
		result = createEditModelAndView(barter);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int barterId) {
		ModelAndView result;
		Barter barter;
		
		barter = barterService.findOne(barterId);
		
		result = createEditModelAndView(barter);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Barter barter, BindingResult binding){

		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(barter);
		} else {
			try {
				barterService.saveToEdit(barter);
				result = new ModelAndView("redirect:list.do?");
			} catch (Throwable oops) {
				result = createEditModelAndView(barter, "barter.cancel.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Collection<Barter> barters;

		barters = barterService.findAllByFollowedUser();
		
		result = new ModelAndView("barter/display");
		result.addObject("requestURI", "barter/user/display.do");
		result.addObject("barters", barters);

		return result;
	}
	
	// Ancillary Methods
	// ----------------------------------------------------------
	protected ModelAndView createEditModelAndView(Barter barter){
		ModelAndView result;
		
		result = createEditModelAndView(barter, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Barter barter, String message){
		ModelAndView result;
		Collection<Barter> allBarters;
		
		allBarters = barterService.findAll();
		
		result = new ModelAndView("barter/edit");
		result.addObject("barter", barter);
		result.addObject("message", message);
		result.addObject("allBarters", allBarters);
		
		return result;
	}
}
