package com.Finance_Planning_Application.Controllers;



import com.Finance_Planning_Application.entity.Transaction;
import com.Finance_Planning_Application.entity.User;
import com.Finance_Planning_Application.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping({"/","/index"})
    public String index(){
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String Login(@RequestParam String email, @RequestParam String password,
                        Model model, HttpSession session) {
        User user = userService.authenticate(email, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", true);
            return "login";
        }
    }
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }


    @PostMapping("/signup")
    public String Signup(User user, Model model) {
        String result = userService.registerUser(user);
        if (result.equals("Email already exists")) {
            model.addAttribute("error", "Email is already taken");
            return "signup";
        }
        return "redirect:/login";
    }

    @GetMapping("/add-transaction")
    public String addTransaction() {
        return "add-transaction";
    }

    @PostMapping("/add-transaction")
    public String addTransaction(@RequestParam String type, @RequestParam String description,
                                 @RequestParam double amount, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        Transaction transaction = new Transaction();
        transaction.setType(type);
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setUser(user);

        userService.saveTransaction(transaction);
        return "redirect:/dashboard";
    }

    @GetMapping("/transactions")
    public String viewTransactions(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<Transaction> transactions = userService.getTransactions(user);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }



    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }






    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        double totalIncome = userService.getTotalIncome(loggedInUser);
        double totalExpenses = userService.getTotalExpenses(loggedInUser);
        double balance = userService.getBalance(loggedInUser);

        model.addAttribute("name", loggedInUser.getName());
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpenses", totalExpenses);
        model.addAttribute("balance", balance);

        return "dashboard";
    }


}

