import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FourForBelotCategoryModule } from './category/category.module';
import { FourForBelotLocationModule } from './location/location.module';
import { FourForBelotProfileModule } from './profile/profile.module';
import { FourForBelotGameModule } from './game/game.module';
import { FourForBelotNotificationModule } from './notification/notification.module';
import { FourForBelotRatingModule } from './rating/rating.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FourForBelotCategoryModule,
        FourForBelotLocationModule,
        FourForBelotProfileModule,
        FourForBelotGameModule,
        FourForBelotNotificationModule,
        FourForBelotRatingModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FourForBelotEntityModule {}
