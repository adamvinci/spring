package org.example.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private String pseudo;
  private String firstname;
  private String lastname;
}