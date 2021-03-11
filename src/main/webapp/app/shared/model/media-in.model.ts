import { MediaUse } from 'app/shared/model/enumerations/media-use.model';
import { MediaType } from 'app/shared/model/enumerations/media-type.model';

export interface IMediaIn {
  id?: number;
  mediaInId?: number;
  mediaInName?: string;
  mediaUse?: MediaUse;
  mediaInType?: MediaType;
}

export const defaultValue: Readonly<IMediaIn> = {};
