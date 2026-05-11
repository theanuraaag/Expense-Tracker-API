package expense_tracker_api.expense;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import expense_tracker_api.domain.expense.Expense;
import expense_tracker_api.domain.expense.ExpenseRepository;
import expense_tracker_api.domain.user.User;
import expense_tracker_api.domain.user.UserRepository;
import expense_tracker_api.expense.dto.ExpenseResponse;
import expense_tracker_api.security.SecurityUtils;

import org.springframework.transaction.annotation.Transactional;

import expense_tracker_api.expense.dto.ExpenseRequest;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> list(String filter, LocalDate startDate, LocalDate endDate) {
        Long userId = SecurityUtils.currentUserId();
        var range = ExpenseDateRangeResolver.resolve(filter, startDate, endDate);
        if (range.isEmpty()) {
            return expenseRepository.findByUser_IdOrderByExpenseDateDesc(userId).stream().map(this::toResponse).toList();
        }
        var r = range.get();
        return expenseRepository.findByUser_IdAndExpenseDateBetweenOrderByExpenseDateDesc(userId, r.startInclusive(), r.endInclusive()).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly=true)
    public ExpenseResponse getById(Long id){
        Long userId = SecurityUtils.currentUserId();
        Expense expense = expenseRepository.findByIdAndUser_Id(id, userId).orElseThrow(()-> new ExpenseNotFoundException(id));
        return toResponse(expense);
    }

    @Transactional
    public ExpenseResponse create(ExpenseRequest request) {
        Long userId = SecurityUtils.currentUserId();
        User user = userRepository.findById(userId).orElseThrow();
        Expense expense = new Expense();
        expense.setUser(user);
        apply(expense, request);
        return toResponse(expenseRepository.save(expense));
    }

    @Transactional
    public ExpenseResponse update(Long id, ExpenseRequest request) {
        Long userId = SecurityUtils.currentUserId();
        Expense expense = expenseRepository.findByIdAndUser_Id(id, userId).orElseThrow(()-> new ExpenseNotFoundException(id));
        apply(expense, request);
        return toResponse(expenseRepository.save(expense));
    }

    @Transactional
    public void delete(Long id){
        Long userId = SecurityUtils.currentUserId();
        Expense expense = expenseRepository.findByIdAndUser_Id(id, userId).orElseThrow(()-> new ExpenseNotFoundException(id));
        expenseRepository.delete(expense);
    }

    private void apply(Expense expense, ExpenseRequest request){
        expense.setAmount(request.amount());
        expense.setCategory(request.category());
        expense.setDescription(request.description());
        expense.setExpenseDate(request.expenseDate());
    }

    private ExpenseResponse toResponse(Expense e){
        return new ExpenseResponse(e.getId(), e.getAmount(), e.getCategory(), e.getDescription(), e.getExpenseDate());
    }
}
