import { BaseEntity } from './../../shared';

export class Category implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public gamesPlayed?: number,
        public children?: BaseEntity[],
        public parentId?: number,
        public interestedProfiles?: BaseEntity[],
        public gamesWithThisCategories?: BaseEntity[],
    ) {
    }
}
