package distributed.systems.lab2.communicaton;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class Client {
    private Sender sender = new Sender();
    private ArrayList<String> response;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/response")
    public String response(Model model) {
        for (int i=0; i<3; i++)
            if (i < response.size())
                model.addAttribute("response"+i, response.get(i));
            else
                model.addAttribute("response"+i, "");
        return "response";
    }

    @PostMapping("/")
    public ModelAndView clientSubmit(@RequestParam("person") String person,
                                     @RequestParam("planet") String planet,
                                     @RequestParam("movie") String movie,
                                     Model model) throws IOException {

        System.out.println(person + planet + movie);

        this.response = sender.respond(person, planet, movie);
        for (int i=0; i<3; i++)
            if (i < response.size())
                model.addAttribute("response"+i, response.get(i));
            else
                model.addAttribute("response"+i, "");

        return new ModelAndView("redirect:/response");
    }
}