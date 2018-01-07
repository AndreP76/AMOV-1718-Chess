package com.amov.lidia.andre.androidchess;

import com.amov.lidia.andre.androidchess.ChessCore.Utils.GameMode;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lídia on 19/12/2017.
 */

/**
 * Deve ser mantido um histórico local dos últimos jogos realizados, respetivos vencedores,
 * modos de jogo e sucessão de jogadas realizadas.
 */

//DONE - chamar construtor de Historico no início de cada jogo
//TODO - criar novo objeto Jogada e adicionar à lista sempre que é efetuado um movimento
// ou se muda de modo de jogo
//DONE - chamar setDate, setVencedor e setTitle quando o jogo termina
//DONE - gravar histórico sempre que um jogo termina
public class Historico implements Serializable {
    private ArrayList<Jogada> jogadas;
    private Date date;
    private String title, vencedor;

    public Historico() {
        jogadas = new ArrayList<>();
        date = new Date();
        title = "(sem título)";
        vencedor = "(sem vencedor)";
    }

    public void addJogada(String player1, String player2, String gameMode, String descricao) {
        jogadas.add(new Jogada(player1, player2, gameMode, descricao));
    }

    public void setDate() {
        date = new Date();
    }

    public void setTitle() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy (HH:mm:ss)");
        title = df.format(date);
    }

    public String getTitle() {
        return title;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }

    public List<Jogada> getJogadas(){
        return jogadas;
    }
}
