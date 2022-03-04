package com.example.demineur;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;


public class GameWindow extends AppCompatActivity {

    private TableLayout table = null;
    private Switch flagSwitch = null;

    private Cellule[][] plateau;
    private int taille;
    private int bombe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_window);

        flagSwitch = (Switch)findViewById(R.id.Drapeau);
        table = (TableLayout) findViewById(R.id.table_jeu);

            Bundle extras = getIntent().getExtras();
            taille = 5;
            bombe = 1;

            fillTableau();
            addBombes();
    }

    /**
     * Remplit le plateau de jeu à partir de rien
     */
    private void fillTableau(){
        plateau = new Cellule[taille][taille];
        // On remplit le plateau
        for (int y = 0; y < taille; y++) {
            TableRow r = new TableRow(this);
            table.addView(r);
            for (int x = 0; x < taille; x++) {
                Cellule c = new Cellule(this,x,y);
                c.setOnClickListener(onClickCellule);
                r.addView(c);
                plateau[x][y] = c;
            }
        }
    }

    /**
     * Clic sur une cellule
     */
    private View.OnClickListener onClickCellule = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Cellule c = (Cellule)v;
                /*On aurait peut être pu faire autrement, plus "propre", mais je n'ai pas vraiment
                trouvé de méthode alternative*/
                //Try catch au cas où v ne serait pas une cellule

                if (flagSwitch.isChecked()) {
                    /*pas besoin de vérifier que la case n'est pas déjà révélée
                     Parce que le listener ne serait alors plus attribué à la cellule
                      */
                    c.poseDrapeau();
                    return;
                }

                if(c.getText() == c.getCharFlag())
                    return;
                //Impossible de révéler une case avec un drapeau

                c.setOnClickListener(null);
                ArrayList<Cellule> voisins = getVoisins(c.getCaseX(), c.getCaseY());
                c.addVoisins(voisins);

            }catch(Exception e){
                e.printStackTrace();
            }

        }

    };

    /**
     * Récupère la liste des voisins d'une cellule
     * @param x coordonnée x de la cellule
     * @param y coordonnée y de la cellule
     * @return l'arraylist des voisins
     */
    private ArrayList<Cellule> getVoisins(int x, int y){
        ArrayList<Cellule> voisins = new ArrayList<Cellule>();

        for(int i = -1; i<2; i++){
            for(int j = -1; j<2; j++){
                if(x+i>=0 && x+i<taille && y+j>=0 && y+j<taille && (i!=0 || j!=0)){
                    voisins.add(plateau[x+i][y+j]);
                    if(plateau[x+i][y+j].isBombe())
                        plateau[x][y].incrBombesVoisines();
                }
            }
        }

        return voisins;
    }

    /**
     * Place les bombes sur le plateau
     */
    private void addBombes(){
        int cptBombe = bombe;
        while (cptBombe>0){
            Random gen = new Random();
            int x = gen.nextInt(taille);
            int y = gen.nextInt(taille);

            if(!plateau[x][y].isBombe()){
                plateau[x][y].devenirBombe();
                cptBombe--;
            }
        }
    }


}

