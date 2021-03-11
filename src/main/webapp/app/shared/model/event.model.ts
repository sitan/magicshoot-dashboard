import { Moment } from 'moment';
import { ICompany } from 'app/shared/model/company.model';
import { EventType } from 'app/shared/model/enumerations/event-type.model';

export interface IEvent {
  id?: number;
  eventId?: number;
  companyId?: number;
  eventName?: string;
  eventStartDate?: string;
  eventEndDate?: string;
  eventStartTime?: string;
  eventEndTime?: string;
  eventType?: EventType;
  company?: ICompany;
}

export const defaultValue: Readonly<IEvent> = {};
