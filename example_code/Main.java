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
import java.util.Random;



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
            int ClientID = Integer.parseInt(args[0]);
            boolean takeRandomPwd = false;

            // Creating socket to connect to server (in this example it runs on the localhost on port 3333)
            Socket socket = new Socket("192.168.1.24", 3333);
            //int portNb = socket.getPort();

            int pwdLength = 3;
            Random rand = new Random();
            String password = "";

            if(takeRandomPwd){
                for(int i = 0 ; i < pwdLength ; i++){
                    char c = (char)(rand.nextInt(26) + 97);
                    password += c;
                }
            } else {
                File mdp10k = new File("10k-most-common_filered.txt");
                BufferedReader br = new BufferedReader(new FileReader(mdp10k));
                int lineNumber = rand.nextInt(8200);
                for(int i = 0 ; i < lineNumber; i++){
                    password = br.readLine();
                }
                br.close();
            }

            pwdLength = password.length();
            System.out.println("Mot de passe: " + password);

            SecretKey keyGenerated = CryptoUtils.getKeyFromPassword(password);

            File inputFile = new File("test_file.pdf");
            File encryptedFile = new File("test_file_encryptedClient" + ClientID + ".pdf");
            File decryptedClient = new File("test_file_decryptedClient" + ClientID + ".pdf");

            // This is an example to help you create your request
            CryptoUtils.encryptFile(keyGenerated, inputFile, encryptedFile);
            System.out.println("Encrypted file length: " + encryptedFile.length());

            // For any I/O operations, a stream is needed where the data are read from or written to. Depending on
            // where the data must be sent to or received from, different kind of stream are used.
            OutputStream outSocket = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outSocket);
            InputStream inFile = new FileInputStream(encryptedFile);
            DataInputStream inSocket = new DataInputStream(socket.getInputStream());


            // SEND THE PROCESSING INFORMATION AND FILE
            long start = System.currentTimeMillis();
            
            byte[] hashPwd = hashSHA1(password);
            long fileLength = encryptedFile.length();
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
            OutputStream outFile = new FileOutputStream(decryptedClient);
            long fileLengthServer = inSocket.readLong();
            System.out.println("Length from the server: "+ fileLengthServer);
            FileManagement.receiveFile(inSocket, outFile, fileLengthServer);

            long finish = System.currentTimeMillis();

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
            socket.close();

            long timeElapsed = finish - start;
            System.out.println("ClientID: " + ClientID + ", Time: " + timeElapsed);

            FileWriter stats = new FileWriter("statsClient_" + ClientID + ".csv");
            PrintWriter writer = new PrintWriter(stats);
            writer.println(String.valueOf(ClientID) + "," + String.valueOf(timeElapsed));
            writer.flush();
            writer.close();
            stats.close();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException |
                NoSuchPaddingException | IllegalBlockSizeException | IOException | BadPaddingException |
                InvalidKeyException e) {
            e.printStackTrace();
        }

    }
}