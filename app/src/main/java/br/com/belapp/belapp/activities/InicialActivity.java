package br.com.belapp.belapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
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
import br.com.belapp.belapp.servicos.MyServiceLocation;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class InicialActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //para pegar dados de  localização via dispositivo
    private LocationManager locationMangaer=null;
    private LocationListener locationListener=null;
    LocalizacaoCliente localCliente;
    //logalização
    MyServiceLocation localizao;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;


    private static final String TAG = "Debug";
    private Boolean flag = false;

    private FirebaseAuth logado = FirebaseAuth.getInstance();
    private TextView AbriActivityLogin;
    ImageButton btnBarba, btnCabelo, btnDepilacao, btnOlho, btnSobrancelha, btnUnha;

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

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        localizao = new MyServiceLocation(InicialActivity.this);


        if (localizao.canGetLocation()) {
            double longitude = localizao.getLongitude();
            double latitude = localizao.getLatitude();
            //Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {
            localizao.showSettingsAlert();
        }

        btnBarba = findViewById(R.id.ibBarba);
        btnCabelo = findViewById(R.id.ibCabelo);
        btnDepilacao = findViewById(R.id.ibDepilacao);
        btnOlho = findViewById(R.id.ibOlho);
        btnSobrancelha = findViewById(R.id.ibSobrancelha);
        btnUnha = findViewById(R.id.ibUnha);

//        EstabelecimentoDAO estabelecimentoDAO = new EstabelecimentoDAO();
//        estabelecimentoDAO.inserirEstabelecimento();



        btnBarba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Barba");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Barba", Toast.LENGTH_SHORT).show();
            }
        });
        btnCabelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Cabelo");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Cabelo", Toast.LENGTH_SHORT).show();
            }
        });
        btnDepilacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                /*double lati = 0;
                double longi = 0;
                try
                {
                    lati = localCliente.getLatitude();
                    longi = localCliente.getLongitude();
                }
                catch(Exception e)
                {
                    System.out.println("error-->"+e.getMessage());
                }*/
                intent.putExtra("categoria", "Depilação");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Depilação", Toast.LENGTH_SHORT).show();
            }
        });
        btnOlho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Olho");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Olho", Toast.LENGTH_SHORT).show();
            }
        });
        btnSobrancelha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Sobrancelha");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Sobrancelha", Toast.LENGTH_SHORT).show();
            }
        });
        btnUnha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Unha");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                startActivity(intent);
                Toast.makeText(InicialActivity.this, "Unha", Toast.LENGTH_SHORT).show();
            }
        });
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

            Intent intetAbrirTelaLogin = new Intent(InicialActivity.this, TelaBuscaActivity.class);
            intetAbrirTelaLogin.putExtra("latitude", localizao.getLatitude());
            intetAbrirTelaLogin.putExtra("longitude", localizao.getLongitude());
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
            /*Toast.makeText(getBaseContext(),"Location changed : Lat: " +
                            loc.getLatitude()+ " Lng: " + loc.getLongitude(),
                    Toast.LENGTH_SHORT).show();*/
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

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(Object permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission((String) permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }

                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(InicialActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localizao.stopListener();
    }
}
