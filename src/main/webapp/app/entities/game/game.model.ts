import { BaseEntity } from './../../shared';

export class Game implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public requiredPeopleCount?: number,
        public currentlyAvalablePeople?: number,
        public createdDateTime?: any,
        public startDateTime?: any,
        public endDateTime?: any,
        public requiresApprovalForJoining?: boolean,
        public isAvailableForJoining?: boolean,
        public notifications?: BaseEntity[],
        public ratings?: BaseEntity[],
        public categories?: BaseEntity[],
        public playersProfiles?: BaseEntity[],
        public locationId?: number,
        public creatorProfileId?: number,
    ) {
        this.requiresApprovalForJoining = false;
        this.isAvailableForJoining = false;
    }
}
