import { BaseEntity } from './../../shared';

export const enum Language {
    'ENGLISH',
    'RUSSIAN'
}

export const enum Sex {
    'MALE',
    'FEMALE'
}

export class Profile implements BaseEntity {
    constructor(
        public id?: number,
        public language?: Language,
        public sex?: Sex,
        public age?: number,
        public gamesPlayed?: number,
        public gamesWon?: number,
        public gamesLost?: number,
        public userId?: number,
        public notifications?: BaseEntity[],
        public createdGames?: BaseEntity[],
        public givenRatings?: BaseEntity[],
        public receivedRatings?: BaseEntity[],
        public categoriesOfInterests?: BaseEntity[],
        public playedGames?: BaseEntity[],
    ) {
    }
}
