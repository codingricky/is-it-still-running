import models.PersistableToken;
import org.junit.Test;
import play.libs.F.Callback;
import play.test.TestBrowser;

import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static play.test.Helpers.*;

public class AcceptanceTest {

    private static final int PORT = 3333;
    private static final String LOCALHOST = "http://localhost:" + PORT;

    @Test
    public void login() {
        runTest(new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                createUserAndLogin(browser);

            }
        });
    }

    private void createUserAndLogin(TestBrowser browser) {
        browser.goTo(LOCALHOST);
        browser.find("a", withText("Sign Up")).click();

        assertThat(browser.title()).contains("Sign Up");
        browser.fill("#email").with("testing@testing.com");
        browser.find("button", withText("Create Account")).click();

        PersistableToken persistableToken = PersistableToken.findByEmail("testing@testing.com");
        assertThat(persistableToken).isNotNull();

        browser.goTo(LOCALHOST + "/signup/" + persistableToken.uuid);
        assertThat(browser.title()).contains("Sign Up");

        browser.fill("#firstName").with("John");
        browser.fill("#lastName").with("Smith");
        browser.fill("#password_password1").with("password");
        browser.fill("#password_password2").with("password");
        browser.find("button", withText("Create Account")).click();

        browser.$(".alert").contains("You can log in now");
        browser.fill("#username").with("testing@testing.com");
        browser.fill("#password").with("password");
        browser.find("button", withText("Login")).click();

        assertThat(browser.title()).contains("running");
    }


    private void runTest(Callback<TestBrowser> callback) {
        running(testServer(PORT, fakeApplication(inMemoryDatabase())), FIREFOX, callback);
    }

}
