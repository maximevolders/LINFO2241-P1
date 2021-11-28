import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;



public class Main {

    /**
     * This function hashes a string with the SHA-1 algorithm
     * @param data The string to hash
     * @return An array of 20 bytes which is the hash of the string
     */
    public static byte[] hashSHA1(String data) throws NoSuchAlgorithmException{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return md.digest(data.getBytes());
    }

    /**
     * This function is used by a client to send the information needed by the server to process the file
     * @param out Socket stream connected to the server where the data are written
     * @param hashPwd SHA-1 hash of the password used to derive the key of the encryption
     * @param pwdLength Length of the clear password
     * @param fileLength Length of the encrypted file
     */
    public static void sendRequest(DataOutputStream out, byte[] hashPwd, int pwdLength,
                       long fileLength) throws IOException {
        out.write(hashPwd,0, 20);
        out.writeInt(pwdLength);
        out.writeLong(fileLength);
    }

    public static void main(String[] args) {
        try{
            String password = "test";
            SecretKey keyGenerated = CryptoUtils.getKeyFromPassword(password);
            // Faire une liste de files pour en envoyer plusieurs, et terminer par un élément null? pour terminer l'échange
            int  numberOfFiles = 1;
            //plus 1 en dessous pour que le dernier envoie soit un null?
            File[] inputFiles = new File[numberOfFiles+1];
            for (int i = 0; i<numberOfFiles; i++){
                //Modifier nom file dans les trois for ci-dessous
                inputFiles[i] = new File("test_file.pdf");
            }

            File[] encryptedFiles = new File[numberOfFiles+1];
            for (int i = 0; i<numberOfFiles; i++){
                inputFiles[i] = new File("test_file-encrypted-client.pdf");
            }

            File[] decryptedClients = new File[numberOfFiles+1];
            for (int i = 0; i<numberOfFiles; i++){
                inputFiles[i] = new File("test_file-decrypted-client.pdf");
            }
            
            //Modification. Le encrypt file est fait après la création du socket pour pouvoir looper dessus.

            // Creating socket to connect to server (in this example it runs on the localhost on port 3333)
            Socket socket = new Socket("localhost", 3333);
            // Le for commence après ce commentaire en français, on est obligé de looper
            // sur les outputstream et inputstream.

            for(int i = 0; i < numberOfFiles; i++){
                // This is an example to help you create your request
                CryptoUtils.encryptFile(keyGenerated, inputFiles[i], encryptedFiles[i]);
                System.out.println("Encrypted file length: " + encryptedFiles[i].length());

                // For any I/O operations, a stream is needed where the data are read from or written to. Depending on
                // where the data must be sent to or received from, different kind of stream are used.
                OutputStream outSocket = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outSocket);
                InputStream inFile = new FileInputStream(encryptedFiles[i]);
                DataInputStream inSocket = new DataInputStream(socket.getInputStream());


                // SEND THE PROCESSING INFORMATION AND FILE
                byte[] hashPwd = hashSHA1(password);
                int pwdLength = 4;
                long fileLength = encryptedFiles[i].length();
                sendRequest(out, hashPwd, pwdLength, fileLength);
                out.flush();

                FileManagement.sendFile(inFile, out);
                /*
                int readCount;
                byte[] buffer = new byte[64];
                //read from the file and send it in the socket
                while ((readCount = inFile.read(buffer)) > 0){
                    out.write(buffer, 0, readCount);
                }*/

                // GET THE RESPONSE FROM THE SERVER
                OutputStream outFile = new FileOutputStream(decryptedClients[i]);
                long fileLengthServer = inSocket.readLong();
                System.out.println("Length from the server: "+ fileLengthServer);
                FileManagement.receiveFile(inSocket, outFile, fileLengthServer);

                /*
                int readFromSocket = 0;
                int byteRead;
                byte[] readBuffer = new byte[64];
                while(readFromSocket < fileLengthServer){
                    byteRead = inSocket.read(readBuffer);
                    readFromSocket += byteRead;
                    outFile.write(readBuffer, 0, byteRead);
                }*/
                out.close();
                outSocket.close();
                outFile.close();
                inFile.close();
                inSocket.close();
            } // Fin de la boucle for?
            socket.close();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException |
                NoSuchPaddingException | IllegalBlockSizeException | IOException | BadPaddingException |
                InvalidKeyException e) {
            e.printStackTrace();
        }

    }
}
