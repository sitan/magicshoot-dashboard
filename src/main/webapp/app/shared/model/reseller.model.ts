import { Moment } from 'moment';
import { ICompany } from 'app/shared/model/company.model';
import { IAdmin } from 'app/shared/model/admin.model';

export interface IReseller {
  id?: number;
  resellerId?: number;
  adminId?: number;
  resellerName?: string;
  resellerEmail?: string;
  resellerPassword?: string;
  resellerCreatedAt?: string;
  resellerModifiedAt?: string;
  companies?: ICompany[];
  admin?: IAdmin;
}

export const defaultValue: Readonly<IReseller> = {};
