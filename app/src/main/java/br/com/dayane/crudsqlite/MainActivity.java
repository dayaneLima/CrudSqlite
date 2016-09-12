package br.com.dayane.crudsqlite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import br.com.dayane.crudsqlite.adapter.AdapterListPessoa;
import br.com.dayane.crudsqlite.database.PessoaDAO;
import br.com.dayane.crudsqlite.helper.Validate;
import br.com.dayane.crudsqlite.model.Pessoa;

public class MainActivity extends AppCompatActivity {

    EditText edtNome;
    EditText edtEmail;
    EditText edtEndereco;
    RadioGroup rbSexo;
    PessoaDAO pessoaDAO ;
    AdapterListPessoa adapter;
    ListView listViewPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pessoaDAO = new PessoaDAO(this);
        listViewPessoa = (ListView) findViewById(R.id.listViewPessoa);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogCadastroPessoa();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        populaListaPessoa();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_nome_aluno) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Aluna")
                    .setMessage("Dayane Lima de Castro Carmonio")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void alertDialogCadastroPessoa(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_cadastro_pessoa,null);

        edtNome = (EditText) view.findViewById(R.id.edtNome);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtEndereco = (EditText) view.findViewById(R.id.edtEndereco);
        rbSexo = (RadioGroup) view.findViewById(R.id.rbSexo);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Cadastro de Pessoa");
        builder.setCancelable(false);
        builder.setView(view);
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        final AlertDialog alertDialog = builder.create();

        alertDialog.show();

        Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()){
                    salvarPessoa();
                    alertDialog.dismiss();
                }
            }
        });
    }

    private boolean valid(){
        boolean valid = true;
        if(!Validate.required(edtNome)) valid = false;
        if(!Validate.required(edtEmail)) valid = false;
        if(!Validate.required(edtEndereco)) valid = false;
        return valid;
    }

    private void salvarPessoa(){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(edtNome.getText().toString());
        pessoa.setEmail(edtEmail.getText().toString());
        pessoa.setEndereco(edtEndereco.getText().toString());

        String sexo = "";
        int idMarcado = rbSexo.getCheckedRadioButtonId();
        if(idMarcado == R.id.rbMasculino){
            sexo = "M";
        }else{
            sexo = "F";
        }
        pessoa.setSexo(sexo);


        long itemInserido = pessoaDAO.insert(pessoa);

        if(itemInserido > -1) {
            apagaCamposCadastroPessoa();
            pessoa.setId(itemInserido);
            adapter.addItem(pessoa);
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Pessoa gravada com sucesso", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Erro ao inserir pessoa!", Toast.LENGTH_SHORT).show();
        }

    }

    private void apagaCamposCadastroPessoa(){
        edtNome.setText("");
        edtEmail.setText("");
        edtEndereco.setText("");
    }

    private void populaListaPessoa(){
        List<Pessoa> listaPessoas = this.pessoaDAO.getAll();
        adapter = new AdapterListPessoa(this, listaPessoas);
        listViewPessoa.setAdapter(adapter);
    }

    public void excluirPessoa(View v){
        final int position = listViewPessoa.getPositionForView((View)v.getParent());
        final Pessoa pessoa = (Pessoa) listViewPessoa.getItemAtPosition(position);

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Confirmação de Exclusão");
        dialog.setMessage("Tem certeza que deseja exlcuir a pessoa "+pessoa.getNome()+"?");
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pessoaDAO.delete(pessoa);
                adapter.removeItem(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,pessoa.getNome()+" excluída com sucesso!",Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog1 = dialog.create();
        dialog1.show();
    }

}
