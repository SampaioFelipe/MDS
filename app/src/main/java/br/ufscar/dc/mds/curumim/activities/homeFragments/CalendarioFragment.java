package br.ufscar.dc.mds.curumim.activities.homeFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import br.ufscar.dc.mds.curumim.activities.HomeActivity;
import br.ufscar.dc.mds.curumim.utils.AtividadeAdapter;
import br.ufscar.dc.mds.curumim.activities.CadastroAtividadeActivity;
import br.ufscar.dc.mds.curumim.R;
import br.ufscar.dc.mds.curumim.entities.Atividade;

import static android.app.Activity.RESULT_OK;

public class CalendarioFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private CalendarView viewCalendario;

    private SimpleDateFormat dateFormat;
    private Date diaSelecionado;

    private DatabaseReference databaseRef;
    private ArrayList<Atividade> listaAtividadesDia;

    public CalendarioFragment() {
        // Required empty public constructor
    }

    public static CalendarioFragment newInstance() {
        return new CalendarioFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendario, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        viewCalendario = view.findViewById(R.id.calendarView);
        final FloatingActionButton botaoAdicionarAtividade = view.findViewById(R.id.botaoAdicionarAtividadeCalendario);

        viewCalendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                try {
                    diaSelecionado = dateFormat.parse(i2 + "/" + (i1 + 1) + "/" + i);
                } catch (ParseException e) {
                    diaSelecionado = new Date(viewCalendario.getDate());
                }
                botaoAdicionarAtividade.setVisibility(View.VISIBLE);
                showDayActivities();
            }
        });

        databaseRef = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getUid() + "/atividades");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

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

        botaoAdicionarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdicionaAtividade = new Intent(getActivity(), CadastroAtividadeActivity.class);
                startActivityForResult(intentAdicionaAtividade, 0);
                showDayActivities();
            }
        });

        diaSelecionado = new Date(viewCalendario.getDate());
    }

    @Override
    public void onResume() {
        super.onResume();

        ((HomeActivity) getActivity()).setActionbarTitle("Calendário");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (listaAtividadesDia == null) {
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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

        View view = getView();

        if (view != null) {
            ListView lista = view.findViewById(R.id.listaAtividades);
            lista.setAdapter(new AtividadeAdapter(listaAtividadesDiaSelecionado, getActivity()));
        }
    }
}
