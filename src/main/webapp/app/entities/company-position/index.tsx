import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CompanyPosition from './company-position';
import CompanyPositionDetail from './company-position-detail';
import CompanyPositionUpdate from './company-position-update';
import CompanyPositionDeleteDialog from './company-position-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompanyPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CompanyPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CompanyPositionDetail} />
      <ErrorBoundaryRoute path={match.url} component={CompanyPosition} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CompanyPositionDeleteDialog} />
  </>
);

export default Routes;
