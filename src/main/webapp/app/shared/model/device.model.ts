import { DeviceType } from 'app/shared/model/enumerations/device-type.model';

export interface IDevice {
  id?: number;
  deviceId?: number;
  deviceName?: string;
  deviceType?: DeviceType;
}

export const defaultValue: Readonly<IDevice> = {};
