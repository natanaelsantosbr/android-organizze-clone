package com.cursoandroid.organizze.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFirebase {
    private static FirebaseAuth autenticacao;


    //retorna a instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao()
    {
        if(autenticacao == null)
        return  FirebaseAuth.getInstance();

        return  autenticacao;
    }
}
