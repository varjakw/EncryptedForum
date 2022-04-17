import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

class handler implements Runnable {
    private static String publicKeyForum = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxwUTdXC6/8vb6Lq+4EKtsuuuZcqQ/JA1HifmUHIqvs/x5VfC+L1t/3pvkDsgUJwrSjK0htEcnJAuMzEAOqkhnu4szVOd+eF3hF8On8omoeraYYGmnxmsAK26XsMGtYeFIuvRjQAMQwNzbz5MXoqKc1WBQq3wFSUnXArE63xaHU95p5JXJ1V5bs3BysK64bCGxriuNQ97ZIAPWzYzmVOLFnfG1xO1R5t+ya93tvPgaCgbF/dq96nhOb0lVZBcAdYdAiUQ7czqaNJ7N4TFZEQaPvQL3xNU/Dv22fR2MOonY2Y3sWOm3w2cvgNJlq4lbjYMvKSkOjIboDSEfrzH1cvyAQIDAQAB";
    private static String privateKeyTrinityGroup = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDHBRN1cLr/y9vour7gQq2y665lypD8kDUeJ+ZQciq+z/HlV8L4vW3/em+QOyBQnCtKMrSG0RyckC4zMQA6qSGe7izNU5354XeEXw6fyiah6tphgaafGawArbpewwa1h4Ui69GNAAxDA3NvPkxeiopzVYFCrfAVJSdcCsTrfFodT3mnklcnVXluzcHKwrrhsIbGuK41D3tkgA9bNjOZU4sWd8bXE7VHm37Jr3e28+BoKBsX92r3qeE5vSVVkFwB1h0CJRDtzOpo0ns3hMVkRBo+9AvfE1T8O/bZ9HYw6idjZjexY6bfDZy+A0mWriVuNgy8pKQ6MhugNIR+vMfVy/IBAgMBAAECggEAMqWiDBMCI7KXZiSQCrYtSgkcOp9QQlXYjrIMtJA5PJHit0CcR3y9j3RzC7tgP+NeSOWad2yj4Vv3xeNEcak0+yMVl7Md0seECNW//P4O0snXhVTfOEHqOcn8Ub1D6dJxGS/2BfHrLOql57ts5+pNzYgG+xcUr+zilVPQRTVCIY7ybuqTp+MS+euz55JiilQYdpRCjr6eMFQS07kABly8MpjRke2TSYsTgWLspSE+53Fys48vjy3OPUpWnOA9GDqETfkZl+L4k+OSo/m7tB3Om+UnnQVFDHb2z/8YeROUVdCiaCC1dCbZlFihbie5wVpWP5+bPKwgtm325n8P2p82GQKBgQD+J284ePvsrQUc3zUC1Jw61R+5fX6N/9UZhuo3PVcg9N/Oz0DRhgLoTkw6XJeseoUHuu9rusA5QnAM5E8XknmHXRlWoX4W5/c5PlEclQYTbodSclsEW8D1HpH0Dac+gCrtgxruHaroVgXjtgdroTNLUE2GOoMSDXGuTdXtEiIrpwKBgQDIdyB4j5vYwLLpc6Nr6AQDk1cO41M1LxgEOvUUlzlAa96ydHNN/R22vVp9a9Rjr38f70C+FruV7caK7navKy9Ta09Vd43n39ZWdOndF6tGDxXSmGQq5te6rfCjWf9CBDfg9pQn51sQk8tUxB0x8M31jQl/IGNXwWH15WnH636KFwKBgDVOrCNaLOz9dV8S6hmH4qTsYtUTH2JFULsj8yhW81l0QyUrj1rjEuUK1y4+kYmZwSFX8jPUXdEkwD/T+0rkbB0aR+BFyx6e1Nnq2jR2hWm5+yQt3C1T2WsQWGiqHHQdieN7ZkCfdGN6941FdtT6YLnhRAFtcGRu8XMv3Dj3XrdtAoGBAJHj2HbFFMd/HfW2GbW7KcisXwxPtkubVWAmxBvTHnBa0LyQ8SVN02UdkxB5/Bz8KIlGJpjwlF49qGONvkQnKo8MJrVth5G7nhJXjadAxX38FoaoLyXl0BRk6B8JrTIPPddzvEj6H5UF6lFrVE/3DvzoQoQJPyMrcq5w4jYDlZjLAoGBAJsiHPuIcx/qBi0GQpBJ7F9qUrTTj+4vVqc5IVO7by6BZJ385XqJ9OQ/xQNQxa81RI4xYdfoQxlamxvHOiah4cM082Ud1xNAKq0hitI1exHX9GXaf0CuSSUsBzZSmV7gD3eWY2hLNnvuKCOnwm4REk5HV1Hbg+/c10Nx/EsEyiVc";
    private static String privateKeyUCDGroup = "test";
    Scanner scanner = new Scanner(System.in);
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket socket;
    boolean isloggedin;
    //list of previous posts, stored as ciphertext
    static List<String> previousPosts = new ArrayList<String>();

    // constructor
    public handler(Socket socket, String name,
                   DataInputStream dis, DataOutputStream dos) throws IOException {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.socket = socket;
        this.isloggedin = true;
    }

    public static PublicKey getPublicKey(String b64PublicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PublicKey publicKey = null;
        //public key generated in X509 format so we use X509 keyspec to convert to RSA key
        //because we have base64 encoded key
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(b64PublicKey.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(keySpec);
        //using keyfactory to recreate instance of public key
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String b64PrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PrivateKey privateKey = null;
        System.out.println(b64PrivateKey); //test
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(b64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        keyFactory = KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static byte[] encryptMessage(String message, String publicKey) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        // PublicKey public = getPublicKey.();

        //a Cipher object for encryption with our public key
        Cipher encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));

        //now we encrypt the message:
        //change message into bytes then encrypt
        return encryptCipher.doFinal(message.getBytes());
    }

    public static String decryptMessage(byte[] encryptedMessageBytes, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //another Cipher but made for decryption mode & with private key
        Cipher decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(decryptCipher.doFinal(encryptedMessageBytes));
    }

    public static String decryptMessage(String data, String b64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        return decryptMessage(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(b64PrivateKey));
    }

    @Override
    public void run() {
        String username;
        String newPost;
        String decryptedString;
        String UserPrivateKey;
        String line;

        previousPosts.add("kDpt5cYQNbyxgITwZ/cCegdR9zbexI+kCEUIx1oPmPZeicFlK9RBLXG6ITBuqsv6biK3SaxHFLqTJanr2UDRLIwfNvrp8bHRR7RVkNE2/9VmboJ7dGeEtMTYXBlqQYZTAaHpauPHeeI2MXvJ1+N7bUFa0ZUWNFGVDl89Nu9KsF3344OpulB45xXlAinReqv7dVHG0dtOvIKiwbpqmmIYXNV/4PvqZeBDoZ1mMT1anl3TqeRq4cvM70Gmh6j37b+gBYxF0p0UTMvFFJhcIua66HAPrtSWVxFs5BoESkL5vtLneHAPnN3hTem9pcUEr62vvsCzv9XapR9s3NymGojjAA==");

/*
        //show ciphertext posts
        for (int x = 0; x < previousPosts.size(); x++) {
            try {
                this.dos.writeUTF(previousPosts.get(x));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
*/

        try {
            this.dos.writeUTF("Please enter your username to view the forum");
            username = this.dis.readUTF();
            System.out.println(username);
            this.dos.writeUTF(username + " logged in.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrivateKey groupKey = null;
        try {
            groupKey = getPrivateKey(privateKeyTrinityGroup);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }


        while (server.users.get("varjak").equals(groupKey)) {
            try {
                   // this.dos.writeUTF("Keys match"); //testing
                    this.dos.writeUTF("Would you like to view, post or exit?");

                    if(this.dis.readUTF().equals("view")){
                        this.dos.writeUTF("Loading posts...");
                        //send plaintext to user
                            //decrypt each element of previousPosts and put in a new decrypted list
                        String data = previousPosts.get(0);
                        this.dos.writeUTF("program stops on this line, no matter what code is here");
                        this.dos.writeUTF("post 1 is : " + data);

                        String decryptedData = decryptMessage(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(privateKeyTrinityGroup));

                        this.dos.writeUTF(decryptedData);

                    }
                    if(this.dis.readUTF().equals("post")){
                        this.dos.writeUTF("Please enter your post.");
                        newPost = dis.readUTF();
                        String encryptedMessage = Base64.getEncoder().encodeToString(encryptMessage(newPost, publicKeyForum));
                        previousPosts.add(encryptedMessage);
                        dos.writeUTF(encryptedMessage);

                    }
                    if(this.dis.readUTF().equals("quit")){
                        this.dos.writeUTF("Saving posts...");
                        this.isloggedin = false;
                        this.socket.close();
                        //write to file posts.txt with the new previousPosts as content
                        PrintWriter writer = new PrintWriter("posts.txt", "UTF-8");
                        for (int x = 0; x < previousPosts.size(); x++) {
                            writer.println(previousPosts.get(x));
                        }
                        writer.close();

                    }



                    dos.writeUTF("You do not have permission for this group. Contact an administrator.");
                    for (int x = 0; x < previousPosts.size(); x++) {
                        dos.writeUTF(previousPosts.get(x));
                    }



            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }

    }
}