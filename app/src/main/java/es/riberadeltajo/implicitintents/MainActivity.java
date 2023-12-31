package es.riberadeltajo.implicitintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import es.riberadeltajo.implicitintents.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = binding.txtWeb.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                Intent chooser = Intent.createChooser(i, "Elige el navegador");
                startActivity(chooser);
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coordenadas = binding.edLatitud.getText().toString();
                coordenadas += ","+binding.edLongitud.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("geo:"+coordenadas));
                Intent chooser = Intent.createChooser(i, "Elige gestor de mapas");
                startActivity(chooser);
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = binding.txtEmail.getText().toString();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto:"+correo));

                //Destinatarios
                String[] array_destinatarios = new String[]{correo, "sasdas@gmail.com"};
                i.putExtra(Intent.EXTRA_EMAIL,array_destinatarios);

                //Asunto
                i.putExtra(Intent.EXTRA_SUBJECT, "Mensaje Automatico");

                //Texto
                i.putExtra(Intent.EXTRA_TEXT, "BUENOS DIAS. ESTE MENSAJE HA SIDO AUTOGENERADO");

                i.setType("message/rfc822");
                startActivity(i);
            }
        });

        Intent i = getIntent();
        String action = i.getAction();

        if(action.equals(Intent.ACTION_SEND)){
            if(i.getStringExtra(Intent.EXTRA_TEXT) == null){
                Uri uri = i.getParcelableExtra(Intent.EXTRA_STREAM);
                if(uri != null) {
                    InputStream is = null;
                    try {
                        is = getContentResolver().openInputStream(uri);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    binding.imageView.setImageBitmap(BitmapFactory.decodeStream(is));
                }
            }else{
                binding.textView.setText(i.getStringExtra(Intent.EXTRA_TEXT));
            }
        }


    }
}