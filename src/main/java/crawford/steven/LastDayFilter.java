package crawford.steven;

import com.google.gson.JsonObject;

import java.time.ZonedDateTime;

public class LastDayFilter implements IJsonArrayFilter {
    private static final String FILTER_PROPERTY = "created_at";


    @Override
    public boolean filter(JsonObject objectToFilter) {
        String strCreateDate = objectToFilter.get(FILTER_PROPERTY).getAsString();
        ZonedDateTime objCreateDate = ZonedDateTime.parse(strCreateDate);
        ZonedDateTime currentDate = ZonedDateTime.now();
        ZonedDateTime yesterdayDate = currentDate.minusDays(1);
        return objCreateDate.compareTo(yesterdayDate) > 0 && objCreateDate.compareTo(currentDate) < 0;
    }
}
