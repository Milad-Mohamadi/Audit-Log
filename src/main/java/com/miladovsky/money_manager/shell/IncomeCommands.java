package com.miladovsky.money_manager.shell;

import com.miladovsky.money_manager.domain.Income;
import com.miladovsky.money_manager.repository.IncomeRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDate;
import java.util.List;

@ShellComponent
public class IncomeCommands {

    private final IncomeRepository incomeRepository;

    public IncomeCommands(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @ShellMethod("List all incomes")
    public List<Income> listIncomes() {
        return incomeRepository.findAll();
    }

    @ShellMethod("Add a new income. Usage: addIncome <amount> <source>")
    public Income addIncome(double amount, String source) {
        Income income = new Income();
        income.setAmount(amount);
        income.setDate(LocalDate.now());
        income.setSource(source);
        return incomeRepository.save(income);
    }
}