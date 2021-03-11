import { Moment } from 'moment';
import { IReseller } from 'app/shared/model/reseller.model';

export interface IAdmin {
  id?: number;
  adminId?: number;
  adminName?: string;
  adminEmail?: string;
  adminPassword?: string;
  adminCreatedAt?: string;
  adminModifiedAt?: string;
  resellers?: IReseller[];
}

export const defaultValue: Readonly<IAdmin> = {};
