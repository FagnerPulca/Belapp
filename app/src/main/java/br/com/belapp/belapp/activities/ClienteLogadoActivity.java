package br.com.belapp.belapp.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import android.widget.TextView;

import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.auth.api.Auth;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.common.ConnectionResult;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.presenter.LocalizacaoCliente;
import br.com.belapp.belapp.servicos.MyServiceLocation;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class ClienteLogadoActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth logado = FirebaseAuth.getInstance();
    private GoogleApiClient mGoogleApiClient;

        

    ImageButton btnBarba, btnCabelo, btnDepilacao, btnOlho, btnSobrancelha, btnUnha;
    LocalizacaoCliente localCliente;
    //logalização
    MyServiceLocation localizao;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private ProgressDialog mProgressDialog;
    private ArrayList<String> ids;
    private ArrayList<String> idcateg;
    private ArrayList<Servico> servicos;
    private String categoria;

    private final static int ALL_PERMISSIONS_RESULT = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_logado);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        personalizarCabecalho(navigationView);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        localizao = new MyServiceLocation(ClienteLogadoActivity.this);


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

        ids = new ArrayList<>();
        idcateg = new ArrayList<>();
        servicos = new ArrayList<>();

        buscar();
        dialogBuscando();

        btnBarba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Barba";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Barba");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                intent.putExtra("ids", ids);
                intent.putExtra("idcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Barba", Toast.LENGTH_SHORT).show();
            }
        });
        btnCabelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Cabelo";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Cabelo");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                intent.putExtra("ids", ids);
                intent.putExtra("idcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Cabelo", Toast.LENGTH_SHORT).show();
            }
        });
        btnDepilacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Depilação";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Depilação");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                intent.putExtra("ids", ids);
                intent.putExtra("idcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Depilação", Toast.LENGTH_SHORT).show();
            }
        });
        btnOlho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Olho";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Olho");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                intent.putExtra("ids", ids);
                intent.putExtra("idcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Olho", Toast.LENGTH_SHORT).show();
            }
        });
        btnSobrancelha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Sobrancelha";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Sobrancelha");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                intent.putExtra("ids", ids);
                intent.putExtra("idcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Sobrancelha", Toast.LENGTH_SHORT).show();
            }
        });
        btnUnha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Unha";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("categoria", "Unha");
                intent.putExtra("latitude", localizao.getLatitude());
                intent.putExtra("longitude", localizao.getLongitude());
                intent.putExtra("ids", ids);
                intent.putExtra("idcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Unha", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void personalizarCabecalho(NavigationView navigationView) {
        View header = navigationView.getHeaderView(0);
        TextView titulo = header.findViewById(R.id.tvTituloNavegadorLogado);
        TextView subtitulo = header.findViewById(R.id.tvSubtituloNavegadorLogado);

        FirebaseUser usuario = logado.getCurrentUser();
        if (usuario != null) {
            String nome = usuario.getDisplayName();
            String email = usuario.getEmail();
            if (nome != null) titulo.setText(nome);
            if (email != null) subtitulo.setText(email);
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }







    private void buscar(){
        Query query = FirebaseDatabase.getInstance().getReference("servicos");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Servico servico = dataSnapshot.getValue(Servico.class);
                servicos.add(servico);

                ids.add(servico.getmEstabId());
                idcateg.add(servico.getmCategoria());

                //myAdapter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // empty
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // empty
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // empty
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // empty
            }
        });
    }

    void dialogBuscando(){
        mProgressDialog = new ProgressDialog(ClienteLogadoActivity.this);
        mProgressDialog.setMessage("Buscando...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgress(0);
        mProgressDialog.show();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

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
            Intent intetAbrirTelaLogin = new Intent(ClienteLogadoActivity.this, TelaBuscaActivity.class);
            intetAbrirTelaLogin.putExtra("latitude", localizao.getLatitude());
            intetAbrirTelaLogin.putExtra("longitude", localizao.getLongitude());
            startActivity(intetAbrirTelaLogin);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            Intent intent = new Intent();
            intent.setClass(ClienteLogadoActivity.this, PerfilActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_notificacao) {


        } else if (id == R.id.nav_agenda) {

        } else if (id == R.id.nav_favoritos) {

        } else if (id == R.id.nav_promocoes) {

        } else if (id == R.id.nav_sair) {

            logado.signOut();
            LoginManager.getInstance().logOut();

            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            }

            Intent intentInicialActivity = new Intent(ClienteLogadoActivity.this, InicialActivity.class);
            startActivity(intentInicialActivity);

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        new AlertDialog.Builder(ClienteLogadoActivity.this)
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
