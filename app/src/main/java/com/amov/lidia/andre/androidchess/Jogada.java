package com.amov.lidia.andre.androidchess;

import java.io.Serializable;

/**
 * Created by Lídia on 20/12/2017.
 */

public class Jogada implements Serializable {
    String player1, player2, gameMode, descricao;

    public Jogada(String player1, String player2, String gameMode, String descricao) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameMode = gameMode;
        this.descricao = descricao;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getDescricao() {
        return descricao;
    }
}
