import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MediaOut from './media-out';
import MediaOutDetail from './media-out-detail';
import MediaOutUpdate from './media-out-update';
import MediaOutDeleteDialog from './media-out-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MediaOutUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MediaOutUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MediaOutDetail} />
      <ErrorBoundaryRoute path={match.url} component={MediaOut} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MediaOutDeleteDialog} />
  </>
);

export default Routes;
