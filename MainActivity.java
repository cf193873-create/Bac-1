package com.example.baccarat;

import android.app.*;
import android.os.Bundle;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    ArrayList<Character> h = new ArrayList<>();
    HistoryView history;
    TextView total, stats, sequence;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        history=findViewById(R.id.history); total=findViewById(R.id.total);
        stats=findViewById(R.id.stats); sequence=findViewById(R.id.sequence);
        findViewById(R.id.player).setOnClickListener(v -> add('P'));
        findViewById(R.id.tie).setOnClickListener(v -> add('E'));
        findViewById(R.id.banker).setOnClickListener(v -> add('B'));
        findViewById(R.id.undo).setOnClickListener(v -> { if(!h.isEmpty()){h.remove(h.size()-1); update();}});
        findViewById(R.id.clear).setOnClickListener(v -> {
            if(h.isEmpty()) return;
            new AlertDialog.Builder(this).setTitle("Confirmar")
                .setMessage("Apagar todo o histórico?").setNegativeButton("Não",null)
                .setPositiveButton("Sim",(d,w)->{h.clear();update();}).show();
        });
        update();
    }
    void add(char c){h.add(c);update();}
    int count(char c){int n=0; for(char x:h)if(x==c)n++; return n;}
    double pct(int n){return h.isEmpty()?0:n*100.0/h.size();}
    int run(char c){int n=0;for(int i=h.size()-1;i>=0&&h.get(i)==c;i--)n++;return n;}
    int maxRun(char c){int a=0,m=0;for(char x:h){if(x==c){a++;m=Math.max(m,a);}else a=0;}return m;}
    String name(char c){return c=='P'?"JOGADOR":c=='B'?"BANCA":"EMPATE";}
    void update(){
        int p=count('P'),b=count('B'),e=count('E');
        total.setText("Mãos: "+h.size());
        stats.setText(String.format(Locale.US,"JOGADOR: %d (%.2f%%)\nBANCA: %d (%.2f%%)\nEMPATE: %d (%.2f%%)\n\nMaior sequência — J: %d | B: %d | E: %d",
            p,pct(p),b,pct(b),e,pct(e),maxRun('P'),maxRun('B'),maxRun('E')));
        if(h.isEmpty()) sequence.setText("Sequência atual: -");
        else {char x=h.get(h.size()-1); sequence.setText("Sequência atual: "+name(x)+" × "+run(x));}
        history.setResults(h); history.invalidate();
    }
}
