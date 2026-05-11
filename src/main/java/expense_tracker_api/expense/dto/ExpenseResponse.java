package expense_tracker_api.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import expense_tracker_api.domain.expense.ExpenseCategory;

public record ExpenseResponse(
    Long id,
    BigDecimal amount,
    ExpenseCategory category,
    String description,
    LocalDate expenseDate
){

}
