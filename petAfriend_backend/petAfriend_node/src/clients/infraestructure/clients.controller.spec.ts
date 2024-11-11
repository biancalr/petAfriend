import { Test, TestingModule } from "@nestjs/testing";
import { ClientsController } from "./clients.controller";
import { UsersService } from "./users.service";

describe("UsersController", () => {
  let controller: ClientsController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ClientsController],
      providers: [UsersService],
    }).compile();

    controller = module.get<ClientsController>(ClientsController);
  });

  it("should be defined", () => {
    expect(controller).toBeDefined();
  });
});
