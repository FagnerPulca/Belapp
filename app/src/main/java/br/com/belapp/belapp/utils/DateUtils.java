package br.com.belapp.belapp.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * Metodo utilizado para chamar um diálogo para selecionar uma data.
     * @param context
     * @param title
     * @param triggerAfterDateSetDialog
     */
    public static void showDialogDate(final Context context, String title, final ITriggerAfterDateSetDialog triggerAfterDateSetDialog) {
        Calendar calendar = Calendar.getInstance();

        final DatePickerDialog dateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yearSelected, int monthSelected, int daySelected) {
                //Pega a data selecionada e instancia um objeto Calendar.
                Calendar calendarSecundary = Calendar.getInstance();
                calendarSecundary.set(yearSelected, monthSelected, daySelected);

                //Executa o gatilho de alterações(TriggerAfterDateSetDialog) passando a data como argumento.
                triggerAfterDateSetDialog.afterDateSet(calendarSecundary.getTime());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        dateDialog.setCancelable(true);
        dateDialog.setCanceledOnTouchOutside(true);
        dateDialog.setTitle(title);
        dateDialog.show();
    }

    /**
     * Interface utilizada para polimorfismo, aonde objetos de classes que implementam está interface poderão ser utilizados para o gatilho do diálogo de escolha.
     */
    public interface ITriggerAfterChoice {

        //Método que será chamado utilizando um objeto de uma classe que implementa esta interface e concretizou o corpo deste método.
        void afterChoice(int choiceCode);
    }

    /**
     * Interface usada para efetuar as operações de interesse quando confirmar a data no diálogo da data.
     */
    public interface ITriggerAfterDateSetDialog {
        void afterDateSet(Date date);
    }
    public static void showDialogDate(Context context, ITriggerAfterDateSetDialog triggerAfterDateSetDialog) {
        showDialogDate(context, "Selecione a data", triggerAfterDateSetDialog);
    }
    /**
     * Metodo que obtem a data atual do smartphone.
     * @return data no formato dd/mm/YYYY.
     */
    public static String getDataAtual(){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    /**
     * Verifica se data á sábado .
     *
     * @param   data        Um objeto Calendar
     * @return  boolean
     */
    public static boolean checarSeSabado(Calendar data){
        return data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }


    /**
     * Verifica se data e domingo .
     *
     * @param   data        Um objeto Calendar
     * @return  boolean
     */
    public static boolean checarSeDomingo(Calendar data){
        return data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }


}
