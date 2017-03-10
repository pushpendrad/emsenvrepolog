package com.sample.mvc.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sample.mvc.model.EmployeeCommand;
import com.sample.mvc.service.EmployeeService;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	private Log logger = LogFactory.getLog(EmployeeController.class);

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String viewHome() throws UnknownHostException {

		logger.info(" ECS_cluster = " + System.getenv("ECS_CLUSTER") + " " + " Server address = "
				+ InetAddress.getLocalHost() + " Continer Host Name = " + System.getenv("HOST_NAME") + " HTTP Method = "
				+ "GET" + " Resource path = " + "/home" + " Application Name = " + "EMSService");

		return "home";
	}

	@RequestMapping("/list")
	public String listContacts(Map<String, Object> map) throws UnknownHostException {
		map.put("contact", new EmployeeCommand());
		map.put("contactList", employeeService.listContact());
		logger.info(" ECS_cluster = " + System.getenv("ECS_CLUSTER") + " " + " Server address = "
				+ InetAddress.getLocalHost() + " Continer Host Name = " + System.getenv("HOST_NAME") + " HTTP Method = "
				+ "GET" + " Resource path = " + "/list" + " Application Name = " + "EMSService");

		return "list";
	}

	@RequestMapping(value = "/jsp/add", method = RequestMethod.POST)
	public ModelAndView addEmployee(@ModelAttribute EmployeeCommand employeeCommand) throws UnknownHostException {
		employeeService.addEmployee(employeeCommand);
		logger.info(" ECS_cluster = " + System.getenv("ECS_CLUSTER") + " " + " Server address = "
				+ InetAddress.getLocalHost() + " Continer Host Name = " + System.getenv("HOST_NAME") + " HTTP Method = "
				+ "POST" + " Resource path = " + "/add" + " Application Name = " + "EMSService");
		return new ModelAndView("redirect:/list");
	}

	@RequestMapping(value = "/edit/{code}")
	public String getEmployeeById(@PathVariable("code") Integer empCode, Model model) throws UnknownHostException {
		EmployeeCommand employee = employeeService.getEmployeebyId(empCode);
		model.addAttribute("employee", employee);
		logger.info(" ECS_cluster = " + System.getenv("ECS_CLUSTER") + " " + " Server address = "
				+ InetAddress.getLocalHost() + " Continer Host Name = " + System.getenv("HOST_NAME") + " HTTP Method = "
				+ "GET" + " Resource path = " + "/edit" + " Application Name = " + "EMSService");
	
		return "edit";
	}

	@RequestMapping(value = "/edit/update", method = RequestMethod.POST)
	public String updateEmployee(@ModelAttribute EmployeeCommand employeeCommand) throws UnknownHostException {
		employeeService.updateEmployee(employeeCommand);
		logger.info(" ECS_cluster = " + System.getenv("ECS_CLUSTER") + " " + " Server address = "
				+ InetAddress.getLocalHost() + " Continer Host Name = " + System.getenv("HOST_NAME") + " HTTP Method = "
				+ "POST" + " Resource path = " + "/edit/update" + " Application Name = " + "EMSService");
		return "redirect:/list";
	}

	@RequestMapping(value = "/delete/{employeeId}")
	public String deleteEmplyee(@PathVariable("employeeId") Integer employeeId) throws UnknownHostException {
		employeeService.deleteEmployee(employeeId);
		logger.info(" ECS_cluster = " + System.getenv("ECS_CLUSTER") + " " + " Server address = "
				+ InetAddress.getLocalHost() + " Continer Host Name = " + System.getenv("HOST_NAME") + " HTTP Method = "
				+ "GET" + " Resource path = " + "/delete" + " Application Name = " + "EMSService");
		return "redirect:/list";
	}
}
