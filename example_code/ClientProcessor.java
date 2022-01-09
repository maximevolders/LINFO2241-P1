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
        try{
            // Stream to read request from socket

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

            System.out.println("File length: " + networkFile.length());


            //We start reading the file with 10k passwords
            File file = new File("10k-most-common_filered.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            boolean foundmdp = false;

            String password = " ";
            
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] checkpwd = null;
            Exception exc = null;

            do{ // As long as the guessed password isn't the correct one
                try{
                    while(!Arrays.equals(request.getHashPassword(),checkpwd) && (password = br.readLine()) != null){ // as long as the hash aren't the same AND password !=null
                        if(password.length() == request.getLengthPwd()) //Check only password of the right length
                            checkpwd = md.digest(password.getBytes());
                    }
                    if(password!=null){ //If a hash is found, check if we get a key
                        SecretKey serverKey = CryptoUtils.getKeyFromPassword(password);
                        CryptoUtils.decryptFile(serverKey, networkFile, decryptedFile);
                        foundmdp = true;}
                    exc = null;
                }catch(BadPaddingException e){ // if getKeyFromPassword sends back an error, we catch it and try the next password
                    exc = e;
                }
            }while(exc!=null);

            br.close();  
            if(!foundmdp){ // If we couldn't find the password, we try brute force
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
            

            dataInputStream.close();
            inputStream.close();
            inDecrypted.close();
            outFile.close();
            socket.close();
        } catch(Exception e) {
            System.out.println("There are errors of type: "+e.toString());
        }

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
