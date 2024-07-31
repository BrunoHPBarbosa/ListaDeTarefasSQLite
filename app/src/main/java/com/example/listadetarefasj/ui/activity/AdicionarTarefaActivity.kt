package com.example.listadetarefasj.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listadetarefasj.database.TarefaDao
import com.example.listadetarefasj.databinding.ActivityAdicionarTarefaBinding
import com.example.listadetarefasj.model.Tarefa

class AdicionarTarefaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAdicionarTarefaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var tarefa: Tarefa? = null
        val bundle = intent.extras
        if (bundle != null) {
            tarefa = bundle.getSerializable("tarefa") as Tarefa
            binding.editTarefa.setText(tarefa.descricao)
        }

        if (binding.editTarefa.text.isNotEmpty()) {

            if (tarefa != null) {
                editar(tarefa)

            } else {
                salvar()
            }

        } else {
            Toast.makeText(
                this,
                "preencha uma tarefa",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun editar(tarefa: Tarefa) {

        val descricao = binding.editTarefa.text.toString()
        val tarefaAtualizar = Tarefa(
            tarefa.idTarefa, descricao, "default"
        )
        val tarefaDao = TarefaDao(this)

        if (tarefaDao.atualizar(tarefaAtualizar)) {
            Toast.makeText(
                this,
                "tarefa atualizada com sucesso",
                Toast.LENGTH_SHORT
            )
                .show()
            finish()
        }
    }

    private fun salvar() {
        val descricao = binding.editTarefa.text.toString()
        val tarefa = Tarefa(
            -1, descricao, "default"

        )
        val tarefaDAO = TarefaDao(this)
        if (tarefaDAO.salvar(tarefa)) {
            Toast.makeText(
                this,
                "tarefa cadastrada com sucesso",
                Toast.LENGTH_SHORT
            )
                .show()
            finish()
        }
    }
}


