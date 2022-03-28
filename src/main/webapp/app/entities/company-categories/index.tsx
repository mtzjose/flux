import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CompanyCategories from './company-categories';
import CompanyCategoriesDetail from './company-categories-detail';
import CompanyCategoriesUpdate from './company-categories-update';
import CompanyCategoriesDeleteDialog from './company-categories-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompanyCategoriesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CompanyCategoriesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CompanyCategoriesDetail} />
      <ErrorBoundaryRoute path={match.url} component={CompanyCategories} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CompanyCategoriesDeleteDialog} />
  </>
);

export default Routes;
