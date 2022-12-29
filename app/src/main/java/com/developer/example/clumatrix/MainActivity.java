package com.developer.example.clumatrix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    EditText edtUname,edtPass;
    Button btnLogin;
    ConstraintLayout login;

    KeyGenerator keyGenerator;
    SecretKey secretKey;
    byte[] IV = new byte[16];
    SecureRandom random;
    String TAG="Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        edtUname=findViewById(R.id.edtUname);
        edtPass=findViewById(R.id.edtPass);
        btnLogin=findViewById(R.id.btnLogin);
        login=findViewById(R.id.login);

        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();

            random = new SecureRandom();
            random.nextBytes(IV);

            byte[] encrypt = encrypt("Shantanu".getBytes(), secretKey, IV);
            String encryptText = new String(encrypt, "UTF-8");
            Log.d(TAG, "encrypt : "+encrypt);
            String decrypt = decrypt(encrypt, secretKey, IV);
            Log.d(TAG, "decrypt : "+decrypt);

        } catch (Exception e) {
            e.printStackTrace();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // To close Virtual Keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnLogin.getWindowToken(), 0);

                if (edtUname.getText().toString().equals("") || edtPass.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter Username / Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (edtUname.getText().toString().equals("admin") && edtPass.getText().toString().equals("admin")) {
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,Dashboard.class));
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Username / Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }

    public static String decrypt(byte[] cipherText, SecretKey key, byte[] IV) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}