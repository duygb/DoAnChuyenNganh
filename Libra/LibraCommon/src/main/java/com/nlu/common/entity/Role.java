package com.nlu.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 40, nullable = false, unique = true)
  @Nationalized
  private String name;

  @Override
  public String toString() {
    return name;
  }
}
