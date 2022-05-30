package br.com.dlweb.contato.Contatos;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class EditarFragment extends Fragment {

    private DatabaseConnnection databaseHelper;
    private Contato m;
    private EditText etNome;
    private EditText etNumero;
    private EditText etNumeroCasa;
    private EditText etNumeroTrabalho;


    public EditarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.contato_fragment_editar, container, false);
        Bundle b = getArguments();
        int id_mae = b.getInt("id");
        databaseHelper = new DatabaseConnnection(getActivity());
        m = databaseHelper.getByIdContato(id_mae);

        etNome = v.findViewById(R.id.editTextNomeContato);
        etNumero = v.findViewById(R.id.editTextNumeroContato);
        etNumeroCasa = v.findViewById(R.id.editTextNumeroCasa);
        etNumeroTrabalho = v.findViewById(R.id.editTextNumeroTrabalho);


        etNome.setText(m.getNome());
        etNumero.setText(String.valueOf(m.getNumero()));
        etNumeroCasa.setText(m.getNumeroCasa());
        etNumeroTrabalho.setText(m.getNumeroTrabalho());


        Button btnEditar = v.findViewById(R.id.buttonEditarMae);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_mae);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluirMae);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.dialog_excluir_Contatos);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir(id_mae);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Não faz nada
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return v;
    }

    private void editar (int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();

        } else if (etNumero.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o número do etNumero!", Toast.LENGTH_LONG).show();
        } else if (etNumeroCasa.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o Numero Casa!", Toast.LENGTH_LONG).show();
        } else if (etNumeroTrabalho.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a Numero Trabalho!", Toast.LENGTH_LONG).show();
        }  else {
            DatabaseConnnection databaseHelper = new DatabaseConnnection(getActivity());
            Contato m = new Contato();
            m.setId(id);
            m.setNome(etNome.getText().toString());
            m.setNumero(Integer.parseInt(etNumero.getText().toString()));
            m.setBairro(etNumeroCasa.getText().toString());
            m.setCidade(etNumeroTrabalho.getText().toString());
            databaseHelper.updateContato(m);
            Toast.makeText(getActivity(), "Contatos atualizada!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContato, new ListarFragment()).commit();
        }
    }

    private void excluir(int id) {
        m = new Contato();
        m.setId(id);
        databaseHelper.deleteContato(m);
        Toast.makeText(getActivity(), "Contatos excluída!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContato, new ListarFragment()).commit();
    }
}