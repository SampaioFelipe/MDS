package br.ufscar.dc.mds.curumim.activities.homeFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import br.ufscar.dc.mds.curumim.R;
import br.ufscar.dc.mds.curumim.activities.InsercaoRegActivity;

import static android.app.Activity.RESULT_OK;

public class RegistroFragment extends Fragment {

    String[] curumins = {"Nome Samps", "Nome Marcio", "Nome Syl", "Nome Tim", "Nome Ju"};
    private Button butInserir;
    private EditText registro;
    private ImageView imageView;
    private Button btnCamera;
    private static final int PICK_IMAGE = 100;
    private static final int TAKE_IMAGE = 50;
    Uri imageUri;

    private OnFragmentInteractionListener mListener;

    public RegistroFragment() {
        // Required empty public constructor
    }

    public static RegistroFragment newInstance() {
        return new RegistroFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView);


        btnCamera = (Button) view.findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
                //openGallery();
            }
        });

        Button btnGaleria = (Button) view.findViewById(R.id.btnGaleria);
        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                takePicture();
                openGallery();
            }
        });

        //AUTOCOMPLETE
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, curumins);
        AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.optCrianca);
        textView.setThreshold(3);
        textView.setAdapter(adapter);

        registro = (EditText) view.findViewById(R.id.txtRegistro); // registro inserido
        butInserir = (Button) view.findViewById(R.id.btnInserir);
        butInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //procura no BD se crianca existe
                //insere registro no BD
                //notifica que registro foi inserido com sucesso
                mostrarMensagem();
            }
        });

        return view;
    }

    //ESCOLHER FOTO DA GALERIA
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    //TIRAR FOTO PELA CAMERA
    private void takePicture() {
        Intent picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //intent para abrir camera
        startActivityForResult(picture, TAKE_IMAGE);
    }

    //RESULTADO DA FOTO ESCOLHIDA OU TIRADA DA CAMERA
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if (requestCode == PICK_IMAGE) {
                imageUri = data.getData();//obter imagem escolhida
                imageView.setImageURI(imageUri);//setar imagem em image view
            } else { //foto tirada pela camera
                if (requestCode == TAKE_IMAGE) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data"); //obter imagem capturada
                    imageView.setImageBitmap(bitmap); //setar imagem em image view
                }
            }
        }
    }

    public void mostrarMensagem() {
        Intent intent = new Intent(getActivity(), InsercaoRegActivity.class);
        startActivity(intent);
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
