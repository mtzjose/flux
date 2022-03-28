import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FounderPositions from './founder-positions';
import FounderPositionsDetail from './founder-positions-detail';
import FounderPositionsUpdate from './founder-positions-update';
import FounderPositionsDeleteDialog from './founder-positions-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FounderPositionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FounderPositionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FounderPositionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={FounderPositions} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FounderPositionsDeleteDialog} />
  </>
);

export default Routes;
