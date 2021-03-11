import { Moment } from 'moment';

export interface IPayments {
  id?: number;
  paymentId?: number;
  paymentDateTime?: string;
}

export const defaultValue: Readonly<IPayments> = {};
