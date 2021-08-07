package com.example.week5challengecraigslistjobposting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class HomeController {

    public static long idCounter = 0;

    ArrayList<Job> jobs = new ArrayList<>();

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/processregister")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        if(result.hasErrors()){
            user.clearPassword();
            model.addAttribute("user", user);
            return "register";
        }else{
            model.addAttribute("user", user);
            model.addAttribute("message", "New user account created.");

            user.setEnabled(true);
            userRepository.save(user);

            Role role = new Role(user.getUsername(), "ROLE_USER");
            roleRepository.save(role);
        }
        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/logout")
    public String logout(){
        return "redirect:/login?logout=true";
    }


    @RequestMapping("/")
    public String loadHomepage(Model model){
        model.addAttribute("jobs", jobs );
        return "alljobs";
    }



    @GetMapping("/addjob")
    public String addJob(Model model){
        Job newJob = new Job();
        idSetter(newJob);
        model.addAttribute("job", newJob);

        return "addjob";
    }

    public void idSetter(Job job){
        idCounter += 1;
        job.setId(idCounter);
    }

    @PostMapping("/confirmjob")
    public String processJobForm(@Valid Job job, BindingResult result, Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("username", username);

        if(result.hasErrors()){
            return "addjob";
        }else{
            jobs.add(job);
            return "confirmjob";
        }
    }

    @RequestMapping("/alljobs")
    public String showAllJobs(Model model){
        model.addAttribute("jobs", jobs );
        return "alljobs";
    }

    @RequestMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long id, Model model){
        for (Job job: jobs){
            if(id == job.getId()){
                jobs.remove(job);
                model.addAttribute("job", job);
                break;
            }
        }
        return "addjob";
    }



    @RequestMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id") long id, Model model){
        for (Job job: jobs){
            if(id == job.getId()){
                jobs.remove(job);
                break;
            }
        }
        return "delete";
    }

    @RequestMapping("/job/{id}")
    public String JobDetail(@PathVariable("id") long id, Model model, Principal principal){
        String username = principal.getName();
        model.addAttribute("username", username);

        for (Job job: jobs){
            if(id == job.getId()){
                model.addAttribute("job", job);
                break;
            }
        }
        return "confirmjob";
    }





}
