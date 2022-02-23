package cg.controllers;

import cg.model.Customer;
import cg.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/home")
public class CustomerController {

    // Bài này không có upload ảnh
    @Value("${file-upload}")
    private String fileUpload;

    // Bài này không có upload ảnh
    @Value("${view}")
    private String view;


    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ModelAndView showCustomerList() {
        ModelAndView modelAndView = new ModelAndView("index");
        ArrayList<Customer> customers = customerService.getAllCustomer();
        if (customers.isEmpty()) {
            modelAndView.addObject("message", "No products!");
            modelAndView.addObject("color", "red");
        }
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView redirectCreate(){
        ModelAndView modelAndView = new ModelAndView("create");
        Customer customer = new Customer();
        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(@ModelAttribute Customer customer){
        ModelAndView modelAndView = new ModelAndView("create");
        Customer customer1 = customerService.saveCustomer(customer);
        if (customer1 != null){
            modelAndView.addObject("message", "Create successfully!");
        }
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView redirectEdit(@PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView("edit");
        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            modelAndView.addObject("customer", customer);
        } else {
            modelAndView.addObject("message", "Id invalid!");
        }
        return modelAndView;
    }

    @PostMapping("/{id}")
    public ModelAndView edit(@ModelAttribute Customer customer, @PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView("edit");
        customer.setId(id);
        Customer customer1 = customerService.saveCustomer(customer);
        if (customer1 != null){
            modelAndView.addObject("message", "Update successfully!");
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView("index");
        customerService.deleteCustomer(id);
        ArrayList<Customer> customers = customerService.getAllCustomer();
        if (customers.isEmpty()) {
            modelAndView.addObject("message", "No products!");
            modelAndView.addObject("color", "red");
        }
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("view")
    public ModelAndView redirectView(@RequestParam("id") int id){
        ModelAndView modelAndView = new ModelAndView("detail");
        Customer customer = customerService.getCustomer(id);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView searchByName(@RequestParam("searchName") String nameSearch){
        ModelAndView modelAndView = new ModelAndView("index");
        ArrayList<Customer> customers = customerService.findByNameContaining(nameSearch);
        if (customers.isEmpty()) {
            modelAndView.addObject("message", "No products!");
            modelAndView.addObject("color", "red");
        }
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }


}
