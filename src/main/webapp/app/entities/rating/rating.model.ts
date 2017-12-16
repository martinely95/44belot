import { BaseEntity } from './../../shared';

export const enum RatingScore {
    'BAD',
    'MODERATE',
    'GOOD',
    'EXPERT',
    'GOD'
}

export class Rating implements BaseEntity {
    constructor(
        public id?: number,
        public teamPlayer?: RatingScore,
        public friendly?: RatingScore,
        public leader?: RatingScore,
        public playedGameId?: number,
        public giverProfileId?: number,
        public receiverProfileId?: number,
    ) {
    }
}
