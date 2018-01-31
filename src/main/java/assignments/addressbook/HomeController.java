package assignments.addressbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    AddressRepository addressRepository;

    @RequestMapping("/")
    public String addressBook(Model model){
        model.addAttribute("addresses",addressRepository.findAll());
        return "addressbook";
    }

    @GetMapping("/add")
    public String courseForm(Model model){
        model.addAttribute("person",new Person());
        return "addressform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Person person, BindingResult result){
        if(result.hasErrors()){
            return "addressform";
        }
        addressRepository.save(person);
        return "redirect:/";
    }


    @RequestMapping("/detail/{id}")
    public String showAddress(@PathVariable("id") long id, Model model){
        model.addAttribute("person", addressRepository.findOne(id));
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateAddress(@PathVariable("id") long id, Model model){
        model.addAttribute("person", addressRepository.findOne(id));
        return "addressform";
    }

    @RequestMapping("/delete/{id}")
    public String delAddress(@PathVariable("id") long id) {
        addressRepository.delete(id);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String getSearch()
    {
        return "show";
    }
    @PostMapping("/search")
    public String showSearchResults(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("search");
        model.addAttribute("search",searchString);
        model.addAttribute("addresses",addressRepository.findAllByFirstNameContainingIgnoreCase(searchString));
        return "addressbook";
    }

/*
    @PostMapping("/search")
    public String showSearchResults(@RequestParam("searchString") String request,Model model)
    {
        //Get the search string from the result form
        //String searchString = request.getParameter("search");
        model.addAttribute("search",request);
        model.addAttribute("addresses",addressRepository.findAllByTitleContainingIgnoreCase(request));
        return "addressbook";
    }
    */
}
