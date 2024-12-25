package com.miladovsky.money_manager.controller;

import com.miladovsky.money_manager.audit.AuditService;
import com.miladovsky.money_manager.audit.Auditable;
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
    @Auditable(action = "INCOME_LIST", entityType = "Income")
    public List<Income> incomes() {
        List<Income> incomes = incomeRepository.findAll();
        return incomes;
    }

    @GetMapping("/{id}")
    @Auditable(action = "GET_INCOME", entityType = "Income")
    public Income income(@PathVariable String id) {
        return incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));
    }

    @PostMapping
    @Auditable(action = "CREATE_INCOME", entityType = "Income", detail = "Creating new income record")
    public Income income(@RequestBody Income income) {
        Income savedIncome = incomeRepository.save(income);
        return savedIncome;

    }

    @PutMapping("/{id}")
    @Auditable(action = "EDIT_INCOME", entityType = "Income")
    public Income income(@PathVariable String id, @RequestBody Income income) {
        Income existing = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));
        existing.setAmount(income.getAmount());
        existing.setDate(income.getDate());
        existing.setSource(income.getSource());
        return incomeRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    @Auditable(action = "DELETE_INCOME", entityType = "Income", detail = "Removing an existing income record")
    public void deleteIncome(@PathVariable String id) {
        incomeRepository.deleteById(id);
    }

}
