import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './company-categories.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompanyCategoriesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const companyCategoriesEntity = useAppSelector(state => state.companyCategories.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyCategoriesDetailsHeading">
          <Translate contentKey="fluxApp.companyCategories.detail.title">CompanyCategories</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fluxApp.companyCategories.id">Id</Translate>
            </span>
          </dt>
          <dd>{companyCategoriesEntity.id}</dd>
          <dt>
            <span id="companyId">
              <Translate contentKey="fluxApp.companyCategories.companyId">Company Id</Translate>
            </span>
          </dt>
          <dd>{companyCategoriesEntity.companyId}</dd>
          <dt>
            <span id="categoryId">
              <Translate contentKey="fluxApp.companyCategories.categoryId">Category Id</Translate>
            </span>
          </dt>
          <dd>{companyCategoriesEntity.categoryId}</dd>
        </dl>
        <Button tag={Link} to="/company-categories" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-categories/${companyCategoriesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyCategoriesDetail;
