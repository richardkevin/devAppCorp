package dac.cma.controller;

import dac.cma.model.Project;
import dac.cma.model.Student;
import dac.cma.repository.ProjectRepository;
import dac.cma.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import dac.cma.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping("/")
    public String home() {        
        return "index";
    }

    @GetMapping("/sign-in")
    public String signInForm(Model model) {
        model.addAttribute("student", new Student());
        return "sign-in";
    }
    
    @PostMapping("/save-student")
    public String signIn(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Long userId = null;
        for (Student student : studentRepository.findAll()) {
            if (student.getUsername().equals(username)) {
                userId = student.getId();
                Student s = studentRepository.findOne(userId);
                if (s.getPassword().equals(password)) {
                    System.out.println("bem vindo");
                }
                break;
            }
        }
        return "redirect:/";
    }

    @GetMapping("/add-project")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        return "add-project";
    }

    @PostMapping("/save-project")
    public String addProject(@ModelAttribute Project project) {
        projectRepository.save(project);

        return "redirect:/my-projects";
    }

//    acessing data
//    @PostMapping("/add") // Map ONLY GET Requests
//    public String addNewUser (@ModelAttribute User user) {
//        // @ResponseBody means the returned String is the response, not a view name
//        // @RequestParam means it is a parameter from the GET or POST request
//        userRepository.save(user);
//        return "redirect:/all";
//    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("listStudent", studentRepository.findAll());
        return "all";
    }
}