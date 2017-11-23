package br.ufscar.dc.mds.curumim;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CalendarioActivity extends AppCompatActivity {

    private CalendarView viewCalendario;
    private LinearLayout viewListaAtividades;
    private LinearLayout.LayoutParams textViewParams;

    private String selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        viewCalendario = findViewById(R.id.calendarView);
        viewListaAtividades = findViewById(R.id.scrollViewAtividades);
        textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        viewCalendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                selectedDay = day + "/" + (month + 1) + "/" + year;
                createActivityView();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        long data = viewCalendario.getDate();
        selectedDay = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(data);

        createActivityView();
    }

    private void createActivityView() {
        TextView atividade = new TextView(this);
        atividade.setTextSize((float) 24.5);
        atividade.setText(selectedDay);
        atividade.setLayoutParams(textViewParams);

        View hline = new View(this);
        LinearLayout.LayoutParams hlineViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.hline_height));
        hline.setLayoutParams(hlineViewParams);
        hline.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        viewListaAtividades.addView(atividade);
        viewListaAtividades.addView(hline);
    }
}
