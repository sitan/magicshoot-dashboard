import { PrintType } from 'app/shared/model/enumerations/print-type.model';

export interface IPrint {
  id?: number;
  printId?: number;
  printName?: string;
  printType?: PrintType;
  printWidth?: number;
  printHeight?: number;
}

export const defaultValue: Readonly<IPrint> = {};
