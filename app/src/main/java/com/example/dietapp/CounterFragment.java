package com.example.dietapp;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import java.util.Locale;

public class CounterFragment extends Fragment {
    private TextView textViewDay, textViewHour, textViewMinute, textViewSecond, textViewMillisecond;
    private MaterialButton reset, start, stop;
    private int days, hours, minutes, seconds, milliSeconds;
    private long millisecondTime, startTime, timeBuff, updateTime = 0L;
    private Handler handler;
    private boolean isCounterRunning = false;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isCounterRunning) {
                millisecondTime = SystemClock.uptimeMillis() - startTime;
                updateTime = timeBuff + millisecondTime;
                days = (int) (updateTime / (1000 * 60 * 60 * 24));
                hours = (int) ((updateTime / (1000 * 60 * 60)) % 24);
                minutes = (int) ((updateTime / (1000 * 60)) % 60);
                seconds = (int) ((updateTime / 1000) % 60);
                milliSeconds = (int) (updateTime % 1000);

                // Gün, saat, dakika, saniye ve salise bilgilerini ayrı TextView'lere yaz
                textViewDay.setText(String.format(Locale.getDefault(), "%02d", days));
                textViewHour.setText(String.format(Locale.getDefault(), "%02d", hours));
                textViewMinute.setText(String.format(Locale.getDefault(), "%02d", minutes));
                textViewSecond.setText(String.format(Locale.getDefault(), "%02d", seconds));
                textViewMillisecond.setText(String.format(Locale.getDefault(), "%03d", milliSeconds));

                handler.postDelayed(this, 0);
            }
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter, container, false);

        textViewDay = view.findViewById(R.id.textViewDay);
        textViewHour = view.findViewById(R.id.textViewHour);
        textViewMinute = view.findViewById(R.id.textViewMinute);
        textViewSecond = view.findViewById(R.id.textViewSecond);
        textViewMillisecond = view.findViewById(R.id.textViewMillisecond);

        reset = view.findViewById(R.id.reset);
        start = view.findViewById(R.id.start);
        stop = view.findViewById(R.id.stop);

        handler = new Handler(Looper.getMainLooper());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                isCounterRunning = true;
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
                stop.setEnabled(true);
                start.setEnabled(false);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCounterRunning = false;
                timeBuff += millisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCounterRunning = false;
                millisecondTime = 0L;
                startTime = 0L;
                timeBuff = 0L;
                updateTime = 0L;
                days = 0;
                hours = 0;
                minutes = 0;
                seconds = 0;
                milliSeconds = 0;

                // Gün, saat, dakika, saniye ve salise bilgilerini sıfırla
                textViewDay.setText("00");
                textViewHour.setText("00");
                textViewMinute.setText("00");
                textViewSecond.setText("00");
                textViewMillisecond.setText("000");
            }
        });

        // Başlangıç değerlerini ayarla
        textViewDay.setText("00");
        textViewHour.setText("00");
        textViewMinute.setText("00");
        textViewSecond.setText("00");
        textViewMillisecond.setText("000");
    }

    @Override
    public void onPause() {
        super.onPause();
        // Fragment durduğunda sayaç işlemlerini durdur
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fragment tekrar aktifleştiğinde ve sayaç başlatılmışsa devam et
        if (isCounterRunning) {
            handler.postDelayed(runnable, 0);
        }
    }
}
