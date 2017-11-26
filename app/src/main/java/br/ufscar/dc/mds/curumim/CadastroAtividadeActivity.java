package br.ufscar.dc.mds.curumim;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.Calendar;
import java.util.Locale;

public class CadastroAtividadeActivity extends AppCompatActivity {

    String nome, local;
    int hora, minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_atividade);

        ImageButton botaoImagemHorarioAtividade = findViewById(R.id.imageButtonHorarioAtividade);
        ImageButton botaoConcluido = findViewById(R.id.botaoConcluidoCadastroAtividade);
        final EditText editTextNomeAtividade = findViewById(R.id.editTextNomeAtividade);
        final PlaceAutocompleteFragment fragmentLocal = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.editTextLocalAtividade);
        Toolbar toolbar = findViewById(R.id.toolbarCadastroAtividade);

        botaoImagemHorarioAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendario = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(CadastroAtividadeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        hora = i;
                        minuto = i1;
                    }
                },
                        calendario.get(Calendar.HOUR_OF_DAY), calendario.get(Calendar.MINUTE), true);

                timePickerDialog.show();
            }
        });

        fragmentLocal.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                local = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                local = "Selecione um local";
            }
        });

        botaoConcluido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome = editTextNomeAtividade.getText().toString();

                if (local == null)
                {
                    Toast.makeText(CadastroAtividadeActivity.this, "Selecione um local", Toast.LENGTH_SHORT).show();
                } else if (nome.isEmpty()) {
                    Toast.makeText(CadastroAtividadeActivity.this, "Digite um nome para a atividade", Toast.LENGTH_SHORT).show();
                } else {
                    String novaAtividade = nome + "@@sep@@" + local + "@@sep@@" +
                            String.format(Locale.US, "%02d", hora) + ":" + String.format(Locale.US, "%02d", minuto);
                    Intent intent = new Intent();
                    intent.putExtra("atividade", novaAtividade);
                    setResult(Activity.RESULT_OK, intent);
                    CadastroAtividadeActivity.this.finish();
                }
            }
        });

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}