package crawford.steven;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.time.ZonedDateTime;

public class Driver {

    //constants
    private static final String urlToQuery = "https://api.github.com/users/jstortz/events/orgs/rcscode";


    public static void main(String[] args) throws Exception {
       HttpResponse resp =  AppAdapter.getHttpResponse(urlToQuery);
        JsonArray results = new Gson().fromJson(EntityUtils.toString(resp.getEntity()), JsonArray.class);

        IJsonArrayFilter filter = new NDayFilter(3);
        JsonArray arrFilteredArray = filter(results, filter);

        System.out.println(">>>All Results: " + results.size() + "<<<");
        System.out.println(">>>Filtered Results: " + arrFilteredArray.size() + "<<<");
    }

    private static JsonArray filter(JsonArray arrSource, IJsonArrayFilter filter){
        JsonArray arrToReturn = new JsonArray();
        for(int i = 0; i<arrSource.size(); i++){
            boolean match = filter.filter(arrSource.get(i).getAsJsonObject());
            if(match){
                arrToReturn.add(arrSource.get(i));
            }
        }
        return arrToReturn;
    }

}
