import { faker } from "@faker-js/faker";
import { ClientProps } from "../../entities/client.entity";

type Props = {
  username?: string;
  email?: string;
  createdAt?: Date;
};

export function ClientDataBuilder(props: Props): ClientProps {
  return {
    username: props.username ?? faker.internet.username(),
    email: props.email ?? faker.internet.email(),
    createdAt: props.createdAt ?? new Date(),
  };
}
