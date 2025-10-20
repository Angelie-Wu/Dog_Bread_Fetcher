package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {

        String url = "https://dog.ceo/api/breed/" + breed +"/list";
        final Request request = new Request.Builder()
                .url(url)
                .build();
        try(Response response = client.newCall(request).execute()){

            final String body = response.body().string();
            final JSONObject responseBody = new JSONObject(body);
            if(!response.isSuccessful())
                throw new BreedNotFoundException(breed);
            if("error".equals(responseBody.getString("status")))
                throw new BreedNotFoundException(breed);

            JSONArray messageArray = responseBody.getJSONArray("message");
            List<String> subBreeds = new ArrayList<>();

            for(int i = 0; i < messageArray.length(); i++){
                subBreeds.add(messageArray.getString(i));
            }
            return subBreeds;

        }catch(IOException | JSONException event){
            throw new BreedNotFoundException(breed);
        }
    }
}