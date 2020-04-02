package crawford.steven;

import com.google.gson.JsonObject;

import java.time.ZonedDateTime;

public class NDayFilter implements IJsonArrayFilter {
    private static final String FILTER_PROPERTY = "created_at";

    private int days;

    public NDayFilter(int days){
        this.days = days;
    }

    @Override
    public boolean filter(JsonObject objectToFilter) {
        String strCreateDate = objectToFilter.get(FILTER_PROPERTY).getAsString();
        ZonedDateTime objCreateDate = ZonedDateTime.parse(strCreateDate);
        ZonedDateTime currentDate = ZonedDateTime.now();
        ZonedDateTime yesterdayDate = currentDate.minusDays(days);
        return objCreateDate.compareTo(yesterdayDate) > 0 && objCreateDate.compareTo(currentDate) < 0;
    }
}
