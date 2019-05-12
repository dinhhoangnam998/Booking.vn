package webtech.gr14.controller.acc;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import webtech.gr14.model.Acc;
import webtech.gr14.service.acc.ProfileS;

@Controller
@RequestMapping("/acc")
public class ProfileC {

	@Autowired
	private ProfileS pS;

	@GetMapping("/profiles/{aid}")
	public String profile(Model model, @PathVariable int aid) {
		model.addAttribute("acc", pS.getAccById(aid));
		return "/acc/profile/info";
	}

	@RequestMapping(value = "/profiles/{aid}/change-avatar", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	public String changeAvatar(HttpSession ss, @PathVariable int aid, @RequestParam MultipartFile file) {
		pS.changeAvatar(ss, aid, file);
		return "redirect:/acc/profiles/" + aid;
	}

	@GetMapping("/profiles/{aid}/edit")
	public String editProfile(Model model, @PathVariable int aid) {
		model.addAttribute("acc", pS.getAccById(aid));
		return "/acc/profile/edit";
	}

	@PostMapping("/profiles/{aid}/edit")
	public String editProfile(RedirectAttributes rdA, Acc acc) {
		if (pS.checkModifyProfileValid(acc)) {
			pS.saveModifiedProfile(acc);
			rdA.addFlashAttribute("msg", "Change profile success!");
			return "/acc/profiles/" + acc.getId();
		} else {
			rdA.addFlashAttribute("msgs", pS.getEditProfileErrorMessages());
			return "redirect:/acc/profiles/" + acc.getId() + "/edit";
		}
	}

	@GetMapping("/profiles/{aid}/change-password")
	public String changePassword(Model model, @PathVariable int aid) {
		return "/acc/profile/change-password";
	}

	@PostMapping("/profiles/{aid}/change-password")
	public String changePassword(RedirectAttributes rdA, Acc acc) {
		if (pS.checkPasswordMatch(acc)) {
			pS.saveChangePassword(acc);
			rdA.addFlashAttribute("msg", "Change password success!");
			return "/acc/profiles/" + acc.getId();
		} else {
			rdA.addFlashAttribute("msg", "Password or Confirm password is not valid!");
			return "redirect:/acc/profiles/" + acc.getId() + "/change-password";
		}
	}
	
	@ResponseBody
	@GetMapping("/profiles/{aid}/change-name")
	public String changeName(HttpSession ss, @RequestParam String name) {
		pS.changeName(name, ss);
		return name;
	}
	
	@ResponseBody
	@GetMapping("/profiles/{aid}/change-birthday")
	public String changeBirthday(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday) {
		pS.changeBirthday(birthday);
		return "ok";
	}
	
	@ResponseBody
	@GetMapping("/profiles/{aid}/change-gender")
	public String toggleGender() {
		pS.toggleGender();
		return "ok";
	}
	
	@ResponseBody
	@GetMapping("/profiles/{aid}/change-email")
	public boolean changeEmail(@RequestParam String email) {
		return pS.changeEmail(email);
	}
	
	@ResponseBody
	@GetMapping("/profiles/{aid}/change-phone")
	public boolean changePhone(@RequestParam String phone) {
		return pS.changePhone(phone);
	}
	
	@ResponseBody
	@GetMapping("/profiles/{aid}/change-address")
	public boolean changeAddress(@RequestParam String address) {
		return pS.changeAddress(address);
	}

}
