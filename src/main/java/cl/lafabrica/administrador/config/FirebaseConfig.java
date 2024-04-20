package cl.lafabrica.administrador.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @Bean
    public Firestore firestore() throws IOException {
        InputStream serviceAccount = null;
        try {
            serviceAccount = this.getClass().getClassLoader().getResourceAsStream("./serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
            return FirestoreClient.getFirestore(firebaseApp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
