package vn.techmaster.demojpa.model.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name="foo")
@Table(name="foo")
public class Foo {
  @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String name;
}

/*
Identity
CREATE TABLE public.foo (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"name" varchar(255) NULL,
	CONSTRAINT foo_pkey PRIMARY KEY (id)
);

Auto
CREATE TABLE public.foo (
	id int8 NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT foo_pkey PRIMARY KEY (id)
);


CREATE TABLE public.foo (
	id int8 NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT foo_pkey PRIMARY KEY (id)
);
*/