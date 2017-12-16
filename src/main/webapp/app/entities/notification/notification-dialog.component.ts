import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Notification } from './notification.model';
import { NotificationPopupService } from './notification-popup.service';
import { NotificationService } from './notification.service';
import { Game, GameService } from '../game';
import { Profile, ProfileService } from '../profile';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-notification-dialog',
    templateUrl: './notification-dialog.component.html'
})
export class NotificationDialogComponent implements OnInit {

    notification: Notification;
    isSaving: boolean;

    games: Game[];

    profiles: Profile[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private notificationService: NotificationService,
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
        if (this.notification.id !== undefined) {
            this.subscribeToSaveResponse(
                this.notificationService.update(this.notification));
        } else {
            this.subscribeToSaveResponse(
                this.notificationService.create(this.notification));
        }
    }

    private subscribeToSaveResponse(result: Observable<Notification>) {
        result.subscribe((res: Notification) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Notification) {
        this.eventManager.broadcast({ name: 'notificationListModification', content: 'OK'});
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
    selector: 'jhi-notification-popup',
    template: ''
})
export class NotificationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private notificationPopupService: NotificationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.notificationPopupService
                    .open(NotificationDialogComponent as Component, params['id']);
            } else {
                this.notificationPopupService
                    .open(NotificationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
