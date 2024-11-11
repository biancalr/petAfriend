export type ClientProps = {
  username: string;
  email: string;
  createdAt?: Date;
};

export class ClientEntity {
  constructor(public readonly props: ClientProps) {
    this.props.createdAt = this.props.createdAt ?? new Date();
  }
}
