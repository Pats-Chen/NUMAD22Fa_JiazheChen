package edu.northeastern.numad22fa_jiazhechen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PrimeFinderActivity extends AppCompatActivity {
    private static final String TAG = "PrimeFinderActivity";
    private long backPressTime;
    TextView primeNumberTextView;
    CheckBox pacifierSwitch;
    static int primeNumber = 3;
    static int numberToCheck = 3;
    static boolean terminateFlag = false;
    Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_finder);
        primeNumberTextView = findViewById(R.id.primeFinderTextView);

        mainHandler = new Handler(Looper.getMainLooper());

        Button findPrimeButton = findViewById(R.id.findPrimeButton);
        findPrimeButton.setOnClickListener(view -> {
            PrimeFinderThread primeFinderThread = new PrimeFinderThread();
            new Thread(primeFinderThread).start();
        });

        Button terminateButton = findViewById(R.id.terminateSearchButton);
        terminateButton.setOnClickListener(view -> {
            terminateFlag = true;
//            primeThread.interrupt();
            final int finalPrimeNumber = primeNumber;
            mainHandler.post(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    primeNumberTextView.setText("Last Prime Number: " + finalPrimeNumber + "\nNow Checking: -");
                }
            });
        });

        pacifierSwitch = findViewById(R.id.pacifierSwitch);
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
//        primeThread.interrupt();
        outState.putInt("Last Prime Number", primeNumber);
        outState.putInt("Last Number to Check", numberToCheck);
        outState.putBoolean("Terminate Flag", terminateFlag);
        Log.d(TAG, "onSaveInstanceState: " + primeNumber);
        Log.d(TAG, "onSaveInstanceState: " + numberToCheck);
        Log.d(TAG, "onSaveInstanceState: " + terminateFlag);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        primeNumber = savedInstanceState.getInt("Last Prime Number");
        numberToCheck = savedInstanceState.getInt("Last Number to Check");
        terminateFlag = savedInstanceState.getBoolean("Terminate Flag");
        Log.d(TAG, "onRestoreInstanceState: " + primeNumber);
        Log.d(TAG, "onRestoreInstanceState: " + numberToCheck);
        Log.d(TAG, "onRestoreInstanceState: " + terminateFlag);
        if (!terminateFlag) {
//            PrimeFinderThread primeFinderThread = new PrimeFinderThread();
//            new Thread(primeFinderThread).start();
            Toast.makeText(getBaseContext(), "Hit Find Primes Button to Continue!", Toast.LENGTH_SHORT).show();
        }
        if (primeNumber == 3) {
            mainHandler.post(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    primeNumberTextView.setText("Last Prime Number: -\nNow Checking: -");
                }
            });
        } else {
            final int finalPrimeNumber = primeNumber;
            mainHandler.post(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    primeNumberTextView.setText("Last Prime Number: " + finalPrimeNumber + "\nNow Checking: -");
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            primeNumber = 3;
            numberToCheck = 3;
            terminateFlag = false;
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press Back Again to Stop Searching!", Toast.LENGTH_SHORT).show();
        }
        backPressTime = System.currentTimeMillis();
    }

    class PrimeFinderThread implements Runnable {
        @Override
        public void run() {
            numberToCheck = primeNumber;
            long startTime = System.currentTimeMillis();
            while (!terminateFlag) {
                if (isPrime(numberToCheck)) {
                    primeNumber = numberToCheck;
                }
                numberToCheck += 2;
                if ((System.currentTimeMillis() - startTime) % 500 == 0) {
                    Log.d(TAG, "This prime number is: " + primeNumber);
                    Log.d(TAG, "This number to be check is: " + numberToCheck);
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
        }

        private boolean isPrime(int number) {
            for (int i = 3; i < number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
