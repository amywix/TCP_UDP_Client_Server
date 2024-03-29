//Amy Wickham 12178502
//COIT132229 Assignmemt 1: client / server

package TCP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {

    // initialise member number
    private static int memberNumber = 1;

    public static void main(String[] args) {

        try {
            // Connect to server
            Socket clientSocket = new Socket("localhost", 1102);

            // input and output streams
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            // Continuous loop for entering member details
            try {
                while (true) {
                    // produce member number for next member entry
                    System.out.println("Member Number: " + memberNumber);

                    // prompts for user input
                    System.out.print("Enter First Name: ");
                    String firstName = scanner.nextLine();
                    while (firstName.trim().isEmpty()) {
                        System.out.println("A first name must be entered. \nEnter First Name:");
                        firstName = scanner.nextLine();
                    }

                    System.out.print("Enter Last Name: ");
                    String lastName = scanner.nextLine();
                    while (lastName.trim().isEmpty()) {
                        System.out.println("A last name must be entered. \nEnter Last Name:");
                        lastName = scanner.nextLine();
                    }

                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();
                    while (address.trim().isEmpty()) {
                        System.out.println("Address must be entered. \n Enter address:");
                        address = scanner.nextLine();
                    }

                    System.out.print("Enter Phone: ");
                    String phone = scanner.nextLine();
                    while (phone.trim().isEmpty()) {
                        System.out.println("Phone must be entered. \n Enter Phone:");
                        phone = scanner.nextLine();
                    }

                    // sending member details to server
                    out.writeInt(memberNumber);
                    out.writeUTF(firstName);
                    out.writeUTF(lastName);
                    out.writeUTF(address);
                    out.writeUTF(phone);

                    // feedback from client
                    System.out.println("\nMEMBER DETAILS SENT TO SERVER");

                    memberNumber++;

                    System.out.print("Enter 'quit' to exit or press any other key to continue: ");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("quit")) {
                        break;
                    }
                }
                // Close the connection
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
