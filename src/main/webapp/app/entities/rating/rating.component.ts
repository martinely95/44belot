import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Rating } from './rating.model';
import { RatingService } from './rating.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rating',
    templateUrl: './rating.component.html'
})
export class RatingComponent implements OnInit, OnDestroy {
ratings: Rating[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ratingService: RatingService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ratingService.query().subscribe(
            (res: ResponseWrapper) => {
                this.ratings = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRatings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Rating) {
        return item.id;
    }
    registerChangeInRatings() {
        this.eventSubscriber = this.eventManager.subscribe('ratingListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
