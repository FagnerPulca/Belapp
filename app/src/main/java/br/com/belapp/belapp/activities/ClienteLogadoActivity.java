package br.com.belapp.belapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.presenter.LocalizacaoCliente;

public class ClienteLogadoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth logado = FirebaseAuth.getInstance();

    ImageButton btnBarba, btnCabelo, btnDepilacao, btnOlho, btnSobrancelha, btnUnha;
    LocalizacaoCliente localCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_logado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnBarba = findViewById(R.id.ibBarba);
        btnCabelo = findViewById(R.id.ibCabelo);
        btnDepilacao = findViewById(R.id.ibDepilacao);
        btnOlho = findViewById(R.id.ibOlho);
        btnSobrancelha = findViewById(R.id.ibSobrancelha);
        btnUnha = findViewById(R.id.ibUnha);

        pedirPermissoes();

        btnBarba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Barba");
                intent.putExtra("latitude", localCliente.getLatitude());
                intent.putExtra("longitude", localCliente.getLongitude());
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Barba22", Toast.LENGTH_SHORT).show();
            }
        });
        btnCabelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Cabelo");
                intent.putExtra("latitude", localCliente.getLatitude());
                intent.putExtra("longitude", localCliente.getLongitude());
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Cabelo", Toast.LENGTH_SHORT).show();
            }
        });
        btnDepilacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Depilação");
                intent.putExtra("latitude", localCliente.getLatitude());
                intent.putExtra("longitude", localCliente.getLongitude());
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Depilação", Toast.LENGTH_SHORT).show();
            }
        });
        btnOlho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Olho");
                intent.putExtra("latitude", localCliente.getLatitude());
                intent.putExtra("longitude", localCliente.getLongitude());
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Olho", Toast.LENGTH_SHORT).show();
            }
        });
        btnSobrancelha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Sobrancelha");
                intent.putExtra("latitude", localCliente.getLatitude());
                intent.putExtra("longitude", localCliente.getLongitude());
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Sobrancelha", Toast.LENGTH_SHORT).show();
            }
        });
        btnUnha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Unha");
                intent.putExtra("latitude", localCliente.getLatitude());
                intent.putExtra("longitude", localCliente.getLongitude());
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Unha", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //pede permissão para usar o GPS
    private void pedirPermissoes() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
            configurarServico();
    }

    //trata a permissão de uso do GPS
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configurarServico();
                } else {
                    Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    //métodos que controlam o funcionamento do GPS
    public void configurarServico(){
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                //roda a cada vez que a localização é atualizada
                public void onLocationChanged(Location location) {
                    atualizar(location);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) { }

                public void onProviderEnabled(String provider) { }

                public void onProviderDisabled(String provider) { }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //guarda a coordenadas do GPS
    public void atualizar(Location location)
    {
        localCliente = new LocalizacaoCliente();
        localCliente.setLatitude(location.getLatitude());
        localCliente.setLongitude(location.getLongitude());
        //Toast.makeText(this, String.valueOf(localCliente.getLatitude())+" "+String.valueOf(localCliente.getLongitude()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cliente_logado, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_agenda) {
            // Handle the camera action
        } else if (id == R.id.nav_notificacao) {

        } else if (id == R.id.nav_perfil) {

        } else if (id == R.id.nav_agenda) {

        } else if (id == R.id.nav_promocoes) {

        } else if (id == R.id.nav_sair) {

            logado.signOut();
            Intent intentInicialActivity = new Intent(ClienteLogadoActivity.this, InicialActivity.class);
            startActivity(intentInicialActivity );

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
