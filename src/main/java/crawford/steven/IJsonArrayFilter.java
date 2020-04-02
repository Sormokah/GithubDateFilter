package crawford.steven;

import com.google.gson.JsonObject;

public interface IJsonArrayFilter {
    boolean filter(JsonObject objectToFilter);
}
