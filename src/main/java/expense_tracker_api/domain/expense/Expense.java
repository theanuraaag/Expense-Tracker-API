package expense_tracker_api.domain.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

import expense_tracker_api.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",
    nullable =false) 
    private User user;

    @Column(nullable=false, precision=19, scale=2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=32)
    private ExpenseCategory category;

    @Column(length=500)
    private String description;

    @Column(name="expense_date", nullable=false)
    private LocalDate expenseDate;

    public Long getId()
    {
        return id;
    }
    public User getUser()
    {
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public ExpenseCategory getCategory(){
        return category;
    }

    public void setCategory(ExpenseCategory category){
        this.category = category;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public LocalDate getExpenseDate(){
        return expenseDate;
    }
    public void setExpenseDate(LocalDate expenseDate){
        this.expenseDate = expenseDate;
    }


}
