import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FourForBelotSharedModule } from '../../shared';
import {
    RatingService,
    RatingPopupService,
    RatingComponent,
    RatingDetailComponent,
    RatingDialogComponent,
    RatingPopupComponent,
    RatingDeletePopupComponent,
    RatingDeleteDialogComponent,
    ratingRoute,
    ratingPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ratingRoute,
    ...ratingPopupRoute,
];

@NgModule({
    imports: [
        FourForBelotSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RatingComponent,
        RatingDetailComponent,
        RatingDialogComponent,
        RatingDeleteDialogComponent,
        RatingPopupComponent,
        RatingDeletePopupComponent,
    ],
    entryComponents: [
        RatingComponent,
        RatingDialogComponent,
        RatingPopupComponent,
        RatingDeleteDialogComponent,
        RatingDeletePopupComponent,
    ],
    providers: [
        RatingService,
        RatingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FourForBelotRatingModule {}
