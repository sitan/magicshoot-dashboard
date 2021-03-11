import { Moment } from 'moment';
import { IEvent } from 'app/shared/model/event.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IContact } from 'app/shared/model/contact.model';
import { IReseller } from 'app/shared/model/reseller.model';

export interface ICompany {
  id?: number;
  companyId?: number;
  resellerId?: number;
  companyName?: string;
  billingAdress1?: string;
  billingAdress2?: string;
  billingZip?: string;
  billingPlace?: string;
  billingCountry?: string;
  billingEmail?: string;
  billingPhone?: string;
  shippingAdress1?: string;
  shippingAdress2?: string;
  shippingZip?: string;
  shippingPlace?: string;
  shippingCountry?: string;
  shippingEmail?: string;
  shippingPhone?: string;
  vatPercentage?: number;
  vatNumber?: string;
  companyCreatedAt?: string;
  companyModifiedAt?: string;
  events?: IEvent[];
  customers?: ICustomer[];
  contacts?: IContact[];
  reseller?: IReseller;
}

export const defaultValue: Readonly<ICompany> = {};
