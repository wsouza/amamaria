package com.redewsouza.amamaria.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.api.NotificacaoService;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;
import com.redewsouza.amamaria.helper.DateCustom;
import com.redewsouza.amamaria.helper.Permissoes;
import com.redewsouza.amamaria.helper.UsuarioFirebase;
import com.redewsouza.amamaria.model.Alerta;
import com.redewsouza.amamaria.model.Destino;
import com.redewsouza.amamaria.model.Notificacao;
import com.redewsouza.amamaria.model.NotificacaoDados;
import com.redewsouza.amamaria.model.Requisicao;
import com.redewsouza.amamaria.model.Usuario;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BtPanicoActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private Button btAcionarPanico;
    private LatLng localUsuarioBt;
    private Alerta alerta;

    private Usuario passageiro;
    private Usuario motorista;
    private Destino destino;


    private GoogleMap mMap;
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    //Notificações
    private Retrofit retrofit;
    private String baseUrl;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_panico);

        //tollbar e botões
        inicializarComponentes();

        //notificações
        notificcoesListar();

        //validar permissões
        Permissoes.validarPermissoes(permissoes, this, 1);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Recuperar localizacao do usuário
        recuperarLocalizacaoUsuario();


    }

    private void recuperarLocalizacaoUsuario() {

        //Objeto responsável por gerenciar a localização do usuário
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.d("Localizacao", "onLocationChanged: " + location.toString() );

                final Double latitude = location.getLatitude();
                final Double longitude = location.getLongitude();

                //recuperar latitude e longitude
                localUsuarioBt = new LatLng(latitude, longitude);

                //Atualizar GeoFire
                UsuarioFirebase.atualizarDadosLocalizacao(latitude, longitude);

                recuperarEndereco( latitude, longitude );


                btAcionarPanico.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        salvarAcionarPanico(latitude, longitude);


                    }
                });


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        /*
         * 1) Provedor da localização
         * 2) Tempo mínimo entre atualizacões de localização (milesegundos)
         * 3) Distancia mínima entre atualizacões de localização (metros)
         * 4) Location listener (para recebermos as atualizações)
         * */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    1,
                    locationListener
            );
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResultado : grantResults) {

            //permission denied (negada)
            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                //Alerta
                alertaValidacaoPermissao();
            } else if (permissaoResultado == PackageManager.PERMISSION_GRANTED) {
                //Recuperar localizacao do usuario

                /*
                 * 1) Provedor da localização
                 * 2) Tempo mínimo entre atualizacões de localização (milesegundos)
                 * 3) Distancia mínima entre atualizacões de localização (metros)
                 * 4) Location listener (para recebermos as atualizações)
                 * */
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            5000,
                            1,
                            locationListener
                    );
                }

            }
        }

    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * Acionar botão do pânico
     */
    private void salvarAcionarPanico(double lat, double lon){

        Address addressDestino = recuperarEndereco( lat, lon );
        if( addressDestino != null ){

            final Destino destino = new Destino();
            destino.setCidade( addressDestino.getAdminArea() );
            destino.setCep( addressDestino.getPostalCode() );
            destino.setBairro( addressDestino.getSubLocality() );
            destino.setRua( addressDestino.getThoroughfare() );
            destino.setNumero( addressDestino.getFeatureName() );
            destino.setLatitude( String.valueOf(addressDestino.getLatitude()) );
            destino.setLongitude( String.valueOf(addressDestino.getLongitude()) );

            StringBuilder mensagem = new StringBuilder();
            mensagem.append( "Cidade: " + destino.getCidade() );
            mensagem.append( "\nRua: " + destino.getRua() );
            mensagem.append( "\nBairro: " + destino.getBairro() );
            mensagem.append( "\nNúmero: " + destino.getNumero() );
            mensagem.append( "\nCep: " + destino.getCep() );

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Confirme seu endereco!")
                    .setMessage(mensagem)
                    .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //salvar requisição
                            salvarRequisicao( destino );

                        }
                    }).setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }

    private void salvarRequisicao(Destino destino){

        Requisicao requisicao = new Requisicao();
        requisicao.setDestino( destino );

        Usuario usuarioPassageiro = UsuarioFirebase.getDadosUsuarioLogado();
        usuarioPassageiro.setLatitude( String.valueOf( destino.getLatitude() ) );
        usuarioPassageiro.setLongitude( String.valueOf( destino.getLongitude() ) );

        requisicao.setPassageiro( usuarioPassageiro );
        requisicao.setStatus( Requisicao.STATUS_AGUARDANDO );
        requisicao.setData( DateCustom.dataAtual() );
        requisicao.salvar();

        enviarNotificacao();

        Intent intent = new Intent(getApplicationContext(), ListHistoricoActivity.class);
        startActivity( intent );


    }



    private Address recuperarEndereco(double lat, double lon){

        /*
                Geocoding -> processo de transformar um endereço
                ou descrição de um local em latitude/longitude
                Reverse Geocoding -> processo de transformar latitude/longitude
                em um endereço
                */
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault() );

        try {

            List<Address> listaEndereco = geocoder.getFromLocation(lat, lon,1);

            //busca por endereço
            //String stringEndereco = "Avenida Paulista, 1374 - Bela Vista, São Paulo - SP";
            //List<Address> listaEndereco = geocoder.getFromLocationName(stringEndereco,1);
            if( listaEndereco != null && listaEndereco.size() > 0 ){
                Address endereco = listaEndereco.get(0);

                /*
                 * onLocationChanged:
                 * Address[
                 *   addressLines=[0:"Av. República do Líbano, 1291 - Parque Ibirapuera, São Paulo - SP, Brazil"],
                 *   feature=1291,
                 *   admin=São Paulo,
                 *   sub-admin=São Paulo,
                 *   locality=São Paulo,
                 *   thoroughfare=Avenida República do Líbano,
                 *   postalCode=null,
                 *   countryCode=BR,
                 *   countryName=Brazil,
                 *   hasLatitude=true,
                 *   latitude=-23.5926719,
                 *   hasLongitude=true,
                 *   longitude=-46.6647561,
                 *   phone=null,
                 *   url=null,
                 *   extras=null]
                 * */

                Double lat1 = endereco.getLatitude();
                Double lon1 = endereco.getLongitude();

                //limpar mapa
                mMap.clear();
                //definir distância do mapa
                LatLng localUsuario = new LatLng(lat1, lon1);
                mMap.addMarker(new MarkerOptions().position(localUsuario).title("Meu local"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localUsuario,17));


                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center( localUsuario );
                circleOptions.radius(100);//em metros
                circleOptions.strokeWidth(10);
                circleOptions.strokeColor(Color.GREEN);
                circleOptions.fillColor( Color.argb(128,255, 153, 0) );//0 até 255 alpha
                mMap.addCircle( circleOptions );

                //Log.d("local", "onLocationChanged: " + endereco.getAddressLine(0) );
                //retorna o endereço
                return endereco;


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void notificcoesListar(){

        baseUrl = "https://fcm.googleapis.com/fcm/";
        retrofit = new Retrofit.Builder()
                .baseUrl( baseUrl )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /* Ativar tópicos
            () Mexicana
            (X) Alemã
            (x) Brasileira
         */

        //FirebaseMessaging.getInstance().unsubscribeFromTopic("protetores");

    }

    private void inicializarComponentes(){

        Toolbar toolbar = findViewById(R.id.toolbarPerfil);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_logo);

        //Inicializar componentes
        //editDestino = findViewById(R.id.editDestino);
        //linearLayoutDestino = findViewById(R.id.linearLayoutDestino);

        //acionar botão
        btAcionarPanico = findViewById(R.id.btAcionarPanico);

        //Configurações iniciais
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void enviarNotificacao(){

        //se for enviar por aparelho
        String tokenAluno = "";

        String to = "";//Tópico ou token
        //to = tokenAluno;
        to = "/topics/protetores";

        //Monta objeto notificação
        Notificacao notificacao = new Notificacao("Botão do pânico acionado!", "Um novo alerta estar disponível no App Ama Maria.");
        NotificacaoDados notificacaoDados = new NotificacaoDados(to, notificacao );

        NotificacaoService service = retrofit.create(NotificacaoService.class);
        Call<NotificacaoDados> call = service.salvarNotificacao( notificacaoDados );

        call.enqueue(new Callback<NotificacaoDados>() {
            @Override
            public void onResponse(Call<NotificacaoDados> call, Response<NotificacaoDados> response) {


                if( response.isSuccessful() ){

                    Toast.makeText(getApplicationContext(),
                            "Uma notificação foi criada, codigo: " + response.code(),
                            Toast.LENGTH_LONG ).show();

                }
            }

            @Override
            public void onFailure(Call<NotificacaoDados> call, Throwable t) {

            }
        });


    }

    public void recuperarToken(View view){

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {

                String token = instanceIdResult.getToken();
                Log.i("getInstanceId", "token getInstanceId: " + token );

            }
        });

    }

}
