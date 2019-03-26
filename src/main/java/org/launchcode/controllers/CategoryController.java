package org.launchcode.controllers;

import com.sun.org.apache.xml.internal.res.XMLErrorResources_sk;
import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("category")
public class CategoryController {
    //creates a class that implements CategoryDao
    // puts object in this field when app starts
    //automatically due to @Autowired and Spring CrudRepository interface, etc
    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "")
    public String index(Model model){
        //this should retrieve a list of all categories done via categoryDao.findAll()
        //method and pass to ul in view
        model.addAttribute("title", "Categories");
        model.addAttribute("categoryDao",categoryDao.findAll());

        return "category/index";

    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addCategoryGet(Model model){

        //for adding new categories to be displayed in index view
        model.addAttribute("title", "Add Categories");
        model.addAttribute(new Category());

        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addCategoryPost(Model model, @ModelAttribute @Valid Category category, Errors errors){

        //Validate data - return form at category/add if errors
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Category");

            return "category/add";
        }

        //if form submission is valid, save new category & redirect to category index view
        categoryDao.save(category);

        return "redirect:";
    }

}
