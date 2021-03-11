import { MediaType } from 'app/shared/model/enumerations/media-type.model';

export interface IMediaOut {
  id?: number;
  mediaOutId?: number;
  mediaOutName?: string;
  mediaOutType?: MediaType;
}

export const defaultValue: Readonly<IMediaOut> = {};
