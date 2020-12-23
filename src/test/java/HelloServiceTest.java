import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class HelloServiceTest {
    private static final String WELCOME = "Hello";
    private static final String FALLBACK_ID_WELCOME = "Hola";

    @Test
    @DisplayName("Should return 'FALLBACK_NAME' value when query param 'name' is null")
    public void test_prepareGreeting_nullName_returnsGreetingWithFallbackName() {
        // given
        HelloService SUT = alwaysReturningHelloRepository();

        // when
        String result = SUT.prepareGreeting(null, "-1");

        // then
        assertThat(result, is(WELCOME + " " + HelloService.FALLBACK_NAME + "!"));
    }

    @Test
    @DisplayName("Should return 'name' value when query param 'name' is not null")
    public void test_prepareGreeting_name_returnsGreetingWithName() {
        // given
        String name = "Mateo";
        HelloService SUT = alwaysReturningHelloRepository();

        // when
        String result = SUT.prepareGreeting(name, "-1");

        // then
        assertThat(result, is(WELCOME + " " + name + "!"));
    }

    @Test
    @DisplayName("Should return greeting with 'fallbackLang' when 'lang' does not exist")
    public void test_prepareGreeting_nonExistingLang_returnsGreetingWithFallbackLang() {
        // given
        LangRepository repositoryMock = new LangRepository() {
            @Override
            Optional<Lang> findById(Integer id) {
                return Optional.empty();
            }
        };
        HelloService SUT = new HelloService(repositoryMock);

        // when
        String result = SUT.prepareGreeting(null, "-1");

        // then
        assertThat(result, is(HelloService.FALLBACK_LANG.getWelcomeMsg() + " " + HelloService.FALLBACK_NAME + "!"));
    }

    @Test
    @DisplayName("Should return greeting with 'fallbackIdWelcome' when 'lang' param is null")
    public void test_prepareGreeting_nullLang_returnsGreetingWithFallbackIdLang() {
        // given
        LangRepository repositoryMock = fallbackLangIdRepository();
        HelloService SUT = new HelloService(repositoryMock);

        // when
        String result = SUT.prepareGreeting(null, null);

        // then
        assertThat(result, is(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!"));
    }

    @Test
    @DisplayName("Should return greeting with 'fallbackIdWelcome' when 'lang' param is non-numeric")
    public void test_prepareGreeting_textLang_returnsGreetingWithFallbackIdLang() {
        // given
        LangRepository repositoryMock = fallbackLangIdRepository();
        HelloService SUT = new HelloService(repositoryMock);

        // when
        String result = SUT.prepareGreeting(null, "abc");

        // then
        assertThat(result, is(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!"));
    }

    private LangRepository fallbackLangIdRepository() {
        return new LangRepository() {
            @Override
            Optional<Lang> findById(Integer id) {
                if (id.equals(HelloService.FALLBACK_LANG.getId())) {
                    return Optional.of(new Lang(null, FALLBACK_ID_WELCOME, null));
                }
                return Optional.empty();
            }
        };
    }

    private HelloService alwaysReturningHelloRepository() {
        LangRepository repositoryMock = mock(LangRepository.class);
        HelloService SUT = new HelloService(repositoryMock);
        given(repositoryMock.findById(-1)).willReturn(Optional.of(new Lang(null, WELCOME, null)));
        return SUT;
    }
}