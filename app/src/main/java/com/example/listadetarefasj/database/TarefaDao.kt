package com.example.listadetarefasj.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.listadetarefasj.model.Tarefa

class TarefaDao(context: Context) : ITarefaDao {

    private val escrita = DataBaseHelper(context).writableDatabase
    private val leitura = DataBaseHelper(context).readableDatabase


    override fun salvar(tarefa: Tarefa): Boolean {
        val conteudos = ContentValues()
        conteudos.put(DataBaseHelper.COLUNA_DESCRICAO, tarefa.descricao)

        try {
            escrita.insert(
                DataBaseHelper.NOME_TABELA_TAREFAS,
                null,
                conteudos
            )
            Log.i("info_db", "Sucessso ao salvar tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao salvar tarefa")
            return false
        }
        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {

        val args = arrayOf(tarefa.idTarefa.toString())
        val conteudo = ContentValues()
        conteudo.put(DataBaseHelper.COLUNA_DESCRICAO,tarefa.descricao)
        try {
            escrita.update(
                DataBaseHelper.NOME_TABELA_TAREFAS,
                conteudo,
                "${DataBaseHelper.COLUNA_ID_TAREFA} = ?",
                args
            )
            Log.i("info_db", "Sucessso ao atualizar tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao atualizar tarefa")
            return false
        }
        return true

    }

    override fun remover(idTarefa: Int): Boolean {

        val args = arrayOf(idTarefa.toString())
        try {
            escrita.delete(
                DataBaseHelper.NOME_TABELA_TAREFAS, "${DataBaseHelper.COLUNA_ID_TAREFA} = ?",
                args
            )
            Log.i("info_db", "Sucessso ao remover tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao remover tarefa")
            return false
        }
        return true
    }

    override fun listar(): List<Tarefa> {


        val listaTarefas = mutableListOf<Tarefa>()

        val sql = "SELECT ${DataBaseHelper.COLUNA_ID_TAREFA}," +
                "${DataBaseHelper.COLUNA_DESCRICAO}," +
                "strftime('%d/%m/%Y %H: %M', ${DataBaseHelper.COLUNA_DATA_CADASTRO})  ${DataBaseHelper.COLUNA_DATA_CADASTRO}" +
                " FROM ${DataBaseHelper.NOME_TABELA_TAREFAS}"

        val cursor = leitura.rawQuery(sql, null)

        val indiceId = cursor.getColumnIndex(DataBaseHelper.COLUNA_ID_TAREFA)
        val indiceDescricao = cursor.getColumnIndex(DataBaseHelper.COLUNA_DESCRICAO)
        val indiceDataCadastro = cursor.getColumnIndex(DataBaseHelper.COLUNA_DATA_CADASTRO)

        while (cursor.moveToNext()) {

            val idTarefa = cursor.getInt(indiceId)
            val descricao = cursor.getString(indiceDescricao)
            val data = cursor.getString(indiceDataCadastro)

            listaTarefas.add(
                Tarefa(idTarefa, descricao, data)
            )

        }
        return listaTarefas
    }
}