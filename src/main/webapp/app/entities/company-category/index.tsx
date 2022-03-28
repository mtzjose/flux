import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CompanyCategory from './company-category';
import CompanyCategoryDetail from './company-category-detail';
import CompanyCategoryUpdate from './company-category-update';
import CompanyCategoryDeleteDialog from './company-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompanyCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CompanyCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CompanyCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={CompanyCategory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CompanyCategoryDeleteDialog} />
  </>
);

export default Routes;
