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

  it("Getter of username field", () => {
    expect(sut.props.username).toBeDefined();
    expect(sut.props.username).toEqual(props.username);
    expect(typeof sut.props.username).toBe("string");
  });

  it("Getter of email field", () => {
    expect(sut.props.email).toBeDefined();
    expect(sut.props.email).toEqual(props.email);
    expect(typeof sut.props.email).toBe("string");
  });

  it("Getter of createdAt field", () => {
    expect(sut.props.createdAt).toBeDefined();
    expect(sut.props.createdAt).toBeInstanceOf(Date);
  });
});
