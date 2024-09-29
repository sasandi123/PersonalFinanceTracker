package com.frontend.financetracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private TransactionService transactionService;




    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        List<Transaction> recentTransactions = transactionService.getLastTenTransactions();
        model.addAttribute("transactions", recentTransactions);

        BigDecimal totalIncome = transactionService.calculateTotalByType("INCOME");
        BigDecimal totalExpenses = transactionService.calculateTotalByType("EXPENSE");
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpenses", totalExpenses);


        return "dashboard";
    }




    @PostMapping("/dashboard")
    public String processForm(@ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "dashboard";
        }
        // Process form data here
        return "redirect:/dashboard";
    }
}




