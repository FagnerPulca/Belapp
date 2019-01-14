package br.com.belapp.belapp;


import org.junit.Test;

import java.util.Calendar;

import br.com.belapp.belapp.utils.DateUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateUtilsUnitTest {

    @Test
    public void verificarConverterDataParaString(){
        Calendar data = Calendar.getInstance();
        data.set(2019,0,25);

        assertEquals("25/01/2019",DateUtils.converterDataParaString(data));
    }
  
    public void verificarSomaDiasComDataEspecifica(){
        String data = "01/09/2018";
        int dias1 = 6;
        int dias2 = 30;
        int dias3 = 365;
        String dataEsperada1 = "07/09/2018";
        String dataEsperada2 = "01/10/2018";
        String dataEsperada3 = "01/09/2019";

        // verifica soma dentro do mesmo mês
        assertEquals(DateUtils.getSomaDiasComDataEspecifica(dias1, data), dataEsperada1);
        // verifica soma em um mes
        assertEquals(DateUtils.getSomaDiasComDataEspecifica(dias2, data), dataEsperada2);
        // verifica soma em um ano
        assertEquals(DateUtils.getSomaDiasComDataEspecifica(dias3, data), dataEsperada3);

    }

    @Test
    public void verificarSubtracaoDiasComDataEspecifica(){
        String data = "01/09/2018";
        int dias1 = 6;
        int dias2 = 30;
        int dias3 = 365;
        String dataEsperada1 = "26/08/2018";
        String dataEsperada2 = "02/08/2018";
        String dataEsperada3 = "01/09/2017";

        // verifica subtracao dentro do mesmo mês
        assertEquals(dataEsperada1, DateUtils.getSubtracaoDiasComDataEspecifica(dias1, data));
        // verifica subtracao em um mes
        assertEquals(dataEsperada2, DateUtils.getSubtracaoDiasComDataEspecifica(dias2, data));
        // verifica subtracao em um ano
        assertEquals(dataEsperada3, DateUtils.getSubtracaoDiasComDataEspecifica(dias3, data));

    }

    @Test
    public void verificarIsDataFutura(){
        String dataAtual = DateUtils.getDataAtual();
        String dataFutura = DateUtils.getSomaDiasComDataEspecifica(5, dataAtual);
        String dataPassada = DateUtils.getSubtracaoDiasComDataEspecifica(5, dataAtual);

        // tem que retornar true
        boolean resultado1 = DateUtils.isDataFutura(dataFutura);
        assertTrue(resultado1);

        // tem que retornar false
        boolean resultado2 = DateUtils.isDataFutura(dataPassada);
        assertFalse(resultado2);

        // tem que retornar false
        boolean resultado3 = DateUtils.isDataFutura(dataAtual);
        assertFalse(resultado3);

    }

    @Test
    public void verificarDiferencaEntreDatasEspecificas(){
        String data1 = "10/01/2019";
        String data2 = "12/01/2019";

        // resultado esperado da operação data2 - data1
        int resultadoEsperado1 = 2;
        assertEquals(resultadoEsperado1, DateUtils.getDiferencaEntreDuasDatasEspecificas(data1, data2));

        // resultado esperado da operação data1 - data2
        int resultadoEsperado2 = -2;
        assertEquals(resultadoEsperado2, DateUtils.getDiferencaEntreDuasDatasEspecificas(data2, data1));

        // resultado esperado da operação data1 - data1
        int resultadoEsperado3 = 0;
        assertEquals(resultadoEsperado3, DateUtils.getDiferencaEntreDuasDatasEspecificas(data1, data1));
    }

    @Test
    public void verificarChecarSeDataPertenceAoMes(){
        String data = "01/01/2019";
        // verifica o caso verdadeiro
        int mes1 = 1;
        assertTrue(DateUtils.checarSeDataPertenceAoMes(data, mes1));

        // verifica o caso falso
        int mes2 = 5;
        assertFalse(DateUtils.checarSeDataPertenceAoMes(data, mes2));
    }


    @Test
    public void verificarIsDataPresente(){
        String data = "11/09/2001";
        String dataAtual = DateUtils.getDataAtual();
        boolean resposta1 = DateUtils.isDataPresente(data);
        boolean resposta2 =DateUtils.isDataPresente(dataAtual);
        assertFalse(resposta1);

        assertTrue(resposta2);
    }
}
