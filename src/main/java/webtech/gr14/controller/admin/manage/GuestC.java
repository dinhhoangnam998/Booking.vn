package webtech.gr14.controller.admin.manage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import webtech.gr14.model.Acc;
import webtech.gr14.model.ReserveOrder;
import webtech.gr14.service.admin.manage.GuestS;
import webtech.gr14.util.AccState;

@Controller
@RequestMapping("/admin/manage/guests")
public class GuestC {

	@Autowired
	private GuestS gS;

	@GetMapping("/bad-transactions")
	public String showListBadTransaction(Model model, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "10") int pS) {
		List<ReserveOrder> badTrans = gS.getBadTransactionsOfGuests(p, pS);
		model.addAttribute("trans", badTrans);
		List<Integer> pages = gS.getPageList(p, pS);
		model.addAttribute("pages", pages);
		return "/admin/manage/guest/bad-transactions";
	}

	@GetMapping("/{gid}")
	public String showGuestInfo(Model model, @PathVariable int gid) {
		Acc guestAcc = gS.aR.getOne(gid);
		model.addAttribute("guest", guestAcc);
		return "";
	}

	@ResponseBody
	@GetMapping("/{gid}/warning")
	public String warning(@PathVariable int gid) {
		Acc guestAcc = gS.aR.getOne(gid);
		guestAcc.setState(AccState.WARNING);
		guestAcc.setWarningDate(new Date());
		gS.aR.save(guestAcc);
		return "";
	}

	@ResponseBody
	@GetMapping("/{gid}/unwarning")
	public String unwarning(@PathVariable int gid) {
		Acc guestAcc = gS.aR.getOne(gid);
		guestAcc.setState(AccState.ACTIVE);
		gS.aR.save(guestAcc);
		return "";
	}

	@ResponseBody
	@GetMapping("/{gid}/block")
	public String block(@PathVariable int gid) {
		Acc guestAcc = gS.aR.getOne(gid);
		guestAcc.setState(AccState.BLOCK);
		gS.aR.save(guestAcc);
		return "";
	}

	@ResponseBody
	@GetMapping("/{gid}/unblock")
	public String unblock(@PathVariable int gid) {
		Acc guestAcc = gS.aR.getOne(gid);
		guestAcc.setState(AccState.ACTIVE);
		gS.aR.save(guestAcc);
		return "";
	}

	@ResponseBody
	@GetMapping("/bad-transactions/{tid}/checked")
	public String checked(@PathVariable int tid) {
		ReserveOrder rO = gS.roR.getOne(tid);
		rO.setChecked(true);
		gS.roR.save(rO);
		return "";
	}

}
