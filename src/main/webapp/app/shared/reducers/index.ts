import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import admin, {
  AdminState
} from 'app/entities/admin/admin.reducer';
// prettier-ignore
import reseller, {
  ResellerState
} from 'app/entities/reseller/reseller.reducer';
// prettier-ignore
import company, {
  CompanyState
} from 'app/entities/company/company.reducer';
// prettier-ignore
import contact, {
  ContactState
} from 'app/entities/contact/contact.reducer';
// prettier-ignore
import customer, {
  CustomerState
} from 'app/entities/customer/customer.reducer';
// prettier-ignore
import quote, {
  QuoteState
} from 'app/entities/quote/quote.reducer';
// prettier-ignore
import invoice, {
  InvoiceState
} from 'app/entities/invoice/invoice.reducer';
// prettier-ignore
import event, {
  EventState
} from 'app/entities/event/event.reducer';
// prettier-ignore
import device, {
  DeviceState
} from 'app/entities/device/device.reducer';
// prettier-ignore
import mediaIn, {
  MediaInState
} from 'app/entities/media-in/media-in.reducer';
// prettier-ignore
import mediaOut, {
  MediaOutState
} from 'app/entities/media-out/media-out.reducer';
// prettier-ignore
import label, {
  LabelState
} from 'app/entities/label/label.reducer';
// prettier-ignore
import print, {
  PrintState
} from 'app/entities/print/print.reducer';
// prettier-ignore
import payments, {
  PaymentsState
} from 'app/entities/payments/payments.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly admin: AdminState;
  readonly reseller: ResellerState;
  readonly company: CompanyState;
  readonly contact: ContactState;
  readonly customer: CustomerState;
  readonly quote: QuoteState;
  readonly invoice: InvoiceState;
  readonly event: EventState;
  readonly device: DeviceState;
  readonly mediaIn: MediaInState;
  readonly mediaOut: MediaOutState;
  readonly label: LabelState;
  readonly print: PrintState;
  readonly payments: PaymentsState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  admin,
  reseller,
  company,
  contact,
  customer,
  quote,
  invoice,
  event,
  device,
  mediaIn,
  mediaOut,
  label,
  print,
  payments,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
