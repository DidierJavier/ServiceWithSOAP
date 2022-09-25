package co.com.sofka.stepdefinition.soap;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static co.com.sofka.questions.ReturnStringValue.returnStringValue;
import static co.com.sofka.tasks.DoPost.doPost;
import static co.com.sofka.utils.FileUtilities.readFile;
import static co.com.sofka.utils.Log4jValues.LOG4J_PROPERTIES_FILE_PATH;
import static com.google.common.base.StandardSystemProperty.USER_DIR;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.containsString;

public class ConvertCurrencyNameStepDefinition {

    private static final Logger LOGGER = Logger.getLogger(ConvertCurrencyNameStepDefinition.class);
    private static final String CURRENCY_ISO_CODE_XML = System.getProperty("user.dir") + "/src/test/resources/file/soap/currencyISOCode.xml";
    private static final String CURRENCY_ISO_CODE = "[isoCode]";
    private static final String URL_BASE = "http://webservices.oorsprong.org";
    private static final String RESOURCE = "/websamples.countryinfo/CountryInfoService.wso";
    private final HashMap<String, Object> headers = new HashMap<>();
    private String bodyRequest;
    private final Actor actor = Actor.named("Didier");
    @Dado("que el usuario está en el recurso web indicando el nombre de moneda ISO {string}")
    public void queElUsuarioEstaEnElRecursoWebIndicandoElNombreDeMonedaISO(String isoCode) {
        setUpLog4j2();
        LOGGER.info("Definiendo el recurso");
        actor.can(CallAnApi.at(URL_BASE));
        headers.put("Content-Type", "text/xml;charset=UTF-8");
        headers.put("SOAPAction", "");
        bodyRequest = defineBodyRequest(isoCode);
    }

    @Cuando("el usuario genera la consulta")
    public void elUsuarioGeneraLaConsulta() {
        LOGGER.info("Generando la consulta");
        actor.attemptsTo(
                doPost()
                        .usingTheResource(RESOURCE)
                        .withHeaders(headers)
                        .andBodyRequest(bodyRequest)
        );
    }
    @Entonces("visualizará el nombre formal de moneda como {string}")
    public void visualizaraElNombreFormalDeMonedaComo(String currencyName) {
        LOGGER.info("Obteniendo la respuesta");
        String soapResponse =
                new String(LastResponse.received().answeredBy(actor).asByteArray(), StandardCharsets.UTF_8);
        actor.should(
                seeThatResponse("El codigo de respuesta debe ser: " + HttpStatus.SC_OK,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                ),

                seeThat(
                        "El nombre formal de la moneda debe ser: " + currencyName,
                        returnStringValue(soapResponse),
                        containsString(currencyName))
        );
    }

    private String defineBodyRequest(String isoCode){
        return readFile(CURRENCY_ISO_CODE_XML).replace(CURRENCY_ISO_CODE, isoCode);
    }

    private void setUpLog4j2() {
        PropertyConfigurator.configure(USER_DIR.value() + LOG4J_PROPERTIES_FILE_PATH.getValue());
    }

}
