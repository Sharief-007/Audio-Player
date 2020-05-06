import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JSlider;



class Time_Calculator
{
    static int seconds=1;
    Timer timer;

    

    public float MicroSeconds_2_seconds(long time)
    {
        float f= (float) ((time) / 1e+6);
        return f;
    }

    public float seconds_2_minutes(float time)
    {
        return (time)/60;
    }

    public int minutes_2_seconds(float time)
    {
        return (int) (time * 60);
    }

    public void StartTimer(JSlider slider,JLabel l,int time)
    {
        timer = new Timer("song time");
        timer.scheduleAtFixedRate(new TimerTask(){
        
            @Override
            public void run() {
                if(seconds<=time)
                {
                    int value =seconds;
                    showTime(value,l);
                    slider.setValue(value);
                    seconds++;
                }
                else
                {
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }
    public void showTime(int seconds,JLabel l)
    {
        if(seconds<59)
        {
            if(seconds<=9)
            {
                l.setText("00 : 0"+seconds);
            }
            else
            {
                l.setText("00 : "+seconds);
            }
        }
        else
        {
            if(seconds/60<=9)
            {
                if(seconds%60<=9)
                {
                    l.setText("0"+seconds/60+" : 0"+seconds%60);
                }
                else
                {
                    l.setText("0"+seconds/60+" : "+seconds%60);
                }
            }
            else
            {
                l.setText(seconds/60+" : "+seconds%60);
            }
        }
    }

    String calculate_time(long time)
    {
       float total= seconds_2_minutes(MicroSeconds_2_seconds(time));
        int mins = (int) total;
        int sec = minutes_2_seconds((total-mins));
        if(mins<=9 && sec <=9)
        {
            return "0"+String.valueOf(mins)+" : 0"+String.valueOf(sec);
        }
        else if(mins<=9 && sec>9)
        {
            return "0"+String.valueOf(mins)+" : "+String.valueOf(sec);
        }
        else if(sec<=9 && mins >9)
        {
            return String.valueOf(mins)+" : 0"+String.valueOf(sec);
        }
        else
        {
            return String.valueOf(mins)+" : "+String.valueOf(sec);
        }
        
    }
    public void stopTimer()
    {
        timer.cancel();
    }
}