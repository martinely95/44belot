import { BaseEntity } from './../../shared';

export class Notification implements BaseEntity {
    constructor(
        public id?: number,
        public text?: string,
        public link?: string,
        public dateTime?: any,
        public gameId?: number,
        public profileToBeNotifiedId?: number,
    ) {
    }
}
