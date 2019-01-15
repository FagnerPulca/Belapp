package br.com.belapp.belapp.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Agendamento;
import br.com.belapp.belapp.model.Cliente;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.presenter.LocalizacaoCliente;
import br.com.belapp.belapp.servicos.MyServiceLocation;
import br.com.belapp.belapp.utils.DateUtils;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static br.com.belapp.belapp.database.utils.FirebaseUtils.getUsuarioAtual;

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
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private ArrayList<Agendamento> mAgendamentos;


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

        try {
            verificarAgendamentos();
        }catch (Exception e){
            System.out.println("ERRO: " + e);
        }

        //Notificacao("Agendamento", "Você tem um serviço agendado");


        btnBarba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Barba";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("mCategoria", "Barba");
                intent.putExtra("mLatitude", localizao.getLatitude());
                intent.putExtra("mLongitude", localizao.getLongitude());
                intent.putExtra("mIds", ids);
                intent.putExtra("mIdcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Barba", Toast.LENGTH_SHORT).show();
            }
        });
        btnCabelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Cabelo";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("mCategoria", "Cabelo");
                intent.putExtra("mLatitude", localizao.getLatitude());
                intent.putExtra("mLongitude", localizao.getLongitude());
                intent.putExtra("mIds", ids);
                intent.putExtra("mIdcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Cabelo", Toast.LENGTH_SHORT).show();
            }
        });
        btnDepilacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Depilação";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("mCategoria", "Depilação");
                intent.putExtra("mLatitude", localizao.getLatitude());
                intent.putExtra("mLongitude", localizao.getLongitude());
                intent.putExtra("mIds", ids);
                intent.putExtra("mIdcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Depilação", Toast.LENGTH_SHORT).show();
            }
        });
        btnOlho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Olho";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("mCategoria", "Olho");
                intent.putExtra("mLatitude", localizao.getLatitude());
                intent.putExtra("mLongitude", localizao.getLongitude());
                intent.putExtra("mIds", ids);
                intent.putExtra("mIdcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Olho", Toast.LENGTH_SHORT).show();
            }
        });
        btnSobrancelha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Sobrancelha";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("mCategoria", "Sobrancelha");
                intent.putExtra("mLatitude", localizao.getLatitude());
                intent.putExtra("mLongitude", localizao.getLongitude());
                intent.putExtra("mIds", ids);
                intent.putExtra("mIdcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Sobrancelha", Toast.LENGTH_SHORT).show();
            }
        });
        btnUnha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "Unha";
                Intent intent = new Intent(ClienteLogadoActivity.this, SaloesActivity.class);
                intent.putExtra("mCategoria", "Unha");
                intent.putExtra("mLatitude", localizao.getLatitude());
                intent.putExtra("mLongitude", localizao.getLongitude());
                intent.putExtra("mIds", ids);
                intent.putExtra("mIdcateg", idcateg);
                startActivity(intent);
                Toast.makeText(ClienteLogadoActivity.this, "Unha", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void personalizarCabecalho(NavigationView navigationView) {
        View header = navigationView.getHeaderView(0);
        TextView titulo = header.findViewById(R.id.tvTituloNavegadorLogado);
        TextView subtitulo = header.findViewById(R.id.tvSubtituloNavegadorLogado);

        if(logado.getUid() != null) {
            DatabaseReference raiz = FirebaseDatabase.getInstance().getReference();
            DatabaseReference cliente = raiz.child("clientes").child(logado.getUid());

            cliente.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Cliente cliente1 = dataSnapshot.getValue(Cliente.class);
                    titulo.setText(cliente1.getmNome());
                    subtitulo.setText(cliente1.getmEmail());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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
            Intent intent = new Intent();
            intent.setClass(ClienteLogadoActivity.this, AgendamentosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_favoritos) {

            Intent intent = new Intent(ClienteLogadoActivity.this, SaloesFavoritosActivity.class);
            intent.putExtra("latitude", localizao.getLatitude());
            intent.putExtra("longitude", localizao.getLongitude());
            intent.putExtra("ids", ids);

            startActivity(intent);
            Toast.makeText(ClienteLogadoActivity.this, "Favoritos", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.nav_promocoes) {
            Intent intent = new Intent(ClienteLogadoActivity.this, PromocoesActivity.class);
            intent.putExtra("latitude", localizao.getLatitude());
            intent.putExtra("longitude", localizao.getLongitude());
            intent.putExtra("ids", ids);
            startActivity(intent);
            Toast.makeText(ClienteLogadoActivity.this,R.string.title_activity_promocoes, Toast.LENGTH_SHORT).show();
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


    //verifica se o cliente que está logado possui
    //um agendamento marcado e gera a notificação
    public void verificarAgendamentos() {
        String idUser = getUsuarioAtual().getUid();
        ArrayList mAgendamentos = new ArrayList<Agendamento>();
        mAgendamentos.clear();

        Query query = FirebaseDatabase.getInstance().getReference("agendamentos").orderByChild("mData");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                Agendamento agendamento = dataSnapshot.getValue(Agendamento.class);


                if(!mAgendamentos.contains(agendamento) && agendamento.getmCliente().equals(idUser))
                {

                    Notificacao("Agendamento", "Você tem um serviço agendado");

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                // empty
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // empty
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                // empty
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // empty
            }
        });

    }

    public void Notificacao(String title, String message)
    {

        Intent resultIntent = new Intent(ClienteLogadoActivity.this , AgendamentosActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(ClienteLogadoActivity.this,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(ClienteLogadoActivity.this);
        mBuilder.setSmallIcon(R.drawable.salao_teste);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) ClienteLogadoActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }

    private void ordenarResultados(){
        Collections.sort(mAgendamentos, (o1, o2) -> DateUtils
                .getDiferencaEntreDuasDatasEspecificas(
                        o2.getmData(),
                        o1.getmData()));

    }


}
