import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './company-category.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompanyCategoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const companyCategoryEntity = useAppSelector(state => state.companyCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyCategoryDetailsHeading">
          <Translate contentKey="fluxApp.companyCategory.detail.title">CompanyCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fluxApp.companyCategory.id">Id</Translate>
            </span>
          </dt>
          <dd>{companyCategoryEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="fluxApp.companyCategory.name">Name</Translate>
            </span>
          </dt>
          <dd>{companyCategoryEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/company-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-category/${companyCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyCategoryDetail;
