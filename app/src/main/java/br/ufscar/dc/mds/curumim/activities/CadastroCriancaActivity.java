package br.ufscar.dc.mds.curumim.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.ufscar.dc.mds.curumim.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroCriancaActivity extends AppCompatActivity {

    @BindView(R.id.sexocrianca)
    Spinner spinnerSexoCrianca;

    @BindView(R.id.campotiposanguineo)
    Spinner spinnerTipoSanguineoCrianca;

    @BindView(R.id.campocadnomecrianca)
    EditText nomeCrianca;

    @BindView(R.id.campoidadecrianca)
    EditText idadeCrianca;

    @BindView(R.id.campoalergiacrianca)
    EditText alergiaCrianca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_crianca);

        ButterKnife.bind(this);

        /*Popula Spinner de sexo da criança*/
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexoCrianca.setAdapter(adapter);

        /*Popula o spinner de tipo sanguíneo*/
        adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_sangue_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoSanguineoCrianca.setAdapter(adapter);
    }

    @OnClick(R.id.botaocadastrarcrianca)
    public void cadastraCrianca()
    {
        if(TextUtils.isEmpty(nomeCrianca.getText().toString())) {
            nomeCrianca.setError("O campo não pode estar vazio");
            return;
        }
        if(TextUtils.isEmpty(idadeCrianca.getText().toString()))
        {
            idadeCrianca.setError("Preencha a data de nascimento da criança");
            return;
        }
        if(TextUtils.isEmpty(alergiaCrianca.getText().toString()))
        {
            alergiaCrianca.setError("Preenchimento obrigatório");
            return;
        }
        if(TextUtils.equals(spinnerSexoCrianca.getSelectedItem().toString(),"Selecionar"))
        {
            Toast.makeText(this,"Selecione o sexo da criança", Toast.LENGTH_SHORT);
            return;
        }
        if(TextUtils.equals(spinnerTipoSanguineoCrianca.getSelectedItem().toString(),"Selecionar"))
        {
            Toast.makeText(this,"Selecione o tipo sanguíneo da criança",Toast.LENGTH_SHORT);
            return;
        }

        //método de cadastrar crianças
    }
}
