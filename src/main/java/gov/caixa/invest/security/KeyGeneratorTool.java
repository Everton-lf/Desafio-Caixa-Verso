package gov.caixa.invest.security;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

public class KeyGeneratorTool {

    public static void main(String[] args) throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair pair = generator.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey  = pair.getPublic();


        Files.write(Paths.get("privateKey.pem"), (
                "-----BEGIN PRIVATE KEY-----\n" +
                        java.util.Base64.getMimeEncoder(64, "\n".getBytes())
                                .encodeToString(privateKey.getEncoded()) +
                        "\n-----END PRIVATE KEY-----\n"
        ).getBytes());


        Files.write(Paths.get("publicKey.pem"), (
                "-----BEGIN PUBLIC KEY-----\n" +
                        java.util.Base64.getMimeEncoder(64, "\n".getBytes())
                                .encodeToString(publicKey.getEncoded()) +
                        "\n-----END PUBLIC KEY-----\n"
        ).getBytes());

        System.out.println("CHAVES GERADAS COM SUCESSO!");
    }
}
