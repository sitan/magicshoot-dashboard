import { Moment } from 'moment';
import { IQuote } from 'app/shared/model/quote.model';

export interface IInvoice {
  id?: number;
  invoiceId?: number;
  contactId?: number;
  companyId?: number;
  invoiceNumber?: string;
  orderNumber?: string;
  orderDateTime?: string;
  createdAt?: string;
  modifiedAt?: string;
  quote?: IQuote;
}

export const defaultValue: Readonly<IInvoice> = {};
