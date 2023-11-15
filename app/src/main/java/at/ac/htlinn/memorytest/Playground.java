package at.ac.htlinn.memorytest;

import android.service.quickaccesswallet.GetWalletCardsCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Playground {
    private Card[][] cards;
    private int whoseOnTurn;
    private int[] score;

    public Playground (int x, int y) {
        this.cards = new Card[x][y];
        this.score = new int[2];
        this.whoseOnTurn = 0;
        init();
    }
    public void init(){
        ArrayList<Integer> order = new ArrayList<Integer>();
        //1 add the image ids to the list
        for(int id = 0; id < cards.length * cards[0].length; id++){
            order.add(id);
        }
        //2 shuffle cards for random positions
        Collections.shuffle(order);

        //3 bilder(values) setzen (durch 2 da jedes Bild 2mal vorkommt)
        for(int value = 0; value < order.size() / 2; value ++){
            //4 1Bild auf 2 Karten setzen
            for(int card = 0; card < 2; card++){
                //5 die id wieder zusammensetzen aus den parametern
                int id = order.get(2 * value + card);
                //6 positionselement erstellen
                Position pos = new Position(id, cards.length, true);
                //7 neues kartenobjekt mit pos erstellen
                cards[pos.x][pos.y] = new Card(value);
            }
        }
    }
    public boolean play(Position p1,Position p2){
        if(isPair(p1, p2)){
            score[whoseOnTurn]++;
        }else{
            cards[p1.x][p1.y].setVisible(false);
            cards[p2.x][p2.y].setVisible(false);
        }
        whoseOnTurn = (whoseOnTurn + 1) % 2;
        return false;
    }
    public boolean finished(){
        for(int i = 0; i < cards.length; i++){
            for(int j = 0; j < cards[i].length; j++){
                if(!(cards[i][j].isVisible())){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public boolean isPair(Position p1, Position p2){
        return cards[p1.x][p1.y].equals(cards[p2.x][p2.y]);
    }
    public Card getCard(Position p1){
        return  cards[p1.x][p1.y];
    }
    private int getNrPairs(){
        int anz = (cards.length * cards[0].length) /2;
        return anz;
    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards[i].length; j++) {
                s += cards[i][j];
            }
        }
        return s;
    }
}
