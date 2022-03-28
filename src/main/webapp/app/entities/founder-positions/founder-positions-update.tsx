import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './founder-positions.reducer';
import { IFounderPositions } from 'app/shared/model/founder-positions.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FounderPositionsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const founderPositionsEntity = useAppSelector(state => state.founderPositions.entity);
  const loading = useAppSelector(state => state.founderPositions.loading);
  const updating = useAppSelector(state => state.founderPositions.updating);
  const updateSuccess = useAppSelector(state => state.founderPositions.updateSuccess);
  const handleClose = () => {
    props.history.push('/founder-positions');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...founderPositionsEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...founderPositionsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fluxApp.founderPositions.home.createOrEditLabel" data-cy="FounderPositionsCreateUpdateHeading">
            <Translate contentKey="fluxApp.founderPositions.home.createOrEditLabel">Create or edit a FounderPositions</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="founder-positions-id"
                  label={translate('fluxApp.founderPositions.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fluxApp.founderPositions.positionId')}
                id="founder-positions-positionId"
                name="positionId"
                data-cy="positionId"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.founderPositions.companyId')}
                id="founder-positions-companyId"
                name="companyId"
                data-cy="companyId"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/founder-positions" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default FounderPositionsUpdate;
