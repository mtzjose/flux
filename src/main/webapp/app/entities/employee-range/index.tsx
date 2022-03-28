import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeRange from './employee-range';
import EmployeeRangeDetail from './employee-range-detail';
import EmployeeRangeUpdate from './employee-range-update';
import EmployeeRangeDeleteDialog from './employee-range-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeRangeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeRangeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeRangeDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeRange} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmployeeRangeDeleteDialog} />
  </>
);

export default Routes;
