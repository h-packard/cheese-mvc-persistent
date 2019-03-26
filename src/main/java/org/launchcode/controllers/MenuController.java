package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import javax.validation.Valid;

import static java.awt.SystemColor.menu;

//pick up at studio 3 - List Menus
@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private CheeseDao cheeseDao;
    @Autowired
    private MenuDao menuDao;

//refactor for menu below

    @RequestMapping(value = "")
    public String index(Model model){
        //this should retrieve a list of all categories done via categoryDao.findAll()
        //method and pass to ul in view
        model.addAttribute("title", "Menus");
        model.addAttribute("menuDao",menuDao.findAll());

        return "menu/index";

    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addMenuGet(Model model){

        //for adding new menus to be displayed in index view
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());

        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addMenuPost(Model model, @ModelAttribute @Valid Menu menu, Errors errors){

        //Validate data - return form at menu/add if errors
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");

            return "menu/add";
        }

        //if form submission is valid, save new menu & redirect to category index view
        menuDao.save(menu);

        return "redirect:view/"+ menu.getId();
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int id) {
        //instance of form
        AddMenuItemForm addMenuItemForm = new AddMenuItemForm();

        Menu aMenu = menuDao.findOne(id);
        Iterable<Cheese> cheeses = addMenuItemForm.getCheeses();
        //for adding new menus to be displayed in index view
        model.addAttribute("title", aMenu.getName());
        model.addAttribute("menu", aMenu);
        model.addAttribute("cheeses",cheeses);


        return "menu/view";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.GET)
    public String addItemGet(Model model,
                             @PathVariable int id){
        Menu aMenu = menuDao.findOne(id);
        Iterable<Cheese> cheeses = cheeseDao.findAll();
        AddMenuItemForm addItem = new AddMenuItemForm();

        model.addAttribute("title", "Add item to menu: "+aMenu.getName());

        //retrieve menu using MenuDao, all cheese and the form
        model.addAttribute("menu",menuDao.findOne(id));
        model.addAttribute("cheeses",cheeses);
        model.addAttribute("addItem", addItem);


        return "menu/add-item";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.POST)
    public String addItem(Model model,
                          @PathVariable int id,
                          @ModelAttribute @Valid AddMenuItemForm addItem,
                          @ModelAttribute @Valid Iterable<Cheese> cheeses,
                          Errors errors){


        //Validate data - return form at menu/add-item if errors
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add item to menu: "+ addItem.getMenu().getName());

            return "menu/add-item/"+id;
        }


        Menu menu = menuDao.findOne(id);
        for (Cheese cheese : cheeses) {
            menu.addItem(cheese);
        }

        menuDao.save(menu);


        //if form submission is valid, save new menu & redirect to category index view
        return "redirect:view/"+ menu.getId();
    }
}
