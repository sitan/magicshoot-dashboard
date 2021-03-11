import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MediaIn from './media-in';
import MediaInDetail from './media-in-detail';
import MediaInUpdate from './media-in-update';
import MediaInDeleteDialog from './media-in-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MediaInUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MediaInUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MediaInDetail} />
      <ErrorBoundaryRoute path={match.url} component={MediaIn} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MediaInDeleteDialog} />
  </>
);

export default Routes;
