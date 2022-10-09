package edu.northeastern.numad22fa_jiazhechen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PrimeFinderActivity extends AppCompatActivity {
    private static final String TAG = "PrimeFinderActivity";
    TextView primeNumberTextView;
    CheckBox pacifierSwitch;
    int primeNumber;
    volatile boolean terminateFlag;
    Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_finder);
        primeNumberTextView = findViewById(R.id.primeFinderTextView);

        primeNumber = 3;
        terminateFlag = false;
        mainHandler = new Handler(Looper.getMainLooper());

        Button findPrimeButton = findViewById(R.id.findPrimeButton);
        findPrimeButton.setOnClickListener(view -> {
            PrimeFinderThread primeFinderThread = new PrimeFinderThread();
            new Thread(primeFinderThread).start();
        });

        Button terminateButton = findViewById(R.id.terminateSearchButton);
        terminateButton.setOnClickListener(view -> {
            terminateFlag = true;
        });

        pacifierSwitch = findViewById(R.id.pacifierSwitch);
    }

    class PrimeFinderThread implements Runnable {
        @Override
        public void run() {
            int primeNumber = 3;
            int numberToCheck = primeNumber;;
            while (!terminateFlag) {
//            for (numberToCheck = primeNumber; numberToCheck < 3000000; numberToCheck += 2) {
                long startTime = System.currentTimeMillis();
                Log.d(TAG, "run: " + primeNumber);
                Log.d(TAG, "run: " + numberToCheck);
                if (isPrime(numberToCheck)) {
                    primeNumber = numberToCheck;
                }
                numberToCheck += 2;
                if ((System.currentTimeMillis() - startTime) >= 500) {
                    final int finalPrimeNumber = primeNumber;
                    final int finalNumberToCheck = numberToCheck;
                    mainHandler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            primeNumberTextView.setText("Last Prime Number: " + finalPrimeNumber + "\nNow Checking: " + finalNumberToCheck);
                        }
                    });
                }
            }
            final int finalPrimeNumber = primeNumber;
            mainHandler.post(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    primeNumberTextView.setText("Last Prime Number: " + finalPrimeNumber + "\nNow Checking: -");
                }
            });
        }

        private boolean isPrime(int number) {
//            Log.d(TAG, "isPrime: " + number);
            for (int i = 3; i < number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
