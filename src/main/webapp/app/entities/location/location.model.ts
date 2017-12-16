import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public longtitude?: number,
        public latitude?: number,
        public url?: string,
        public games?: BaseEntity[],
    ) {
    }
}
