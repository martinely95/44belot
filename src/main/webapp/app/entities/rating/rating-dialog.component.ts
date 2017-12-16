import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Rating } from './rating.model';
import { RatingPopupService } from './rating-popup.service';
import { RatingService } from './rating.service';
import { Game, GameService } from '../game';
import { Profile, ProfileService } from '../profile';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rating-dialog',
    templateUrl: './rating-dialog.component.html'
})
export class RatingDialogComponent implements OnInit {

    rating: Rating;
    isSaving: boolean;

    games: Game[];

    profiles: Profile[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ratingService: RatingService,
        private gameService: GameService,
        private profileService: ProfileService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.gameService.query()
            .subscribe((res: ResponseWrapper) => { this.games = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.profileService.query()
            .subscribe((res: ResponseWrapper) => { this.profiles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rating.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ratingService.update(this.rating));
        } else {
            this.subscribeToSaveResponse(
                this.ratingService.create(this.rating));
        }
    }

    private subscribeToSaveResponse(result: Observable<Rating>) {
        result.subscribe((res: Rating) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Rating) {
        this.eventManager.broadcast({ name: 'ratingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackGameById(index: number, item: Game) {
        return item.id;
    }

    trackProfileById(index: number, item: Profile) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rating-popup',
    template: ''
})
export class RatingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ratingPopupService: RatingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ratingPopupService
                    .open(RatingDialogComponent as Component, params['id']);
            } else {
                this.ratingPopupService
                    .open(RatingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
