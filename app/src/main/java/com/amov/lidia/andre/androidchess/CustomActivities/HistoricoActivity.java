package com.amov.lidia.andre.androidchess.CustomActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.Historico;
import com.amov.lidia.andre.androidchess.R;

import java.util.ArrayList;

public class HistoricoActivity extends Activity {

    ListView lvHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        lvHistorico = findViewById(R.id.lvHistorico);
        HistAdapter adapter = new HistAdapter(Chess.getHistoricos());
        lvHistorico.setAdapter(adapter);
        lvHistorico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoricoActivity.this,VerHistoricoJogoActivity.class);
                intent.putExtra("historico", Chess.getHistoricos().get(position));
                startActivity(intent);
            }
        });

    }

    class HistAdapter extends BaseAdapter {

        ArrayList<Historico> historico;

        public HistAdapter(ArrayList<Historico> historico) {
            this.historico = historico;
        }

        @Override
        public int getCount() {
            return historico.size();
        }

        @Override
        public Object getItem(int position) {
            return historico.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View layout = getLayoutInflater().inflate(R.layout.item_historico, null);
            Historico hist = historico.get(position);
            ((TextView) layout.findViewById(R.id.list_title)).setText(hist.getTitle());

            //Código para fazer o botão remover um jogo do histórico
            //TODO - Confirmar que não dá asneira!!!
            Button removeButton = layout.findViewById(R.id.list_delete);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chess.removeHistorico(position);
                }
            });
            return layout;
        }
    }
}
