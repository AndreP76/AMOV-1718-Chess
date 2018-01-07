package com.amov.lidia.andre.androidchess.CustomActivities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amov.lidia.andre.androidchess.Historico;
import com.amov.lidia.andre.androidchess.Jogada;
import com.amov.lidia.andre.androidchess.R;

import java.util.List;
import java.util.Locale;

public class VerHistoricoJogoActivity extends Activity {

    ListView lvJogadas;
    TextView tvVerHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_historico_jogo);

        Historico hist = (Historico) getIntent().getSerializableExtra("historico");
        List<Jogada> jogadas;

        if (hist != null) {
            tvVerHistorico = findViewById(R.id.tvVerHistorico);
            tvVerHistorico.setText(hist.getTitle());
            jogadas = hist.getJogadas();
            lvJogadas = findViewById(R.id.lvListaJogadas);
            JogadasAdapter jogAdapter = new JogadasAdapter(jogadas);
            lvJogadas.setAdapter(jogAdapter);
        }
    }

    class JogadasAdapter extends BaseAdapter {
        List<Jogada> lista;

        public JogadasAdapter(List<Jogada> lista) {
            this.lista = lista;
        }

        @Override
        public int getCount() {
            return lista.size();
        }

        @Override
        public Object getItem(int position) {
            return lista.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layout = getLayoutInflater().inflate(R.layout.item_jogada, null);
            Jogada jogada = lista.get(position);
            String str;
            str = String.format(Locale.getDefault(), "%s %s %s",
                    jogada.getPlayer1(),
                    getResources().getString(R.string.versus),
                    jogada.getPlayer2());
            ((TextView) layout.findViewById(R.id.lvPlayers)).setText(str);
            ((TextView) layout.findViewById(R.id.lvGameMode)).setText(jogada.getGameMode());
            ((TextView) layout.findViewById(R.id.lvDescription)).setText(jogada.getDescricao());
            return layout;
        }
    }
}
