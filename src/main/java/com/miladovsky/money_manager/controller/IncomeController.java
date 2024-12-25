package com.miladovsky.money_manager.controller;

import com.miladovsky.money_manager.audit.AuditService;
import com.miladovsky.money_manager.domain.Income;
import com.miladovsky.money_manager.repository.IncomeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeRepository incomeRepository;
    private final AuditService auditService;

    public IncomeController(IncomeRepository incomeRepository, AuditService auditService) {
        this.incomeRepository = incomeRepository;
        this.auditService = auditService;
    }

    @GetMapping
    public List<Income> incomes() {
        List<Income> incomes = incomeRepository.findAll();
        auditService.logAction(
                "GET_INCOMES_LIST",
                "Income",
                "Amount: " + incomes,
                "Details"
        );
        return incomes;
    }

    @GetMapping("/{id}")
    public Income income(@PathVariable String id) {
        return incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));
    }

    @PostMapping
    public Income income(@RequestBody Income income) {
        Income savedIncome = incomeRepository.save(income);
        auditService.logAction(
                "CREATE_INCOME",
                income.getId(),
                "Income",
                "Amount: " + savedIncome.getAmount()
        );
        return savedIncome;

    }

    @PutMapping("/{id}")
    public Income income(@PathVariable String id, @RequestBody Income income) {
        Income existing = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));
        existing.setAmount(income.getAmount());
        existing.setDate(income.getDate());
        existing.setSource(income.getSource());
        return incomeRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteIncome(@PathVariable String id) {
        incomeRepository.deleteById(id);
    }

}
