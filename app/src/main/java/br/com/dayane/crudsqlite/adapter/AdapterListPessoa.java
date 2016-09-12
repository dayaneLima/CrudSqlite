package br.com.dayane.crudsqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import br.com.dayane.crudsqlite.MainActivity;
import br.com.dayane.crudsqlite.R;
import br.com.dayane.crudsqlite.model.Pessoa;

/**
 * Created by Dayane on 10/09/2016.
 */
public class AdapterListPessoa extends BaseAdapter{

    private final MainActivity context;
    private final List<Pessoa> listaPessoa;

    public AdapterListPessoa(MainActivity context, List<Pessoa> listaPessoa){
        this.context = context;
        this.listaPessoa = listaPessoa;
    }

    @Override
    public int getCount() {
        return (this.listaPessoa != null)?this.listaPessoa.size():0;
    }

    @Override
    public Object getItem(int position) {
        return this.listaPessoa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.listaPessoa.get(position).getId();
    }

    public void removeItem(int position){
        this.listaPessoa.remove(position);
    }

    public void addItem(Pessoa pessoa){
        this.listaPessoa.add(pessoa);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.adapter_list_pessoa,parent,false);

        TextView txtNome = (TextView) view.findViewById(R.id.txtNome);
        TextView txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        TextView txtEndereco = (TextView) view.findViewById(R.id.txtEndereco);
        TextView txtSexo = (TextView) view.findViewById(R.id.txtSexo);
        ImageButton btnDelete = (ImageButton) view.findViewById(R.id.btnDelete);

        Pessoa pessoa = listaPessoa.get(position);

        txtNome.setText("Nome: "+pessoa.getNome());
        txtEmail.setText("E-mail: "+pessoa.getEmail());
        txtEndereco.setText("Endereço: "+pessoa.getEndereco());
        txtSexo.setText("Sexo: "+((pessoa.getSexo() == "F")?"Feminino":"Masculino"));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               context.excluirPessoa(v);
            }
        });

        return view;
    }
}
