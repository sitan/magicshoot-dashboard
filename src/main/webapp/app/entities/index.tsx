import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Admin from './admin';
import Reseller from './reseller';
import Company from './company';
import Contact from './contact';
import Customer from './customer';
import Quote from './quote';
import Invoice from './invoice';
import Event from './event';
import Device from './device';
import MediaIn from './media-in';
import MediaOut from './media-out';
import Label from './label';
import Print from './print';
import Payments from './payments';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}admin`} component={Admin} />
      <ErrorBoundaryRoute path={`${match.url}reseller`} component={Reseller} />
      <ErrorBoundaryRoute path={`${match.url}company`} component={Company} />
      <ErrorBoundaryRoute path={`${match.url}contact`} component={Contact} />
      <ErrorBoundaryRoute path={`${match.url}customer`} component={Customer} />
      <ErrorBoundaryRoute path={`${match.url}quote`} component={Quote} />
      <ErrorBoundaryRoute path={`${match.url}invoice`} component={Invoice} />
      <ErrorBoundaryRoute path={`${match.url}event`} component={Event} />
      <ErrorBoundaryRoute path={`${match.url}device`} component={Device} />
      <ErrorBoundaryRoute path={`${match.url}media-in`} component={MediaIn} />
      <ErrorBoundaryRoute path={`${match.url}media-out`} component={MediaOut} />
      <ErrorBoundaryRoute path={`${match.url}label`} component={Label} />
      <ErrorBoundaryRoute path={`${match.url}print`} component={Print} />
      <ErrorBoundaryRoute path={`${match.url}payments`} component={Payments} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
