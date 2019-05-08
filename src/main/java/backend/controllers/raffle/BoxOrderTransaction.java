package backend.controllers.raffle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/transaction")
public class BoxOrderTransaction {

	
	@RequestMapping(value="/checkout", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Boolean getCheckout() {
		return false;
	}
	
	@RequestMapping(value="/charge", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Boolean getCharge() {
		return false;
	}
}
