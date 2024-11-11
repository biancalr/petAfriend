import { faker } from "@faker-js/faker";
import { ClientEntity, ClientProps } from "../../client.entity";

describe("ClientEntity", () => {
  let props: ClientProps;
  let sut: ClientEntity;

  beforeEach(() => {
    props = {
      username: faker.internet.username(),
      email: faker.internet.email(),
    };

    sut = new ClientEntity(props);
  });
  it("Constructor method", () => {
    expect(sut.props.username).toEqual(props.username);
    expect(sut.props.email).toEqual(props.email);
  });
});
