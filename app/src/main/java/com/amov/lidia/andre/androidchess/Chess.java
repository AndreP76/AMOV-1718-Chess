package com.amov.lidia.andre.androidchess;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import com.amov.lidia.andre.androidchess.ChessCore.Game;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.GamePiece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by andre on 12/15/17.
 */

//TODO : torres so andam para a direita e para baixo
//TODO : as peças nao podem atacar, embora os ataques estejam prontos no nucleo
//TODO : os bispos nao andam numa das direçoes
//DONE : por alguma razao o metodo move dos peoes nao está a ser chamado
//declarado o metodo move como abstract e implementado
public class Chess extends Application {
    public static final String FILE_NAME = "history.dat";
    public static final String APP_TAG = "AMovChessGame: ";

    public static Resources resources; //para facilitar acesso às strings fora de um context

    private static Game currentGame = null;
    private static GamePiece currentSelectedPiece = null;
    private static ArrayList<Historico> historicos;
    private static Chess chess;

    public Chess() {
        chess = this;
        historicos = new ArrayList<>();
    }

    public static GamePiece getCurrentSelectedPiece() {
        return currentGame.getCurrentSelectedPiece();
    }

    public static void setCurrentSelectedPiece(GamePiece currentSelectedPiece) {
        currentGame.setCurrentSelectedPiece(currentSelectedPiece);
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        Chess.currentGame = currentGame;
    }

    //TODO - Adicionar objeto Historico ao ArrayList no final de um jogo
    public static void addHistorico(Historico h) {
        historicos.add(h);
        gravarHistoricos();
    }

    public static void removeHistorico(int index) {
        if (index >= 0 && index < historicos.size())
            historicos.remove(index);
        gravarHistoricos();
    }

    public static void gravarHistoricos() {
        try {
            FileOutputStream fos = chess.openFileOutput(FILE_NAME, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(historicos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e(APP_TAG, "Ficheiro não encontrado (escrita): " + e.getMessage());
        } catch (IOException e) {
            Log.e(APP_TAG, "Erro ao gravar histórico: " + e.getMessage());
        }
    }

    public static void lerHistoricos() {
        historicos = null;
        try {
            FileInputStream fis = chess.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Historico> hist = (ArrayList<Historico>) ois.readObject();
            historicos = hist;
            fis.close();
        } catch (FileNotFoundException e) {
            Log.e(APP_TAG, "Ficheiro não encontrado para leitura: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(APP_TAG, "Erro ao ler histórico (readObject): " + e.getMessage());
        } catch (IOException e) {
            Log.e(APP_TAG, "Erro ao ler histórico: " + e.getMessage());
        }

        if (historicos == null)
            historicos = new ArrayList<>();
    }

    public static ArrayList<Historico> getHistoricos() {
        return historicos;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        resources = getResources();
        lerHistoricos();
    }
}
