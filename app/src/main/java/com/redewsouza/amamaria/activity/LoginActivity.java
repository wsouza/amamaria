package com.redewsouza.amamaria.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;
import com.redewsouza.amamaria.helper.UsuarioFirebase;
import com.redewsouza.amamaria.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoEntrar = findViewById(R.id.buttonEntrar);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if ( !textoEmail.isEmpty() ){
                    if ( !textoSenha.isEmpty() ){

                        usuario = new Usuario();
                        usuario.setEmail( textoEmail );
                        usuario.setSenha( textoSenha );
                        validarLogin();

                    }else {
                        Toast.makeText(LoginActivity.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha o email!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void validarLogin(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    abrirTelaPrincipal();

                }else {

                    String excecao = "";

                    /*
                    String errorCode = task.getException().getMessage();



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
                    */

                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e ) {
                        excecao = "Usuário não está cadastrado.";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void abrirTelaPrincipal(){

        UsuarioFirebase.redirecionaUsuarioLogado(LoginActivity.this);
    }

}
