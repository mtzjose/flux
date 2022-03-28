import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Founder from './founder';
import FounderDetail from './founder-detail';
import FounderUpdate from './founder-update';
import FounderDeleteDialog from './founder-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FounderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FounderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FounderDetail} />
      <ErrorBoundaryRoute path={match.url} component={Founder} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FounderDeleteDialog} />
  </>
);

export default Routes;
