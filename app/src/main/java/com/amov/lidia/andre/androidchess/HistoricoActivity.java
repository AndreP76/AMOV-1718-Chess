package com.amov.lidia.andre.androidchess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoricoActivity extends Activity {

    ListView lvHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        lvHistorico = (ListView) findViewById(R.id.lvHistorico);
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View layout = getLayoutInflater().inflate(R.layout.item_historico, null);
            Historico hist = historico.get(position);
            ((TextView) layout.findViewById(R.id.list_title)).setText(hist.getTitle());
            return layout;
        }
    }
}
