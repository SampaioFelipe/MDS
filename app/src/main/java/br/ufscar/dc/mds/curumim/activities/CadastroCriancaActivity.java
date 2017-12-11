package br.ufscar.dc.mds.curumim.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ufscar.dc.mds.curumim.R;
import br.ufscar.dc.mds.curumim.entities.Crianca;
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

    @BindView(R.id.camporestricoescrianca)
    EditText restricoesCriança;

    @BindView(R.id.campoalergiacrianca)
    EditText alergiaCrianca;

    @BindView(R.id.campopatologias)
    EditText patologiaCrianca;


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
        if(TextUtils.isEmpty(alergiaCrianca.getText().toString()))
        {
            alergiaCrianca.setError("Preenchimento obrigatório");
            return;
        }
        if(TextUtils.isEmpty(restricoesCriança.getText().toString()))
        {
            restricoesCriança.setError("Preenchimento obrigatório");
            return;
        }
        if(TextUtils.isEmpty(patologiaCrianca.getText().toString()))
        {
            patologiaCrianca.setError("Preenchimento obrigatório");
            return;
        }

        realizaCadastroBD();
    }

    public void realizaCadastroBD()
    {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getUid() + "/criancas");
        Crianca c = new Crianca();

        c.setNome(nomeCrianca.getText().toString());
        c.setIdade(idadeCrianca.getText().toString());
        c.setSexo(spinnerSexoCrianca.getSelectedItem().toString());
        c.setTipoSanguineo(spinnerTipoSanguineoCrianca.getSelectedItem().toString());
        c.setRestricoes(restricoesCriança.getText().toString());
        c.setAlergias(alergiaCrianca.getText().toString());

        databaseRef.setValue(c);

        Toast.makeText(this,"Cadastro realizado",Toast.LENGTH_LONG);

        CadastroCriancaActivity.this.finish();
    }
}
