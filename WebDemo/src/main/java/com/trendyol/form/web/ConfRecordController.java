package com.trendyol.form.web;

import com.trendyol.form.service.ConfRecordService;
import com.trendyol.form.validator.ConfRecordFormValidator;
import model.ConfRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
public class ConfRecordController {

    private final Logger logger = LoggerFactory.getLogger(ConfRecordController.class);

    @Autowired
    ConfRecordFormValidator confRecordFormValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(confRecordFormValidator);
    }

    private ConfRecordService confRecordService;

    @Autowired
    public void setConfRecordService(ConfRecordService confRecordService) {
        this.confRecordService = confRecordService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        logger.debug("index()");
        return "redirect:/confRecords";
    }

    @RequestMapping(value = "/confRecords", method = RequestMethod.GET)
    public String showAllConfRecords(Model model) {
        logger.debug("showAllConfRecords()");
        if (!model.asMap().containsKey("confRecords")) {
            model.addAttribute("confRecords", confRecordService.findAll());
        }
        return "confRecords/list";
    }

    @RequestMapping(value = "/confRecords/search", method = RequestMethod.GET)
    public ModelAndView loginValidate(HttpServletRequest request, RedirectAttributes redir) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/confRecords");
        redir.addFlashAttribute("confRecords", confRecordService.search(request.getParameter("searchText")));
        return modelAndView;
    }

    @RequestMapping(value = "/confRecords", method = RequestMethod.POST)
    public String saveOrUpdateConfRecord(@ModelAttribute("confRecord") @Validated ConfRecord confRecord,
                                         BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        logger.debug("saveOrUpdateConfRecord() : {}", confRecord);
        if (result.hasErrors()) {
            populateDefaultModel(model);
            return "confRecords/confRecordForm";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            if (confRecord.isNew()) {
                redirectAttributes.addFlashAttribute("msg", "Record added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Record updated successfully!");
            }
            confRecordService.saveOrUpdate(confRecord);
            return "redirect:/confRecords";
        }
    }

    @RequestMapping(value = "/confRecords/add", method = RequestMethod.GET)
    public String showAddConfRecordForm(Model model) {
        logger.debug("showAddConfRecordForm()");
        ConfRecord confRecord = new ConfRecord();
        confRecord.setName("test");
        confRecord.setValue("test");
        confRecord.setIsActive("1");
        confRecord.setApplicationName("test");
        model.addAttribute("confRecord", confRecord);
        populateDefaultModel(model);
        return "confRecords/confRecordForm";
    }

    @RequestMapping(value = "/confRecords/{id}/update", method = RequestMethod.GET)
    public String showUpdateConfRecordForm(@PathVariable("id") int id, Model model) {
        logger.debug("showUpdateConfRecordForm() : {}", id);
        ConfRecord confRecord = confRecordService.findById(id);
        model.addAttribute("confRecord", confRecord);
        populateDefaultModel(model);
        return "confRecords/confRecordForm";
    }

    @RequestMapping(value = "/confRecords/{id}/delete", method = RequestMethod.POST)
    public String deleteConfRecord(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
        logger.debug("deleteConfRecord() : {}", id);
        confRecordService.delete(id);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "User is deleted!");
        return "redirect:/confRecords";
    }

    private void populateDefaultModel(Model model) {
        model.addAttribute("types", Arrays.asList("string", "boolean", "integer", "double"));
    }

}