package webtech.gr14.controller.admin.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import webtech.gr14.service.admin.manage.HostS;

@Controller
public class HostC {

	@Autowired
	private HostS hS;
}
