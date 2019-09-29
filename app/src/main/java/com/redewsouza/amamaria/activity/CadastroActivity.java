package com.redewsouza.amamaria.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;
import com.redewsouza.amamaria.helper.Base64Custom;
import com.redewsouza.amamaria.helper.UsuarioFirebase;
import com.redewsouza.amamaria.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    private Switch switchTipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        campoNome = findViewById(R.id.editNomeProtetor);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        switchTipoUsuario = findViewById(R.id.switchTipoUsuario);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                //Validar se os campos foram preenchidos
                if ( !textoNome.isEmpty() ){
                    if ( !textoEmail.isEmpty() ){
                        if ( !textoSenha.isEmpty() ){

                            usuario = new Usuario();
                            usuario.setNome( textoNome );
                            usuario.setEmail( textoEmail );
                            usuario.setSenha( textoSenha );
                            usuario.setTipo( verificaTipoUsuario() );

                            cadastrarUsuario(usuario);

                        }else {
                            Toast.makeText(CadastroActivity.this,
                                    "Preencha a senha!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CadastroActivity.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void cadastrarUsuario(final Usuario usuario){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful() ){



                    try{

                        //String idUsuario = Base64Custom.codificarBase64( usuario.getEmail() );
                        String idUsuario = task.getResult().getUser().getUid();
                        usuario.setIdUsuario( idUsuario );
                        usuario.salvar();

                        //Atualizar nome no UserProfile
                        UsuarioFirebase.atualizarNomeUsuario( usuario.getNome() );

                        // Redireciona o usuário com base no seu tipo
                        // Se o usuário for passageiro chama a activity maps
                        // senão chama a activity requisicoes
                        if( verificaTipoUsuario() == "M" ){
                            startActivity(new Intent(CadastroActivity.this, HomeActivity.class ));
                            finish();

                            Toast.makeText(CadastroActivity.this,
                                    "Sucesso ao cadastrar!",
                                    Toast.LENGTH_SHORT).show();

                        }else {
                            startActivity(new Intent(CadastroActivity.this, SerProtetorActivity.class ));
                            finish();

                            Toast.makeText(CadastroActivity.this,
                                    "Sucesso ao cadastrar-se como protetor!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {

                    String errorCode = task.getException().getMessage();

                    String excecao = "";

                    switch (errorCode) {

                        case "ERROR_INVALID_CUSTOM_TOKEN":
                            excecao = "The custom token format is incorrect. Please check the documentation.";
                            break;

                        case "ERROR_CUSTOM_TOKEN_MISMATCH":
                            excecao = "The custom token corresponds to a different audience.";
                            break;

                        case "ERROR_INVALID_CREDENTIAL":
                            excecao = "The supplied auth credential is malformed or has expired.";
                            break;

                        case "ERROR_INVALID_EMAIL":
                            excecao = "The email address is badly formatted.";
                            break;

                        case "ERROR_WRONG_PASSWORD":
                            excecao = "The password is invalid or the user does not have a password.";
                            break;

                        case "ERROR_USER_MISMATCH":
                            excecao = "The supplied credentials do not correspond to the previously signed in user.";
                            break;

                        case "ERROR_REQUIRES_RECENT_LOGIN":
                            excecao = "This operation is sensitive and requires recent authentication. Log in again before retrying this request.";
                            break;

                        case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                            excecao = "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.";
                            break;

                        case "ERROR_EMAIL_ALREADY_IN_USE":
                            excecao = "The email address is already in use by another account.   ";
                            break;

                        case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                            excecao = "This credential is already associated with a different user account.";
                            break;

                        case "ERROR_USER_DISABLED":
                            excecao = "The user account has been disabled by an administrator.";
                            break;

                        case "ERROR_USER_TOKEN_EXPIRED":
                            excecao = "The user\\'s credential is no longer valid. The user must sign in again.";
                            break;

                        case "ERROR_USER_NOT_FOUND":
                            excecao = "There is no user record corresponding to this identifier. The user may have been deleted.";
                            break;

                        case "ERROR_INVALID_USER_TOKEN":
                            excecao = "The user\\'s credential is no longer valid. The user must sign in again.";
                            break;

                        case "ERROR_OPERATION_NOT_ALLOWED":
                            excecao = "This operation is not allowed. You must enable this service in the console.";
                            break;

                        case "ERROR_WEAK_PASSWORD":
                            excecao = "The given password is invalid.";
                            break;

                    }

                    /*
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        excecao= "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }*/

                    Toast.makeText(CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    /**
     * p = protetor
     * m = protegid
     * @return
     */
    public String verificaTipoUsuario(){
        return switchTipoUsuario.isChecked() ? "P" : "M" ;
    }


}
