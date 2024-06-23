package org.example;

/**
 * The main class that initializes and runs the Player communication simulation.
 */
public class Main {
    public static void main(String[] args) {
        // Create players
        Player initiator = new Player("Initiator");
        Player player2 = new Player("Player2");

        // Set each other as communication partners
        initiator.setOtherPlayer(player2);
        player2.setOtherPlayer(initiator); // Optional since it's bidirectional

        // Start threads for both players
        Thread threadInitiator = new Thread(initiator);
        Thread threadPlayer2 = new Thread(player2);

        threadInitiator.start();
        threadPlayer2.start();

        // Wait for players to initialize
        try {
            Thread.sleep(100); // Ensure both players are ready (not always necessary)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Initiator sends the initial message to start the communication
        initiator.sendMessage("Start Message");

        // Wait for threads to finish
        try {
            threadInitiator.join();
            threadPlayer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}