package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dienthoai_laptop.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Lienhe extends AppCompatActivity {
    private TextInputLayout tilName;
    private TextInputEditText etName;
    private TextInputLayout tilEmail;
    private TextInputEditText etEmail;
    private TextInputLayout tilMessage;
    private TextInputEditText etMessage;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lienhe);
        AnhXa();
        EvenButton();
    }
    private void EvenButton() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String message = etMessage.getText().toString().trim();
                if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                    Toast.makeText(Lienhe.this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Lienhe.this, "Gửi thông tin thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void AnhXa() {
        tilName = findViewById(R.id.tilName);
        etName = findViewById(R.id.etName);
        tilEmail = findViewById(R.id.tilEmail);
        etEmail = findViewById(R.id.etEmail);
        tilMessage = findViewById(R.id.tilMessage);
        etMessage = findViewById(R.id.etMessage);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
}