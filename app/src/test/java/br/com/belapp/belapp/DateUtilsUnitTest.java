package br.com.belapp.belapp;


import org.junit.Test;

import java.util.Calendar;

import br.com.belapp.belapp.utils.DateUtils;

import static org.junit.Assert.assertEquals;


public class DateUtilsUnitTest {

    @Test
    public void verificarConverterDataParaString(){
        Calendar data = Calendar.getInstance();
        data.set(2019,0,25);

        assertEquals("25/01/2019",DateUtils.converterDataParaString(data));
    }
}
