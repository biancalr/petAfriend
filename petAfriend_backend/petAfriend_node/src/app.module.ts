import { Module } from "@nestjs/common";
import { AppController } from "./app.controller";
import { AppService } from "./app.service";
import { EnvConfigService } from "./shared/infraestructure/env-config/env-config.service";
import { EnvConfigModule } from "./shared/infraestructure/env-config/env-config.module";
import { UsersModule } from "./users/infraestructure/users.module";

@Module({
  imports: [EnvConfigModule, UsersModule],
  controllers: [AppController],
  providers: [AppService, EnvConfigService],
})
export class AppModule {}
