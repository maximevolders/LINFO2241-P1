import javax.crypto.SecretKey; // This class represents a factory for secret keys.
import java.io.*;
import java.net.Socket; // his class implements client sockets (also called just "sockets"). A socket is an endpoint for communication between two machines. 
import java.security.InvalidKeyException; // This is the exception for invalid Keys (invalid encoding, wrong length, uninitialized, etc).

public class ClientProcessor implements Runnable{

    private File networkFile;
    private File decryptedFile;
    private Socket socket;

    public ClientProcessor(File networkFile, File decryptedFile, Socket socket){
        this.networkFile = networkFile;
        this.decryptedFile = decryptedFile;
        this.socket = socket;
    }

    /**
     * @param in Stream from which to read the request
     * @return Request with information required by the server to process encrypted file
     */
    public static Request readRequest(DataInputStream in) throws IOException {
        byte [] hashPwd = new byte[20];
        int count = in.read(hashPwd,0, 20);
        if (count < 0){
            throw new IOException("Server could not read from the stream");
        }
        int pwdLength = in.readInt();
        long fileLength = in.readLong();

        return new Request(hashPwd, pwdLength, fileLength);
    }

    public void run() {
        try{ // GROS TRY CATCH A ENLEVER, JE SAIS JUSTE PAS COMMENT GERER L'ERREUR
            // Stream to read request from socket
            //On devrait peut etre faire une fonction de tout ça, et le mettre
            //Dans une boucle while pour pouvoir recevoir x fichiers, toujours
            //Avec le même mdp. Donc mettre le bon pw dans un final?

            //Faire une fonction c'est peut etre pas nécessaire sauf pour le mot de passe
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            // Stream to write response to socket
            DataOutputStream outSocket = new DataOutputStream(socket.getOutputStream());


            // Stream to write the file to decrypt
            OutputStream outFile = new FileOutputStream(networkFile);

            Request request = readRequest(dataInputStream);
            long fileLength = request.getLengthFile();

            FileManagement.receiveFile(inputStream, outFile, fileLength);
            /*
            int readFromFile = 0;
            int bytesRead = 0;
            byte[] readBuffer = new byte[64];
            System.out.println("[Server] File length: "+ fileLength);
            while((readFromFile < fileLength)){
                bytesRead = inputStream.read(readBuffer);
                readFromFile += bytesRead;
                outFile.write(readBuffer, 0, bytesRead);
            }*/

            System.out.println("File length: " + networkFile.length());

            // HERE THE PASSWORD IS HARDCODED, YOU MUST REPLACE THAT WITH THE BRUTEFORCE PROCESS
            Exception mdp = null;
            do{ 
                try{
                    String password = "test";
                    SecretKey serverKey = CryptoUtils.getKeyFromPassword(password);

                    CryptoUtils.decryptFile(serverKey, networkFile, decryptedFile);
                    mdp = null;
                } catch(InvalidKeyException e) {
                    mdp = e;
                }
            }while(mdp != null);

            // Send the decryptedFile
            InputStream inDecrypted = new FileInputStream(decryptedFile);
            outSocket.writeLong(decryptedFile.length());
            outSocket.flush();
            FileManagement.sendFile(inDecrypted, outSocket);
            /*
            int readCount;
            byte[] buffer = new byte[64];
            //read from the file and send it in the socket
            while ((readCount = inDecrypted.read(buffer)) > 0){
                outSocket.write(buffer, 0, readCount);
            }*/

            dataInputStream.close();
            inputStream.close();
            inDecrypted.close();
            outFile.close();
            socket.close();
        } catch(Exception e) {
            System.out.println("Y a des erreurs de type: "+e.toString());
        }
/*        Exception decrypt;
        do{
            try{
            String password = this.guessPwd();
            SecretKey serverKey = CryptoUtils.getKeyFromPassword(password);

            CryptoUtils.decryptFile(serverKey, networkFile, decryptedFile);
            decrypt = null;
            }
            catch(Exception e){
                decrypt = e;
            }
        } while(decrypt != null); */
    }
}
