import { Entity } from "@/shared/domain/entities/entity";

export type ClientProps = {
  username: string;
  email: string;
  createdAt?: Date;
};

export class ClientEntity extends Entity<ClientProps> {
  constructor(
    public readonly props: ClientProps,
    id?: string,
  ) {
    super(props, id);
    this.props.createdAt = this.props.createdAt ?? new Date();
  }

  update(value: string): void {
    this.username = value;
  }

  get username() {
    return this.props.username;
  }

  private set username(value: string) {
    this.props.username = value;
  }

  get email() {
    return this.props.email;
  }

  get createdAt() {
    return this.props.createdAt;
  }
}
