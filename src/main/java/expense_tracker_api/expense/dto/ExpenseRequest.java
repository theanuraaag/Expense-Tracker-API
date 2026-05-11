package expense_tracker_api.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import expense_tracker_api.domain.expense.ExpenseCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ExpenseRequest (
    @NotNull @Positive BigDecimal amount,
    @NotNull ExpenseCategory category,
    @Size(max=500) String description,
    @NotNull LocalDate expenseDate
){
    
}
