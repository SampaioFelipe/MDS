package br.ufscar.dc.mds.curumim.activities.homeFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import br.ufscar.dc.mds.curumim.R;
import br.ufscar.dc.mds.curumim.activities.HomeActivity;
import br.ufscar.dc.mds.curumim.utils.TipOfTheDay;
import br.ufscar.dc.mds.curumim.entities.Atividade;
import br.ufscar.dc.mds.curumim.utils.TarefasHojeAdapter;

public class InitialFragment extends Fragment {

    private TextView tipOfTheDay;


    private OnFragmentInteractionListener mListener;

    private TarefasHojeAdapter tarefasHojeAdapter;

    private ArrayList<Atividade> listaAtividadesDia;

    public InitialFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InitialFragment newInstance() {

        return new InitialFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.tarefa_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        listaAtividadesDia = new ArrayList<>();

        tarefasHojeAdapter = new TarefasHojeAdapter(listaAtividadesDia);

        recyclerView.setAdapter(tarefasHojeAdapter);

        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getUid() + "/atividades");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<ArrayList<Atividade>> t = new GenericTypeIndicator<ArrayList<Atividade>>() {
                };

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                ArrayList<Atividade> atividades = dataSnapshot.getValue(t);

                if (atividades != null) {

                    listaAtividadesDia.clear();

                    Collections.sort(atividades);

                    for (Atividade atividade : atividades){
                        Log.d("teste", atividade.nome);
                        if (Objects.equals(dateFormat.format(atividade.horario), dateFormat.format(new Date()))) {
                            listaAtividadesDia.add(atividade);
                        }
                    }

                    tarefasHojeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                listaAtividadesDia = new ArrayList<>();
            }
        });

        tipOfTheDay = view.findViewById(R.id.type_of_the_day_text);

        tipOfTheDay.setText(TipOfTheDay.getTipOfTheDay(getContext()));

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    @Override
    public void onResume() {
        super.onResume();

        ((HomeActivity) getActivity()).setActionbarTitle("Home");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
