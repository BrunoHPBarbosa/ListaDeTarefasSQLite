package com.example.listadetarefasj.database

import com.example.listadetarefasj.model.Tarefa

interface ITarefaDao {

    fun salvar(tarefa: Tarefa): Boolean
    fun atualizar(tarefa: Tarefa): Boolean
    fun remover(idtarefa:Int): Boolean
    fun listar(): List<Tarefa>
}