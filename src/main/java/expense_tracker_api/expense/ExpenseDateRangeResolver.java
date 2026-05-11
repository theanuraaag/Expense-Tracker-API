package expense_tracker_api.expense;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

public final class ExpenseDateRangeResolver {
    private ExpenseDateRangeResolver(){

    }
    public record DateRange(LocalDate startInclusive, LocalDate endInclusive){

    }

    public static Optional<DateRange> resolve(String filter, LocalDate startDate, LocalDate endDate){
        boolean hasStart = startDate != null;
        boolean hasEnd = endDate != null;
        if(hasStart != hasEnd){
            throw new IllegalArgumentException("Both startDate and endDate are required for a custom range");
        }
        if(hasStart){
            if(filter != null && !filter.isBlank()){
                throw new IllegalArgumentException("Do not combine filter with startDate/endDate; use one or the other");
            }
            return Optional.of(new DateRange(startDate, endDate));
        }
        if(filter == null || filter.isBlank()){
            return Optional.empty();
        }
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        return Optional.of(switch(filter.trim().toLowerCase()){
            case "past_week" -> new DateRange(today.minusDays(6), today);
            case "past_month"-> new DateRange(today.minusDays(29), today);
            case "past_last_three_months"-> new DateRange(today.minusDays(89), today);
            default -> throw new IllegalArgumentException("Unknown filter: user past_week, past_month, last_three_months, or startDate+endDate");
        });
    }
}
