package co.com.sofka.runner.soap;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/test/resources/feature/soap/currencyName.feature"},
        glue = {"co/com/sofka/stepdefinition/soap"},
        tags = ""
)

public class ConvertCurrencyNameTest {

}
