package br.unitins.studyflow.service;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class FirebaseAdminSetup {

    void onStart(@Observes StartupEvent ev) {
        System.out.println("--- INICIANDO FIREBASE ADMIN SDK ---");
        try {
            // mudar isso no futuro para variavel de ambiente
            FileInputStream serviceAccount = new FileInputStream("C:\\key-firebase\\studyflow-pi-firebase-adminsdk-fbsvc-07a7781669.json");

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println(">>> Firebase Admin SDK inicializado com SUCESSO! <<<");
            }
        } catch (IOException e) {
            System.err.println(">>> ERRO CRÍTICO AO INICIALIZAR O FIREBASE ADMIN SDK: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}