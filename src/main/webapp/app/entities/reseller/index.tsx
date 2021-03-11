import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Reseller from './reseller';
import ResellerDetail from './reseller-detail';
import ResellerUpdate from './reseller-update';
import ResellerDeleteDialog from './reseller-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResellerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResellerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResellerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Reseller} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ResellerDeleteDialog} />
  </>
);

export default Routes;
