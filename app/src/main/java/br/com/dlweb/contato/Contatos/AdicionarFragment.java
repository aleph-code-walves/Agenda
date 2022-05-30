package br.com.dlweb.contato.Contatos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.dlweb.contato.R;
import br.com.dlweb.contato.database.DatabaseConnnection;

public class AdicionarFragment extends Fragment {

    private EditText etNome;
    private EditText etCep;
    private EditText etLogradouro;
    private EditText etNumero;
    private EditText etBairro;
    private EditText etCidade;
    private EditText etFixo;
    private EditText etCelular;
    private EditText etComercial;
    private EditText etDataNascimento;
    private EditText etNumeroCasa;
    private EditText etNumeroTrabalho;


    public AdicionarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.contato_fragment_adicionar, container, false);

        etNome = v.findViewById(R.id.editTextNomeContato);
        etNumero = v.findViewById(R.id.editTextNumeroContato);
        etNumeroCasa = v.findViewById(R.id.editTextNumeroCasa);
        etNumeroTrabalho = v.findViewById(R.id.editTextNumeroTrabalho);


        Button btnAdicionar = v.findViewById(R.id.buttonAdicionarMae);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        }  else if (etNumero.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o número de telefone!", Toast.LENGTH_LONG).show();
        } else if (etNumeroCasa.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o Numero Casa!", Toast.LENGTH_LONG).show();
        } else if (etNumeroTrabalho.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a Numero Trabalho!", Toast.LENGTH_LONG).show();
        }
        else {
            DatabaseConnnection databaseHelper = new DatabaseConnnection(getActivity());
            Contato m = new Contato();

            m.setNome(etNome.getText().toString());
            m.setNumero(Integer.parseInt(etNumero.getText().toString()));
            m.setBairro(etNumeroCasa.getText().toString());
            m.setCidade(etNumeroTrabalho.getText().toString());

            databaseHelper.createContato(m);
            Toast.makeText(getActivity(), "Mãe salva!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContato, new ListarFragment()).commit();
        }
    }
}