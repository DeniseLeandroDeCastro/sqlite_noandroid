package br.edu.ifrn.bancodedadossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        ** Criando um banco de dados SQLite.
        *  O comando openOrCreateDatabase é para abrir um banco de dados se já existir e
        *  se não existir criar um. Em name, deve ser colocado o nome do banco de dados
        *  que deseja criar ou abrir. MODE_PRIVATE, significa que apenas o app terá
        *  acesso ao banco de dados criado.
         */
        try {
            //Criar banco de dados
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);
            /**
             * Cria uma tabela caso ela ainda não exista
             * tabela pessoas, com colunas: nome e idade
             */
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, idade INT(3))");
            //bancoDados.execSQL("DROP TABLE pessoas"); //Apaga a tabela

            //Inserindo dados
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES('Mariana', 18)");
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES('Maria', 35)");

            //Atualizando dados
            //bancoDados.execSQL("UPDATE pessoas SET idade = 19 WHERE nome = 'Mariana'");

            //Deletando registros
            bancoDados.execSQL(" DELETE FROM pessoas ");

            /**
             * Recuperando pessoas
             * O comando rawQuery é utilizado para recuperar registros
             */
            /**
            String consulta =
                    "SELECT nome, idade " + "FROM pessoas " +
                    "WHERE nome = 'Jamilton' AND idade = 30";

             String consulta = "SELECT nome, idade FROM pessoas WHERE idade >= 35 OR idade = 18";
             String consulta = "SELECT nome, idade FROM pessoas WHERE idade IN(18, 35)";
             */

            String consulta = " SELECT id, nome, idade FROM pessoas " +
                    " WHERE 1=1 ";

            Cursor cursor = bancoDados.rawQuery(consulta, null);

            //Índices da tabela
            int indiceId = cursor.getColumnIndex("id");
            int indiceNome = cursor.getColumnIndex("nome");
            int indiceIdade = cursor.getColumnIndex("idade");

            cursor.moveToFirst(); //Diz ao cursor para voltar ao início da lista
            while(cursor != null) {
                String id = cursor.getString(indiceId);
                String nome = cursor.getString(indiceNome);
                String idade = cursor.getString(indiceIdade);

                Log.i("RESULTADO - id ", id +  " - nome: " + nome + " - idade: " + idade);
                cursor.moveToNext();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}