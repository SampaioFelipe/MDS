package br.ufscar.dc.mds.curumim.activities.homeFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.ufscar.dc.mds.curumim.R;
import br.ufscar.dc.mds.curumim.activities.CadastroAtividadeActivity;
import br.ufscar.dc.mds.curumim.activities.CadastroCriancaActivity;
import br.ufscar.dc.mds.curumim.activities.CadastroCriancaActivity;
import br.ufscar.dc.mds.curumim.entities.Atividade;
import br.ufscar.dc.mds.curumim.entities.Crianca;
import br.ufscar.dc.mds.curumim.utils.CriancaRecyclerViewAdapter;
import butterknife.OnClick;


public class CriancaFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users/" +FirebaseAuth.getInstance().getUid() + "/criancas");
    ArrayList<Crianca> criancas = new ArrayList<>();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CriancaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CriancaFragment newInstance() {
        return new CriancaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crianca_list, container, false);


        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

//        // Geração de dados fakes
//        ArrayList<Crianca> criancas = new ArrayList<>();

            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<ArrayList<Crianca>> t = new GenericTypeIndicator<ArrayList<Crianca>>(){};

                    criancas = dataSnapshot.getValue(t);
                    //mostra o array das criança
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    criancas = new ArrayList<>();
                }
            });

//        criancas.add(new Crianca("Felipe"));
//        criancas.add(new Crianca("Júlia"));
//        criancas.add(new Crianca("Marcio"));
//        criancas.add(new Crianca("Pedro"));
//        criancas.add(new Crianca("Sylviane"));

        recyclerView.setAdapter(new CriancaRecyclerViewAdapter(criancas));

        ((FloatingActionButton) view.findViewById(R.id.botaoAdicionarPerfilCrianca)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CadastroCriancaActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction();
    }
}
