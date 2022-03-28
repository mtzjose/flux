import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './company-position.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompanyPositionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const companyPositionEntity = useAppSelector(state => state.companyPosition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyPositionDetailsHeading">
          <Translate contentKey="fluxApp.companyPosition.detail.title">CompanyPosition</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fluxApp.companyPosition.id">Id</Translate>
            </span>
          </dt>
          <dd>{companyPositionEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="fluxApp.companyPosition.name">Name</Translate>
            </span>
          </dt>
          <dd>{companyPositionEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/company-position" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-position/${companyPositionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyPositionDetail;
