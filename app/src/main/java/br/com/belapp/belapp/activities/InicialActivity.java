package br.com.belapp.belapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.belapp.belapp.DAO.EstabelecimentoDAO;
import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Agenda;
import br.com.belapp.belapp.model.Endereco;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Profissional;
import br.com.belapp.belapp.model.Promocoes;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.presenter.LocalizacaoCliente;


public class InicialActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //para pegar dados de  localização via dispositivo
    private LocationManager locationMangaer=null;
    private LocationListener locationListener=null;



    private static final String TAG = "Debug";
    private Boolean flag = false;



    private FirebaseAuth logado = FirebaseAuth.getInstance();
    private TextView AbriActivityLogin;
    ImageButton btnBarba, btnCabelo, btnDepilacao, btnOlho, btnSobrancelha, btnUnha;
    LocalizacaoCliente localCliente;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //verifica se esta logado
        isLogado();

        locationMangaer = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,locationListener);

        btnBarba = findViewById(R.id.ibBarba);
        btnCabelo = findViewById(R.id.ibCabelo);
        btnDepilacao = findViewById(R.id.ibDepilacao);
        btnOlho = findViewById(R.id.ibOlho);
        btnSobrancelha = findViewById(R.id.ibSobrancelha);
        btnUnha = findViewById(R.id.ibUnha);

        pedirPermissoes();
//        EstabelecimentoDAO estabelecimentoDAO = new EstabelecimentoDAO();
//        estabelecimentoDAO.inserirEstabelecimento();


        btnBarba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Barba");
                intent.putExtra("latitude", localCliente.getLatitude());
                intent.putExtra("longitude", localCliente.getLongitude());
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Barba", Toast.LENGTH_SHORT).show();
            }
        });
        btnCabelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Cabelo");
                intent.putExtra("latitude", localCliente.getLatitude());
                intent.putExtra("longitude", localCliente.getLongitude());
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Cabelo", Toast.LENGTH_SHORT).show();
            }
        });
        btnDepilacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                double lati = 0;
                double longi = 0;
                try
                {
                    lati = localCliente.getLatitude();
                    longi = localCliente.getLongitude();
                }
                catch(Exception e)
                {
                    System.out.println("error-->"+e.getMessage());
                }
                intent.putExtra("categoria", "Depilação");
                intent.putExtra("latitude", lati);
                intent.putExtra("longitude", longi);
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Depilação", Toast.LENGTH_SHORT).show();
            }
        });
        btnOlho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Olho");
                intent.putExtra("latitude", localCliente.getLatitude());
                intent.putExtra("longitude", localCliente.getLongitude());
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Olho", Toast.LENGTH_SHORT).show();
            }
        });
        btnSobrancelha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Sobrancelha");
                intent.putExtra("latitude", localCliente.getLatitude());
                intent.putExtra("longitude", localCliente.getLongitude());
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Sobrancelha", Toast.LENGTH_SHORT).show();
            }
        });
        btnUnha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Unha");
                intent.putExtra("latitude", localCliente.getLatitude());
                intent.putExtra("longitude", localCliente.getLongitude());
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Unha", Toast.LENGTH_SHORT).show();
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

    public void isLogado() {
        if (logado.getCurrentUser() != null) {
            abrirTelaPrincipal();

        }
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.app_bar_search) {

            Intent intetAbrirTelaLogin = new Intent(InicialActivity.this, BuscaActivity.class);
            startActivity(intetAbrirTelaLogin);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cadastrar) {
            // Handle the cadastrar action

        } else if (id == R.id.nav_logar) {


            Intent intetAbrirTelaLogin = new Intent(InicialActivity.this, LoginActivity.class);
            startActivity(intetAbrirTelaLogin);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void abrirTelaPrincipal() {

        Intent intentAbritPrincipal = new Intent(InicialActivity.this , ClienteLogadoActivity.class);
        startActivity(intentAbritPrincipal);

    }



    /*----------Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {

            //editLocation.setText("");
          //  pb.setVisibility(View.INVISIBLE);
            Toast.makeText(getBaseContext(),"Location changed : Lat: " +
                            loc.getLatitude()+ " Lng: " + loc.getLongitude(),
                    Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " +loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " +loc.getLatitude();
            Log.v(TAG, latitude);

            /*----------to get City-Name from coordinates ------------- */
            String cityName=null;
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try
            {
             addresses = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                    cityName=addresses.get(0).getLocality();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            String s = longitude+"\n"+latitude +
                    "\n\nMy Currrent City is: "+cityName;
            //editLocation.setText(s);
            System.out.println("localização : "+s);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }
}
