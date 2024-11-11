import {
  Controller,
  Get,
  Post,
  Body,
  Patch,
  Param,
  Delete,
} from "@nestjs/common";
import { UsersService as ClientsService } from "./users.service";
import { CreateClientDto as CreateClientDto } from "./dto/create-user.dto";
import { UpdateUserDto as UpdateClientDto } from "./dto/update-user.dto";

@Controller("clients")
export class ClientsController {
  constructor(private readonly clientsService: ClientsService) {}

  @Post()
  create(@Body() createClientDto: CreateClientDto) {
    return this.clientsService.create(createClientDto);
  }

  @Get()
  findAll() {
    return this.clientsService.findAll();
  }

  @Get(":id")
  findOne(@Param("id") id: string) {
    return this.clientsService.findOne(+id);
  }

  @Patch(":id")
  update(@Param("id") id: string, @Body() updateClientDto: UpdateClientDto) {
    return this.clientsService.update(+id, updateClientDto);
  }

  @Delete(":id")
  remove(@Param("id") id: string) {
    return this.clientsService.remove(+id);
  }
}
