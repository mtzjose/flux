import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProcessStage from './process-stage';
import ProcessStageDetail from './process-stage-detail';
import ProcessStageUpdate from './process-stage-update';
import ProcessStageDeleteDialog from './process-stage-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProcessStageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProcessStageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProcessStageDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProcessStage} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProcessStageDeleteDialog} />
  </>
);

export default Routes;
