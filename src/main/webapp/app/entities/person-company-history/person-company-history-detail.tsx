import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './person-company-history.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonCompanyHistoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const personCompanyHistoryEntity = useAppSelector(state => state.personCompanyHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personCompanyHistoryDetailsHeading">
          <Translate contentKey="fluxApp.personCompanyHistory.detail.title">PersonCompanyHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fluxApp.personCompanyHistory.id">Id</Translate>
            </span>
          </dt>
          <dd>{personCompanyHistoryEntity.id}</dd>
          <dt>
            <span id="companyId">
              <Translate contentKey="fluxApp.personCompanyHistory.companyId">Company Id</Translate>
            </span>
          </dt>
          <dd>{personCompanyHistoryEntity.companyId}</dd>
          <dt>
            <span id="personId">
              <Translate contentKey="fluxApp.personCompanyHistory.personId">Person Id</Translate>
            </span>
          </dt>
          <dd>{personCompanyHistoryEntity.personId}</dd>
          <dt>
            <span id="investor">
              <Translate contentKey="fluxApp.personCompanyHistory.investor">Investor</Translate>
            </span>
          </dt>
          <dd>{personCompanyHistoryEntity.investor ? 'true' : 'false'}</dd>
          <dt>
            <span id="founder">
              <Translate contentKey="fluxApp.personCompanyHistory.founder">Founder</Translate>
            </span>
          </dt>
          <dd>{personCompanyHistoryEntity.founder ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/person-company-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/person-company-history/${personCompanyHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonCompanyHistoryDetail;
