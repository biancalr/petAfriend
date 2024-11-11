import { Module } from "@nestjs/common";
import { AppController } from "./app.controller";
import { AppService } from "./app.service";
import { EnvConfigService } from "./shared/infraestructure/env-config/env-config.service";
import { EnvConfigModule } from "./shared/infraestructure/env-config/env-config.module";

@Module({
  imports: [EnvConfigModule],
  controllers: [AppController],
  providers: [AppService, EnvConfigService],
})
export class AppModule {}
