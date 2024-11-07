import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { EnvConfigService } from './shared/infaestructure/env-config/env-config.service';

@Module({
  imports: [],
  controllers: [AppController],
  providers: [AppService, EnvConfigService],
})
export class AppModule {}
