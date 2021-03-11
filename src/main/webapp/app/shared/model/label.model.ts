import { LabelType } from 'app/shared/model/enumerations/label-type.model';

export interface ILabel {
  id?: number;
  labelId?: number;
  labelName?: string;
  labelType?: LabelType;
  labelWidth?: number;
  labelHeight?: number;
}

export const defaultValue: Readonly<ILabel> = {};
