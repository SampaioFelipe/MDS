package br.ufscar.dc.mds.curumim;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;

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
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CalendarioActivity extends AppCompatActivity {

    private CalendarView viewCalendario;

    private SimpleDateFormat dateFormat;
    private Date diaSelecionado;

    private DatabaseReference databaseRef;
    private ArrayList<Atividade> listaAtividadesDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        viewCalendario = findViewById(R.id.calendarView);
        final FloatingActionButton botaoAdicionarAtividade = findViewById(R.id.botaoAdicionarAtividadeCalendario);

        databaseRef = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getUid() + "/atividades");

        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        viewCalendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                try {
                    diaSelecionado = dateFormat.parse(day + "/" + (month + 1) + "/" + year);
                } catch (ParseException e) {
                    diaSelecionado = new Date(viewCalendario.getDate());
                }
                botaoAdicionarAtividade.setVisibility(View.VISIBLE);
                showDayActivities();
            }
        });

        botaoAdicionarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdicionaAtividade = new Intent(CalendarioActivity.this, CadastroAtividadeActivity.class);
                startActivityForResult(intentAdicionaAtividade, 0);
                showDayActivities();
            }
        });

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Atividade>> t = new GenericTypeIndicator<ArrayList<Atividade>>() {
                };

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (listaAtividadesDia == null)
                {
                    listaAtividadesDia = new ArrayList<>();
                }

                String novaAtividadeCodificada = data.getStringExtra("atividade");
                Atividade novaAtividade = new Atividade(dateFormat.format(diaSelecionado), novaAtividadeCodificada);
                listaAtividadesDia.add(novaAtividade);
                databaseRef.setValue(listaAtividadesDia);
                showDayActivities();
            }
        }
    }

    private void showDayActivities() {
        ArrayList<Atividade> listaAtividadesDiaSelecionado = new ArrayList<>();

        if (listaAtividadesDia != null) {
            Collections.sort(listaAtividadesDia);
            for (Atividade a : listaAtividadesDia) {
                if (Objects.equals(dateFormat.format(a.horario), dateFormat.format(diaSelecionado))) {
                    listaAtividadesDiaSelecionado.add(a);
                }
            }
        }

        ListView lista = findViewById(R.id.listaAtividades);
        lista.setAdapter(new AtividadeAdapter(listaAtividadesDiaSelecionado, CalendarioActivity.this));
    }
}
