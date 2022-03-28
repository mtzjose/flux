import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './pronoun.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PronounDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const pronounEntity = useAppSelector(state => state.pronoun.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pronounDetailsHeading">
          <Translate contentKey="fluxApp.pronoun.detail.title">Pronoun</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fluxApp.pronoun.id">Id</Translate>
            </span>
          </dt>
          <dd>{pronounEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="fluxApp.pronoun.name">Name</Translate>
            </span>
          </dt>
          <dd>{pronounEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/pronoun" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pronoun/${pronounEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PronounDetail;
