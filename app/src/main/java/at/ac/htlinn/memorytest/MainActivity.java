package at.ac.htlinn.memorytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int[] pics;
    private Playground field ;
    private ImageButton[][] buttons;
    private Position previousPosition = null;

    private final int SIZE_X = 4;
    private final int SIZE_Y = 4;

    Timer timer = null; //Use the timer to schedule actions
    private int secs = 0; //How many seconds elapes since start

    class SecondsTask extends TimerTask
    {
        @Override
        public void run() {
            runOnUiThread(() -> {
                TextView secsView = findViewById(R.id.timer);
                secsView.setText(++secs +" sec");
            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons = new ImageButton[SIZE_X][SIZE_Y];
        generateGrid(SIZE_X,SIZE_Y);
        pics = getPicsArray();
        field = new Playground(SIZE_X, SIZE_Y);
        field.init();
        timer = new Timer();
        timer.schedule(new SecondsTask(), 0, 1000);
    }

    private void closeCards(Position pos1, Position pos2)
    {
        class CloseTask extends TimerTask
        {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    buttons[pos1.x][pos1.y].setImageResource(R.drawable.back);
                    field.getCard(pos1).setVisible(false);
                    buttons[pos2.x][pos2.y].setImageResource(R.drawable.back);
                    field.getCard(pos2).setVisible(false);
                });
            }
        }
        Log.d("MEMORYLOG", "Das ist die MEldung");
        timer.schedule(new CloseTask(),1000); //Clode cards in a second
    }

    private void generateGrid(int nrCols, int nrRows){
        TableLayout playField = findViewById(R.id.playfield);
        for(int i = 0; i < nrRows; i++){
            TableRow tr1 = generateAndAddRows(i, nrCols);
            playField.addView(tr1);
        }

    }
        private TableRow generateAndAddRows(int row, int nrCols){
        TableRow.LayoutParams tableRowParams=
                new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(tableRowParams);
        for(int i = 0; i < nrCols; i++){
            Position p1 = new Position(i, row);
            tr.addView(generateButton(p1));
        }
        return tr;
    }
    private ImageButton generateButton(Position pos){

        ImageButton b = new ImageButton(this);
        b.setImageResource(R.drawable.back);
        b.setTag(pos); //We store the coordinates for each card
        b.setOnClickListener(this);
        buttons[pos.x][pos.y] = b;
        return b;
    }
    public void onClick(View view){
        Position p = (Position) view.getTag(); //This card was clicked
        Card ok = field.getCard(p);

        if(!ok.isVisible()) { //Filter clicks on already opened cards
            ok.setVisible(true);
            ((ImageButton) view).setImageResource(
                    pics[field.getCard(p).getValue()]);
            Log.d("MEMORY_TAG", previousPosition + "");
            if (previousPosition != null) { //its the second card which was opened
                if (!(field.isPair(p, previousPosition))) {
                    Snackbar.make(view,R.string.no_pair,5000).show();
                    closeCards(p, previousPosition);
                } else
                {

                    if (field.finished())
                    {
                        Snackbar.make(view,R.string.finished,5000).show();
                    } else
                    {
                        Snackbar.make(view,R.string.pair,5000).show();
                    }
                }
                previousPosition = null;
            } else { //first card, so store position for later usage
                previousPosition = p;
                Snackbar.make(view,R.string.open_second,5000).show();
            }
        }
    }

    public  int[] getPicsArray() {
        Resources resources = getResources();
        int[] c = new int[20];
        for (int i  = 0; i< c.length;i++)
        {
            // e.g R.drawable.i000;
            String id = String.format("i%03d", i);
            c[i] = resources.getIdentifier(id, "drawable", getPackageName() );
        }
        return c;
    }

/*
        public static int[] getPicsArray() {
        int[] c = new int[20];

        c[0] = R.drawable.i000;
        c[1] = R.drawable.i001;
        c[2] = R.drawable.i002;
        c[3] = R.drawable.i003;
        c[4] = R.drawable.i004;
        c[5] = R.drawable.i005;
        c[6] = R.drawable.i006;
        c[7] = R.drawable.i007;
        c[8] = R.drawable.i008;
        c[9] = R.drawable.i009;
        c[10] = R.drawable.i010;
        c[11] = R.drawable.i011;
        c[12] = R.drawable.i012;
        c[13] = R.drawable.i013;
        c[14] = R.drawable.i014;
        c[15] = R.drawable.i015;
        c[16] = R.drawable.i016;
        c[17] = R.drawable.i017;
        c[18] = R.drawable.i018;
        c[19] = R.drawable.i019;
        return c;
    }
    */

}