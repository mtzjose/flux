import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './founder-positions.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FounderPositionsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const founderPositionsEntity = useAppSelector(state => state.founderPositions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="founderPositionsDetailsHeading">
          <Translate contentKey="fluxApp.founderPositions.detail.title">FounderPositions</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fluxApp.founderPositions.id">Id</Translate>
            </span>
          </dt>
          <dd>{founderPositionsEntity.id}</dd>
          <dt>
            <span id="positionId">
              <Translate contentKey="fluxApp.founderPositions.positionId">Position Id</Translate>
            </span>
          </dt>
          <dd>{founderPositionsEntity.positionId}</dd>
          <dt>
            <span id="companyId">
              <Translate contentKey="fluxApp.founderPositions.companyId">Company Id</Translate>
            </span>
          </dt>
          <dd>{founderPositionsEntity.companyId}</dd>
        </dl>
        <Button tag={Link} to="/founder-positions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/founder-positions/${founderPositionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FounderPositionsDetail;
