package co.com.sofka.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ReturnStringValue implements Question<String> {

    private final String systemValue;

    public ReturnStringValue(String systemValue) {
        this.systemValue = systemValue;
    }

    @Override
    public String answeredBy(Actor actor) {
        return systemValue;
    }

    public static ReturnStringValue returnStringValue(String systemValue){
        return new ReturnStringValue(systemValue);
    }
}
