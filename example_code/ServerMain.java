//  Provides the classes and interfaces for cryptographic operations.
// import javax.crypto.SecretKey; // This class represents a factory for secret keys.
import java.io.*;
import java.net.ServerSocket; // A server socket waits for requests to come in over the network. It performs some operation based on that request, and then possibly returns a result to the requester. 
import java.net.Socket; // his class implements client sockets (also called just "sockets"). A socket is an endpoint for communication between two machines. 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors; //Pour les threads
/*
import java.security.NoSuchAlgorithmException; // This exception is thrown when a particular cryptographic algorithm is requested but is not available in the environment.
import java.security.spec.InvalidKeySpecException; //This is the exception for invalid key specifications.
import javax.crypto.BadPaddingException; // This exception is thrown when a particular padding mechanism is expected for the input data but the data is not padded properly.
import javax.crypto.IllegalBlockSizeException; // This exception is thrown when the length of data provided to a block cipher is incorrect, i.e., does not match the block size of the cipher.
import javax.crypto.NoSuchPaddingException; // This exception is thrown when a particular padding mechanism is requested but is not available in the environment.
import java.security.InvalidKeyException; // This is the exception for invalid Keys (invalid encoding, wrong length, uninitialized, etc).
*/


public class ServerMain {

    private static int MAX_T = 4; // nombre maximal de threads

    /**
     * @param in Stream from which to read the request
     * @return Request with information required by the server to process encrypted file
     */ /*
    public static Request readRequest(DataInputStream in) throws IOException {
        byte [] hashPwd = new byte[20];
        int count = in.read(hashPwd,0, 20);
        if (count < 0){
            throw new IOException("Server could not read from the stream");
        }
        int pwdLength = in.readInt();
        long fileLength = in.readLong();

        return new Request(hashPwd, pwdLength, fileLength);
    } */

    public static void main(String[] args) throws IOException {

        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);

        ServerSocket ss = new ServerSocket(3333);
        System.out.println("Waiting connection");

        while(!ss.isClosed()){ // C'EST TOUJOURS VRAI CA, CE SERA A CHANGER (mais je sais pas trop comment voir s'il reste des connections en attente ou non, ptet betement while(true) ?)
            Socket socket = ss.accept();
            System.out.println("Connection from: " + socket + "\n ID : "  + socket.getPort());
            Runnable processor = new ClientProcessor(socket, socket.getPort());
            pool.execute(processor);
            System.out.println("Pool executed");
        }
        ss.close();
        pool.shutdown();
    }
}
