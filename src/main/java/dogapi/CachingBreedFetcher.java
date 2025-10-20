package dogapi;

import java.lang.reflect.Array;
import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private BreedFetcher breedFetcher;
    private Map<String, List<String>> cache;
    public CachingBreedFetcher(List<String> subBreeds) {}
    private int callsMade = 0;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.breedFetcher = fetcher;
        this.cache = new HashMap<>();
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        if(this.cache.containsKey(breed)) {
            this.cache.get(breed);
        }
        callsMade++;
        List<String> subBreed = breedFetcher.getSubBreeds(breed);
        this.cache.put(breed, subBreed);

        return subBreed;
    }

    public int getCallsMade() {
        return callsMade;
    }
}