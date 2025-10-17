package dogapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void getNumberOfSubBreedsValidBreed() {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();

        assertEquals(2, Main.getNumberOfSubBreeds("hound", mock));
    }

    @Test
    void getNumberOfSubBreedsInvalidBreed() {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();

        assertEquals(0, Main.getNumberOfSubBreeds("cat", mock));
    }

    @Test
    void exceptionTypeTest() {
        Exception bfe = new BreedFetcher.BreedNotFoundException("hound");
        assertTrue(bfe instanceof Exception && !(bfe instanceof RuntimeException),
                "BreedFetcher.BreedNotFoundException must be a checked exception.");
    }

}