create table client (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  username  varchar(20),
  email varchar(50),
  created_at timestamp,
  constraint pk_client primary key (id)
);

