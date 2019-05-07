import { Moment } from 'moment';

export const enum IncidentType {
  THEFT = 'THEFT',
  PETTY_TEFT = 'PETTY_TEFT',
  NOISE = 'NOISE',
  ALCOHOL = 'ALCOHOL',
  ATTACK = 'ATTACK',
  PROPERTY = 'PROPERTY',
  DAMAGE = 'DAMAGE',
  DEBOUCH = 'DEBOUCH'
}

export interface IIncident {
  id?: number;
  type?: IncidentType;
  date?: Moment;
  place?: string;
  hotel?: string;
  personNameHash?: string;
  personDocumentHash?: string;
  phoneNumberHash?: string;
  description?: string;
}

export class Incident implements IIncident {
  constructor(
    public id?: number,
    public type?: IncidentType,
    public date?: Moment,
    public place?: string,
    public hotel?: string,
    public personNameHash?: string,
    public personDocumentHash?: string,
    public phoneNumberHash?: string,
    public description?: string
  ) {}
}
