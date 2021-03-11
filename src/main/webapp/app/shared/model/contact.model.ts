import { Moment } from 'moment';
import { IQuote } from 'app/shared/model/quote.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IContact {
  id?: number;
  contactId?: number;
  contactName?: string;
  companyId?: number;
  contactTelephone?: string;
  contactEmail?: string;
  contactPassword?: string;
  contactCreatedAt?: string;
  contactModifiedAt?: string;
  quotes?: IQuote[];
  company?: ICompany;
}

export const defaultValue: Readonly<IContact> = {};
