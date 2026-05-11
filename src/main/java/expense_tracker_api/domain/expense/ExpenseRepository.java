package expense_tracker_api.domain.expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{
    List<Expense> findByUser_IdOrderByExpenseDateDesc(Long userId);

    List<Expense> findByUser_IdAndExpenseDateBetweenOrderByExpenseDateDesc( Long userId,
        LocalDate startInclusive,
        LocalDate endInclusive
    );

    Optional<Expense> findByIdAndUser_Id(Long id, Long userId);
    
}
