import { validate as uuidValidate } from "uuid";
import { Entity } from "../../entity";

type StubProps = {
  prop1: string;
  prop2: number;
};

class StubEntity extends Entity<StubProps> {}

describe("Entity unit tests", () => {
  it("Should set props and id", () => {
    const props = { prop1: "value 1", prop2: 26 };
    const entity = new StubEntity(props);

    expect(entity.props).toStrictEqual(props);
    expect(entity._id).not.toBeNull();
    expect(uuidValidate(entity._id)).toBeTruthy();
  });

  it("Should accept a valid uuid", () => {
    const props = { prop1: "value 1", prop2: 26 };
    const id = "451aea2a-16e2-41c8-8fe5-55f22fed43ae";
    const entity = new StubEntity(props, id);

    expect(uuidValidate(entity._id)).toBeTruthy();
    expect(entity._id).toBe(id);
  });

  it("Should convert an entity to a Javascript object", () => {
    const props = { prop1: "value 1", prop2: 26 };
    const id = "451aea2a-16e2-41c8-8fe5-55f22fed43ae";
    const entity = new StubEntity(props, id);

    expect(entity.toJSON()).toStrictEqual({
      id,
      ...props,
    });
  });
});
