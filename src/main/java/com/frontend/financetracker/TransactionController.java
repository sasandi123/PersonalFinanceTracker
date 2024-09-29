package com.frontend.financetracker;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TransactionController {
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    public TransactionController(TransactionService transactionService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    @RequestMapping("/transactionEntry")
    public String transaction(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "transactionEntryPage";
    }

    @PostMapping("/transaction")
    public String transactionEntry(@ModelAttribute("transaction") Transaction transaction, Model model) {
        transactionService.addTransaction(transaction);
        return "redirect:/transactionEntry";

    }

    @GetMapping("/transactionEntry")
    public String newTransactionForm(Model model) {
        model.addAttribute("transaction", new Transaction()); // Assuming you have a Transaction object
        List<Category> categories = categoryService.getAllCategories(); // Replace with your service call to fetch categories
        model.addAttribute("categories", categories);
        return "transactionEntryPage"; // Replace with your actual Thymeleaf template name
    }

    @GetMapping("/transactionEntry/edit/{id}")
    public String editTransactionForm(@PathVariable Long id, Model model) {
        Transaction transaction = transactionService.getTransactionById(id);
        model.addAttribute("transaction", transaction);

        // Add categories for dropdown
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        return "transactionEdit"; // Create this Thymeleaf template to handle transaction edits
    }

    // Handle form submission for updating a transaction
    @PostMapping("/transactionEntry/update/{id}")
    public String updateTransaction(@PathVariable Long id, @ModelAttribute("transaction") Transaction transaction) {
        transactionService.updateTransaction(id, transaction);
        return "redirect:/history"; // Redirect back to history after update
    }

    // Handle deleting a transaction
    @PostMapping("/transactionEntry/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransactionById(id);
        return "redirect:/history"; // Redirect back to history after deletion
    }



    @RequestMapping("/history")
    public String transactionHistory(Model model,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                     @RequestParam(required = false) String type,
                                     @RequestParam(required = false) String category) {
        if ("all".equals(type)) {
            type = null;
        }
        if ("all".equals(category)) {
            category = null;
        }

        List<Transaction> transactions = transactionService.getFilteredTransactions(startDate, endDate, type, category);
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", categories);

        return "transactionHistory";
    }

    @GetMapping("/transaction/{id}")
    public String viewTransaction(@PathVariable Long id, Model model) {
        Transaction transaction = transactionService.getTransactionById(id);
        model.addAttribute("transaction", transaction);
        return "transactionHistory";  // Return the n
    }
}










