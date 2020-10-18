package com.davi.listadetarefas.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.davi.listadetarefas.R;
import com.davi.listadetarefas.helper.TarefaDAO;
import com.davi.listadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {
    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        editTarefa = findViewById(R.id.textTarefa);

        //Recuperar tarefa, caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if(tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSalvar:
                ///Executar a ação para o Item Salvar
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                if(tarefaAtual != null){//Edição
                    String nomeTarefa = editTarefa.getText().toString();
                    if(!nomeTarefa.isEmpty()){

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //Atualizar no banco de dados
                        if(tarefaDAO.atualizar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(),"Sucesso ao salvar tarefa!",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getApplicationContext(),"Erro ao salvar tarefa!",Toast.LENGTH_SHORT).show();
                        }
                    }



                }else{//Salvar

                    String nomeTarefa = editTarefa.getText().toString();
                    if(!nomeTarefa.isEmpty()){

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);

                        if(tarefaDAO.salvar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(),"Sucesso ao salvar tarefa!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Erro ao salvar tarefa!",Toast.LENGTH_SHORT).show();
                        }


                    }
                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
