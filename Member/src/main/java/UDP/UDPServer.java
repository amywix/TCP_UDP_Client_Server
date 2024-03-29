//Amy Wickham 12178502
//COIT132229 Assignmemt 1: client / server
package UDP;

import TCP.Member;
import java.io.*;
import java.net.*;
import java.util.List;

public class UDPServer {

    private static final String OBJECT_FILE = "memberListObject";

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(1103);
            System.out.println("\nServer connected... Waiting for client...");

            while (true) {
                // Receive request from client
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String clientRequest = new String(receivePacket.getData(), 0, receivePacket.getLength());

                if (clientRequest.equals("memberListObject")) {
                    System.out.println("Client request received.");
                    // Read serialized objects from file and send to client
                    sendSerializedObjectsToClient(socket, receivePacket.getAddress(), receivePacket.getPort());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendSerializedObjectsToClient(DatagramSocket socket, InetAddress clientAddress, int clientPort) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(OBJECT_FILE))) {
            List<Member> members = (List<Member>) objectInputStream.readObject();

            // Print the list of members
            System.out.println("Contents of memberListObject file:");
            for (Member member : members) {
                System.out.println(member);
            }
            // Convert the list of members to a byte array
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
            objectOutputStream.writeObject(members);
            objectOutputStream.flush();
            byte[] serializedObject = byteStream.toByteArray();

            // Create a DatagramPacket and send it to the client
            DatagramPacket sendPacket = new DatagramPacket(serializedObject, serializedObject.length, clientAddress, clientPort);
            socket.send(sendPacket);

            // Notify client that all entries have been sent
            String endMessage = "All serialized objects sent.";
            byte[] endData = endMessage.getBytes();
            DatagramPacket endPacket = new DatagramPacket(endData, endData.length, clientAddress, clientPort);
            socket.send(endPacket);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
