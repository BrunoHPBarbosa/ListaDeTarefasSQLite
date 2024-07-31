package com.example.listadetarefasj.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION
import android.util.Log

class DataBaseHelper(context: Context) : SQLiteOpenHelper(
    context, NOME_DATA_BASE, null, VERSION
) {

    companion object {
        const val NOME_DATA_BASE = "ListaTarefas.db"
        const val VERSION = 1
        const val NOME_TABELA_TAREFAS = "tarefas"
        const val COLUNA_ID_TAREFA = "id_tarefa"
        const val COLUNA_DATA_CADASTRO = "data_cadastro"
        const val COLUNA_DESCRICAO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sql = "CREATE TABLE IF NOT EXISTS $NOME_TABELA_TAREFAS(" +
                "$COLUNA_ID_TAREFA INTEGER not NULL PRIMARY KEY AUTOINCREMENT," +
                "$COLUNA_DESCRICAO VARCHAR(70)," +
                "$COLUNA_DATA_CADASTRO DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"

        try {
            db?.execSQL(sql)
            Log.i("info_db", "Sucessso ao criar tabela")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao criar tabela")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}