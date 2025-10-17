package dogapi;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CachingBreedFetcherTest {

    @Test
    void testCachingAvoidsRedundantCalls() throws BreedFetcher.BreedNotFoundException {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();
        CachingBreedFetcher cachingFetcher = new CachingBreedFetcher(mock);

        List<String> firstCall = cachingFetcher.getSubBreeds("hound");
        List<String> secondCall = cachingFetcher.getSubBreeds("hound");

        assertEquals(List.of("afghan", "basset"), firstCall);
        assertEquals(firstCall, secondCall);
        assertEquals(1, mock.getCallCount(), "Fetcher should only be called once due to caching");
    }

    @Test
    void testExceptionStillPropagates() {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();
        CachingBreedFetcher cachingFetcher = new CachingBreedFetcher(mock);

        assertThrows(BreedFetcher.BreedNotFoundException.class, () -> cachingFetcher.getSubBreeds("dragon"));
        assertEquals(1, mock.getCallCount(), "Fetcher should be called even if breed is invalid");
    }

    @Test
    void testExceptionRepeatsCalls() {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();
        CachingBreedFetcher cachingFetcher = new CachingBreedFetcher(mock);

        assertThrows(BreedFetcher.BreedNotFoundException.class, () -> cachingFetcher.getSubBreeds("dragon"));
        assertThrows(BreedFetcher.BreedNotFoundException.class, () -> cachingFetcher.getSubBreeds("dragon"));
        assertEquals(2, mock.getCallCount(), "Fetcher should be called again even if breed is invalid");
    }

    // tests that the count of API calls is correctly recorded
    @Test
    void testCachingAvoidsRedundantCallsCheckCallsMade() throws BreedFetcher.BreedNotFoundException {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();
        CachingBreedFetcher cachingFetcher = new CachingBreedFetcher(mock);

        cachingFetcher.getSubBreeds("hound");
        cachingFetcher.getSubBreeds("hound");

        assertEquals(1, cachingFetcher.getCallsMade(),
                "Fetcher should only be called once due to caching. " +
                "Make sure that your implementation is recording how many calls have been made!");
    }

    @Test
    void testExceptionStillPropagatesCheckCallsMade() {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();
        CachingBreedFetcher cachingFetcher = new CachingBreedFetcher(mock);

        assertThrows(BreedFetcher.BreedNotFoundException.class, () -> cachingFetcher.getSubBreeds("dragon"));
        assertEquals(1, cachingFetcher.getCallsMade(),
                "Fetcher should be called even if breed is invalid. " +
                "Make sure that your implementation is recording how many calls have been made!");
    }

    @Test
    void testExceptionRepeatsCallsCheckCallsMade() {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();
        CachingBreedFetcher cachingFetcher = new CachingBreedFetcher(mock);

        assertThrows(BreedFetcher.BreedNotFoundException.class, () -> cachingFetcher.getSubBreeds("dragon"));
        assertThrows(BreedFetcher.BreedNotFoundException.class, () -> cachingFetcher.getSubBreeds("dragon"));
        assertEquals(2, cachingFetcher.getCallsMade(),
                "Fetcher should be called again even if breed is invalid. " +
                "Make sure that your implementation is recording how many calls have been made!");
    }
}
