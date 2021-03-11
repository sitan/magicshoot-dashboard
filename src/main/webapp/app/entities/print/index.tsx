import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Print from './print';
import PrintDetail from './print-detail';
import PrintUpdate from './print-update';
import PrintDeleteDialog from './print-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PrintUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PrintUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PrintDetail} />
      <ErrorBoundaryRoute path={match.url} component={Print} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PrintDeleteDialog} />
  </>
);

export default Routes;
