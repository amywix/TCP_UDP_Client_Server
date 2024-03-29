//Amy Wickham 12178502
//COIT132229 Assignmemt 1: client / server
package UDP;

import TCP.Member;
import java.io.*;
import java.net.*;
import java.util.*;

public class UDPClient {

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 1103;
            System.out.println("\nCONNECTED TO SERVER:");

            // Request serialized objects from server
            String request = "memberListObject";
            byte[] requestData = request.getBytes();
            DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, serverAddress, serverPort);
            socket.send(requestPacket);

            // Receive and deserialize objects from server
            receiveAndDeserializeObjects(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveAndDeserializeObjects(DatagramSocket socket) {
        try {
            byte[] buffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

            // Receive the entire list of members
            socket.receive(receivePacket);
            ByteArrayInputStream byteStream = new ByteArrayInputStream(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
            @SuppressWarnings("unchecked")
            // Cast to List<Member>
            List<Member> members = (List<Member>) objectInputStream.readObject();

            // Print the details of all received members
            System.out.println("Received members:");
            for (Member receivedMember : members) {
                System.out.println(receivedMember);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
