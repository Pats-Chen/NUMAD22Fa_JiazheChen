package edu.northeastern.numad22fa_jiazhechen;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrimeFinderActivity extends AppCompatActivity {
    TextView primeNumberTextView;
    CheckBox pacifierSwitch;
    int primeNumber = 0;
//    boolean terminateFlag = false;
//    ExecutorService primeExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_finder);
        primeNumberTextView = findViewById(R.id.primeFinderTextView);

        Button findPrimeButton = findViewById(R.id.findPrimeButton);
        findPrimeButton.setOnClickListener(view -> {
//            while (!terminateFlag) {
                if (primeNumber == 0) {
                    primeNumber = 3;
                } else {
                    int numberToCheck = primeNumber + 2;
                    while (!isPrime(numberToCheck)) {
//                        if (terminateFlag) {
//                            break;
//                        }
                        numberToCheck += 2;
                    }
                    if (isPrime(numberToCheck)) {
                        primeNumber = numberToCheck;
                        primeNumberTextView.setText("Last Prime Number: " + primeNumber +"\nNow Checking: -");

                    }
//                    primeExecutor = Executors.newSingleThreadExecutor();
//                    Callable<Integer> primeCallable = new PrimeNumberCallable(primeNumber);
//                    Future<Integer> primeFuture = primeExecutor.submit(primeCallable);
//                    try {
//                        primeNumber = primeFuture.get();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                        Log.d("ExecutionException", e.getMessage());
//                    } catch (InterruptedException e) {
//                        Log.d("InterruptedException", e.getMessage());
//                        e.printStackTrace();
//                    }
                }
//            }
//            primeExecutor.shutdown();
        });

        Button terminateButton = findViewById(R.id.terminateSearchButton);
        terminateButton.setOnClickListener(view -> {
//            terminateFlag = true;
//            primeExecutor.shutdownNow();
//            primeNumberTextView.setText("Last Prime Number: " + primeNumber +"\nNow Checking: -");
        });

        pacifierSwitch = findViewById(R.id.pacifierSwitch);
    }

    private boolean isPrime(int number) {
        for (int i = 3; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

//    class PrimeNumberCallable implements Callable<Integer> {
//        private int number;
//
//        public PrimeNumberCallable(int number) {
//            this.number = number;
//        }
//
//        @Override
//        public Integer call() {
//            while (!isPrime(number)) {
//                number += 2;
//            }
//            return number;
//        }
//    }
}
