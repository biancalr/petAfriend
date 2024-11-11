export type ClientProps = {
  username: string;
  email: string;
  createdAt?: Date;
};

export class ClientEntity {
  constructor(public readonly props: ClientProps) {
    this.props.createdAt = this.props.createdAt ?? new Date();
  }

  get username() {
    return this.props.username;
  }

  get email() {
    return this.props.email;
  }

  get createdAt() {
    return this.props.createdAt;
  }
}
