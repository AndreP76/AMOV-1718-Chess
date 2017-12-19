package com.amov.lidia.andre.androidchess;

import android.app.Application;
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

public class Chess extends Application {
    public static final String FILE_NAME = "history.dat";
    public static final String APP_TAG = "AMovChessGame: ";
    private static Game currentGame = null;
    private static GamePiece currentSelectedPiece = null;
    private static ArrayList<Historico> historicos;
    private static Chess chess;

    public Chess() {
        chess = this;
        historicos = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        lerHistoricos();
    }

    public static GamePiece getCurrentSelectedPiece() {
        return currentSelectedPiece;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        Chess.currentGame = currentGame;
    }

    //TODO - Adicionar objeto Historico ao ArrayList no final de um jogo
    public static void addHistorico(Historico h){
        historicos.add(h);
        gravarHistoricos();
    }

    public static void gravarHistoricos(){
        try {
            FileOutputStream fos = chess.openFileOutput(FILE_NAME, MODE_APPEND);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(historicos);
            fos.close();
        }catch (FileNotFoundException e){
            Log.e(APP_TAG, "Ficheiro não encontrado (escrita).", e);
        }catch (IOException e){
            Log.e(APP_TAG, "Erro ao gravar histórico.", e);
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
            Log.e(APP_TAG, "Ficheiro não encontrado para leitura.", e);
        } catch (ClassNotFoundException e) {
            Log.e(APP_TAG, "Erro ao ler histórico (readObject).", e);
        } catch (IOException e) {
            Log.e(APP_TAG, "Erro ao ler histórico.", e);
        }

        if(historicos == null)
            historicos = new ArrayList<>();
    }

    public static ArrayList<Historico> getHistoricos(){
        return historicos;
    }
}
