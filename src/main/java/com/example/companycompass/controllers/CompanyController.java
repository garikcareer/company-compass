package com.example.companycompass.controllers;

import com.example.companycompass.model.Company;
import com.example.companycompass.repository.CompanyRepository;
import com.example.companycompass.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(path = "/web/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("")
    @ModelAttribute
    public ModelAndView getAllCompanies(Model model) {
        model.addAttribute("content", "company");
        model.addAttribute("pageTitle", "Companies");
        model.addAttribute("companyList", companyService.getCompanies());
        return new ModelAndView("layout");
    }

    @DeleteMapping("/delete/{companyId}")
    public ModelAndView deleteCompany(@PathVariable Long companyId, RedirectAttributes redirectAttributes, Model model) {
        try {
            if (companyService.existsById(companyId)) {
                companyService.deleteCompanyById(companyId);
                redirectAttributes.addFlashAttribute("successMessage", "Company deleted successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Company not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete company: " + e.getMessage());
        }
        model.addAttribute("content", "company");
        model.addAttribute("pageTitle", "Companies");
        List<Company> companies = companyService.getCompanies();
        model.addAttribute("companyList", companies);
        return new ModelAndView("layout");
    }

    @PostMapping("/edit/{companyId}")
    public ModelAndView editCompany(@PathVariable Long companyId, @RequestParam String name, @RequestParam String location, RedirectAttributes redirectAttributes, Model model) {
        try {
            if (companyService.existsById(companyId)) {
                Company company = new Company(name, location);
                companyService.updateCompany(company, companyId);
                redirectAttributes.addFlashAttribute("successMessage", "Company was edited successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Company does not exist");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to edit company: " + e.getMessage());
        }
        model.addAttribute("content", "company");
        return new ModelAndView("layout");
    }
}

