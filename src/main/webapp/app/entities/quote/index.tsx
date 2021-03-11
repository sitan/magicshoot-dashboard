import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Quote from './quote';
import QuoteDetail from './quote-detail';
import QuoteUpdate from './quote-update';
import QuoteDeleteDialog from './quote-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={QuoteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={QuoteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={QuoteDetail} />
      <ErrorBoundaryRoute path={match.url} component={Quote} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={QuoteDeleteDialog} />
  </>
);

export default Routes;
