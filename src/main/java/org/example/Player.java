package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents a player that communicates with another player.
 */
public class Player implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Player.class);

    private final String playerId;
    private final BlockingQueue<Messages> inbox;
    private Player otherPlayer;
    private int messageCounter = 0;

    /**
     * Constructor to initialize a player with a unique ID and an inbox for receiving messages.
     *
     * @param playerId Unique identifier for the player.
     */
    public Player(String playerId) {
        this.playerId = playerId;
        this.inbox = new LinkedBlockingQueue<>();
    }

    /**
     * Sets the other player that this player will communicate with.
     *
     * @param otherPlayer Another instance of Player that this player communicates with.
     */
    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }

    /**
     * Sends a message to the other player.
     * Creates a new message with the given content and sends it to the other player.
     * Increases the message counter after sending each message.
     *
     * @param content Content of the message to be sent.
     */
    public void sendMessage(String content) {
        Messages message = new Messages(content);
        otherPlayer.receiveMessage(message);
        messageCounter++;
    }

    /**
     * Receives a message from the other player and adds it to the inbox.
     *
     * @param message Message received from the other player.
     */
    public void receiveMessage(Messages message) {
        inbox.offer(message);
    }

    /**
     * Executes the message handling logic in a separate thread.
     * Continuously listens for messages in the inbox, processes them by appending
     * the message counter, and sends the updated message back to the other player.
     */
    @Override
    public void run() {
        try {
            while (messageCounter < 10) {
                Messages message = inbox.take();
                String newContent = message.content() + " " + messageCounter;
                logger.info("{} received: {}, sending: {}", playerId, message.content(), newContent);
                sendMessage(newContent);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}