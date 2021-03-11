import { Moment } from 'moment';
import { ICompany } from 'app/shared/model/company.model';

export interface ICustomer {
  id?: number;
  customerId?: number;
  companyId?: number;
  contactEmail?: string;
  contactCreatedAt?: string;
  contactModifiedAt?: string;
  company?: ICompany;
}

export const defaultValue: Readonly<ICustomer> = {};
