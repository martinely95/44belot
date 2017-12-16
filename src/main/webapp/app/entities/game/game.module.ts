import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FourForBelotSharedModule } from '../../shared';
import {
    GameService,
    GamePopupService,
    GameComponent,
    GameDetailComponent,
    GameDialogComponent,
    GamePopupComponent,
    GameDeletePopupComponent,
    GameDeleteDialogComponent,
    gameRoute,
    gamePopupRoute,
    GameResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...gameRoute,
    ...gamePopupRoute,
];

@NgModule({
    imports: [
        FourForBelotSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GameComponent,
        GameDetailComponent,
        GameDialogComponent,
        GameDeleteDialogComponent,
        GamePopupComponent,
        GameDeletePopupComponent,
    ],
    entryComponents: [
        GameComponent,
        GameDialogComponent,
        GamePopupComponent,
        GameDeleteDialogComponent,
        GameDeletePopupComponent,
    ],
    providers: [
        GameService,
        GamePopupService,
        GameResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FourForBelotGameModule {}
