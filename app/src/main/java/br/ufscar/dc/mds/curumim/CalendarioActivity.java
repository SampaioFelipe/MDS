package br.ufscar.dc.mds.curumim;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CalendarioActivity extends AppCompatActivity {

    private CalendarView viewCalendario;
    private LinearLayout viewListaAtividades;
    private LinearLayout.LayoutParams textViewParams;

    private DatabaseReference databaseRef;

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat hourFormat;
    private Date diaSelecionado;

    private ArrayList<Atividade> listaAtividadesDia;

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        viewCalendario = findViewById(R.id.calendarView);
        FloatingActionButton botaoAdicionarAtividade = findViewById(R.id.botaoAdicionarAtividadeCalendario);
        viewListaAtividades = findViewById(R.id.scrollViewAtividades);
        textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        databaseRef = FirebaseDatabase.getInstance().getReference("atividades/" + FirebaseAuth.getInstance().getUid());

        dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.US);
        hourFormat = new SimpleDateFormat("HH:mm", Locale.US);

        viewCalendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                try {
                    diaSelecionado = dateFormat.parse(day + "/" + (month + 1) + "/" + year);
                } catch (ParseException e) {
                    diaSelecionado = new Date(viewCalendario.getDate());
                }
                showDayActivities();
            }
        });

        i = 0;
        botaoAdicionarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaAtividadesDia.add(new Atividade("Novo item " + i++, "Aqui", diaSelecionado));
                databaseRef.setValue(listaAtividadesDia);
                showDayActivities();
            }
        });

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Atividade>> t = new GenericTypeIndicator<ArrayList<Atividade>>() {};

                listaAtividadesDia = dataSnapshot.getValue(t);
                showDayActivities();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listaAtividadesDia = new ArrayList<>();
            }
        });

        diaSelecionado = new Date(viewCalendario.getDate());
    }

    private void showDayActivities() {

        viewListaAtividades.removeAllViews();

        for (Atividade a: listaAtividadesDia) {
            if (Objects.equals(dateFormat.format(a.horario), dateFormat.format(diaSelecionado))) {
                String textoAtividade = a.nome + "\n" + a.local + "\n" +
                        dateFormat.format(a.horario) + "\n" +
                        hourFormat.format(a.horario);

                TextView atividade = new TextView(this);
                atividade.setTextSize((float) 24.5);
                atividade.setText(textoAtividade);
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
    }
}
