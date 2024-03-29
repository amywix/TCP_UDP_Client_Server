//Amy Wickham 12178502
//COIT132229 Assignmemt 1: client / server

package TCP;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {

    private static final String MEMBER_FILE = "memberList.txt";
    private static final String OBJECT_FILE = "memberListObject";

    public static void main(String[] args) {
        ExecutorService ex = Executors.newFixedThreadPool(10);

        TCPServer server = new TCPServer(); // Create an instance of TCPServer

        try (ServerSocket serverSocket = new ServerSocket(1102)) {
            System.out.println("\nServer connected... Waiting for client...");

            while (true) {
                Socket socket = serverSocket.accept();
                ex.submit(new ClientConnection(socket, server));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ex.shutdown();
    }

    static class ClientConnection extends Thread {

        private static int clientCount = 0;
        private Socket s;
        private TCPServer server; // Reference to TCPServer instance

        public ClientConnection(Socket socket, TCPServer server) {
            this.s = socket;
            this.server = server; // Assign TCPServer instance
            clientCount++;
        }

        @Override
        public void run() {

            Timer timer = new Timer();
            long threadId = Thread.currentThread().getId();
            System.out.println("Client " + clientCount + "  Thread ID: " + threadId);
            System.out.println("======================================================================");

            DataInputStream in;
            DataOutputStream out;

            try {
                in = new DataInputStream(s.getInputStream());
                out = new DataOutputStream(s.getOutputStream());

                try {
                    while (true) {
                        // Read m details from client
                        int memberNumber = in.readInt();
                        String firstName = in.readUTF();
                        String lastName = in.readUTF();
                        String address = in.readUTF();
                        String phone = in.readUTF();

                        // Member details string
                        //  String memberDetails = String.format("Member Number: %d, First Name: %s, Last Name: %s, Phone: %s, Address: %s",
                        //          memberNumber, firstName, lastName, address, phone);
                        Member m = new Member(memberNumber, firstName, lastName, address, phone);
                        System.out.println("\nReceived member details: " + m);
                        System.out.println("==========================================================================================");

                        // Write m to file
                        writeMemberToFile(m);

// Add the Member object to the object file
                        addToMemberListObject(m);

                        //  MemberListUpdater memberListUpdater = new MemberListUpdaterImpl(server, m);
                        //         memberListUpdater.updateMemberList(m);
                        // Send feedback to client
                        out.writeUTF("Saved successfully to file.");
                        out.flush();
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private void writeMemberToFile(Member m) {
            try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(MEMBER_FILE, true)))) {
                printWriter.println(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void addToMemberListObject(Member m) {
        try {
            // Read existing objects from the file
            List<Member> members = readMemberListObjects();

            // Append the new member to the list
            members.add(m);

            // Write the updated list back to the file
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(OBJECT_FILE));
            objectOutputStream.writeObject(members);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        printMemberListObjects();
    }

    static List<Member> readMemberListObjects() {
        List<Member> members = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(OBJECT_FILE))) {
            Object obj;
            while ((obj = objectInputStream.readObject()) != null) {
                if (obj instanceof List) {
                    members.addAll((List<Member>) obj);
                }
            }
        } catch (EOFException e) {
            // Reached end of file
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return members;
    }

    static void printMemberListObjects() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(OBJECT_FILE))) {
            System.out.println("Contents of memberListObject file:");

            // Read the list of members
            List<Member> members = (List<Member>) objectInputStream.readObject();

            // Print each member
            for (Member member : members) {
                System.out.println(member);
            }
        } catch (EOFException e) {
            // Reached end of file
            System.out.println("End of file reached.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
