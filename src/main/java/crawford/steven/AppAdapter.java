package crawford.steven;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppAdapter {

    /**
     * Returns http response for given url via GET method
     *
     * @param strURL URL to fetch data from.
     * @return Returns HttpResponse object.
     */
    public static HttpResponse getHttpResponse(String strURL) throws Exception {

        final String METHOD_NAME = "getHttpResponse";

        // Variable to hold return value
        HttpResponse objToReturn;
        String authToken = getAuthTokenFromResources();
        try {
            HttpClient client = HttpClients.createMinimal();
            HttpGet request = new HttpGet(strURL);
            request.setHeaders(new BasicHeader[]{new BasicHeader("Accept", " application/vnd.github.cloak-preview"),
                    new BasicHeader("Authorization", "Bearer " + authToken)});
            objToReturn = client.execute(request);

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new Exception();
        }

        return objToReturn;
    }

    private static String trimSlash(URL path) {
        try {
            return Paths.get(path.toURI()).toFile().getAbsolutePath();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private static String loadResourceAsString(String strTarget) throws IOException {
        String strJSON = trimSlash(AppAdapter.class.getResource(strTarget));
        return new String(Files.readAllBytes(Paths.get(strJSON)));
    }

    private static String getAuthTokenFromResources() throws IOException{
        String strJsonAuth = null;
        try{

            strJsonAuth = loadResourceAsString("/secret.json");
        } catch (IOException ioe){
            throw new IOException("Requres resource \"secret.json\" with \"github_auth\" property.");
        }
        JsonObject objAuths = new Gson().fromJson(strJsonAuth, JsonObject.class);
        String gitHubAuth = objAuths.get("github_auth").getAsString();

        return gitHubAuth;
    }

}
