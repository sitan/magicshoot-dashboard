import { Moment } from 'moment';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IContact } from 'app/shared/model/contact.model';

export interface IQuote {
  id?: number;
  quoteId?: number;
  contactId?: number;
  companyId?: number;
  quoteDescription?: string;
  quotePrice?: number;
  orderNumber?: string;
  quoteCreatedAt?: string;
  quoteModifiedAt?: string;
  invoices?: IInvoice[];
  contact?: IContact;
}

export const defaultValue: Readonly<IQuote> = {};
