package com.example.listadetarefasj.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadetarefasj.R
import com.example.listadetarefasj.database.TarefaDao
import com.example.listadetarefasj.databinding.ActivityMainBinding
import com.example.listadetarefasj.model.Tarefa
import com.example.listadetarefasj.ui.adapter.TarefaAdapter

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.fabButtom.setOnClickListener {
            val intent = Intent(this, AdicionarTarefaActivity::class.java)
            startActivity(intent)
        }
        tarefaAdapter = TarefaAdapter(
            { id -> confirmarExclusao(id) },
            {tarefa -> editar(tarefa)}
        )
        binding.rvTarefas.adapter = tarefaAdapter

        binding.rvTarefas.layoutManager = LinearLayoutManager(this)

    }

    private fun editar(tarefa: Tarefa) {
        val intent = Intent(this,AdicionarTarefaActivity::class.java)
         intent.putExtra("tarefa",tarefa)

        startActivity(intent)

    }

    private fun confirmarExclusao(id:Int) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Confirmar exclusao")
        alertBuilder.setMessage("Deseja realmente excluir a tarefa?")

        alertBuilder.setPositiveButton("sim"){_,_ ->

        }
        alertBuilder.setNegativeButton("Nao"){_,_ -> }
        alertBuilder.create().show()

        val tarefaDao = TarefaDao(this)
       if( tarefaDao.remover(id)){
           atualizarListaTarefas()
           Toast.makeText(this, "sucesso ao remover tarefa", Toast.LENGTH_SHORT).show()

       }else{
           Toast.makeText(this, "Erro ao remover tarefa", Toast.LENGTH_SHORT).show()
       }

    }

    private fun atualizarListaTarefas() {
        val tarefaDao = TarefaDao(this)
        listaTarefas = tarefaDao.listar()
        tarefaAdapter?.adicionarLista(listaTarefas)
    }

    override fun onStart() {
        super.onStart()
        atualizarListaTarefas()


    }
}