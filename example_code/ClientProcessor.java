import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey; // This class represents a factory for secret keys.
import java.io.*;
import java.net.Socket; // his class implements client sockets (also called just "sockets"). A socket is an endpoint for communication between two machines. 
import java.security.MessageDigest;
import java.util.Arrays;

public class ClientProcessor implements Runnable{

    private Socket socket;
    private int portNb;

    public ClientProcessor(Socket socket, int portNb){
        this.socket = socket;
        this.portNb = portNb;
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

            File decryptedFile = new File("test_file-decrypted-server" + portNb + ".pdf");
            File networkFile = new File("temp-server" + portNb + ".pdf");

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


            //On commence par la lecture du fichier
            File file = new File("10k-most-common_filered.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            boolean foundmdp = false;

            String password = " ";
            
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] checkpwd = null;
            Exception exc = null;

            do{ // Tant que le mdp n'est pas le bon
                try{
                    while(!Arrays.equals(request.getHashPassword(),checkpwd) && (password = br.readLine()) != null){ //tant que les hash ne sont pas les mêmes ET que password !=null
                        if(password.length() == request.getLengthPwd()) //On regarde seulement les mdp de la bonne taille
                            checkpwd = md.digest(password.getBytes());
                    }
                    if(password!=null){ //si on a un hash, on regarde si on obtient une key. Si pas, on reprend en haut là où on s'était arrêté
                        SecretKey serverKey = CryptoUtils.getKeyFromPassword(password);
                        CryptoUtils.decryptFile(serverKey, networkFile, decryptedFile);
                        foundmdp = true;}
                    exc = null;
                }catch(BadPaddingException e){ // Si getKeyFromPassword nous renvoit une erreur (pas le bon mdp), on catch l'erreur et on reprend en haut
                    exc = e;
                }
            }while(exc!=null);

            br.close();  
            if(!foundmdp){ // Si on a pas de mdp, on va faire le brute force pour tous les "mots" de la taille request.getKegnthPwd()
                password = "";  
                for(int i = 0 ; i < request.getLengthPwd() ; i++){
                    char c = 'a';
                    password += c;
                }
                do{
                    try{
                        while(!Arrays.equals(request.getHashPassword(),checkpwd)){
                            password = findMDP(password, request.getLengthPwd()-1, request.getLengthPwd());
                            checkpwd = md.digest(password.getBytes());
                        }
                        SecretKey serverKey = CryptoUtils.getKeyFromPassword(password);
                        CryptoUtils.decryptFile(serverKey, networkFile, decryptedFile);
                        exc = null;
                    }catch(BadPaddingException e){
                        exc = e;
                    }
                }while(exc!=null);
            }

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

    public static String findMDP(String mdpTest, int index, int len){
        char[] charmdp = new char[len];
        charmdp = mdpTest.toCharArray();
        if(charmdp[index] == 'z'){
            charmdp[index] = 'a';
            mdpTest = String.valueOf(charmdp);
            index--;
            return findMDP(mdpTest, index, len);
        }
        else{
            charmdp[index]++;
            mdpTest = String.valueOf(charmdp);
            return mdpTest;
        }
    }
}
