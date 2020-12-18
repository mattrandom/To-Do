import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HelloServiceTest {
    private final HelloService SUT = new HelloService();

    @Test
    @DisplayName("Should return 'FALLBACK_NAME' value when query param 'name' is null")
    public void test_prepareGreeting_null_returnsGreetingWithFallback() {
        // given + when
        String result = SUT.prepareGreeting(null);

        // then
        assertThat(result, is("Hello " + HelloService.FALLBACK_NAME + "!"));
    }

    @Test
    @DisplayName("Should return 'name' value when query param 'name' is not null")
    public void test_prepareGreeting_name_returnsGreetingWithName() {
        // given
        String name = "Mateo";

        // when
        String result = SUT.prepareGreeting(name);

        // then
        assertThat(result, is("Hello " + name + "!"));
    }
}