/**
 * CredentialsDto record for receiving login credentials
 */
package com.calendar.login.dtos;

public record LoginCredentialsDTO(String email, char[] password) {
}
