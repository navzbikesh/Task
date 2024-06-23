package org.example;

/**
 * Represents a message with content.
 */
public record Messages(String content) {
    // The class body is empty because a record implicitly provides:
    // - A constructor that initializes the 'content' field
    // - Getter method 'getContent()' for the 'content' field
    // - Useful 'toString()', 'equals()', and 'hashCode()' methods
}