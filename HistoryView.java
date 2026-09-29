package com.example.baccarat;

import android.content.*;
import android.graphics.*;
import android.view.*;
import java.util.*;

public class HistoryView extends View {
    ArrayList<Character> r=new ArrayList<>();
    Paint p=new Paint(1);
    public HistoryView(Context c){super(c);p.setTypeface(Typeface.DEFAULT_BOLD);}
    void setResults(ArrayList<Character> x){r=x;}
    @Override protected void onDraw(Canvas c){
        super.onDraw(c); c.drawColor(Color.WHITE);
        int size=44,gap=10,cols=Math.max(1,(getWidth()-20)/(size+gap));
        for(int i=0;i<r.size();i++){
            int col=i%cols,row=i/cols,x=10+col*(size+gap),y=10+row*(size+gap);
            int color=r.get(i)=='P'?Color.rgb(35,100,210):r.get(i)=='B'?Color.rgb(200,45,45):Color.rgb(190,145,30);
            p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(4);p.setColor(color);
            c.drawCircle(x+size/2f,y+size/2f,size/2f-3,p);
            p.setStyle(Paint.Style.FILL);p.setTextSize(16);p.setColor(color);
            String s=String.valueOf(r.get(i));float tw=p.measureText(s);
            c.drawText(s,x+size/2f-tw/2,y+size/2f+6,p);
        }
        if(r.isEmpty()){p.setColor(Color.GRAY);p.setTextSize(16);c.drawText("Regista o primeiro resultado abaixo.",16,32,p);}
    }
}
