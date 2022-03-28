import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './person-company-history.reducer';
import { IPersonCompanyHistory } from 'app/shared/model/person-company-history.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonCompanyHistoryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const personCompanyHistoryEntity = useAppSelector(state => state.personCompanyHistory.entity);
  const loading = useAppSelector(state => state.personCompanyHistory.loading);
  const updating = useAppSelector(state => state.personCompanyHistory.updating);
  const updateSuccess = useAppSelector(state => state.personCompanyHistory.updateSuccess);
  const handleClose = () => {
    props.history.push('/person-company-history');
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
      ...personCompanyHistoryEntity,
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
          ...personCompanyHistoryEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fluxApp.personCompanyHistory.home.createOrEditLabel" data-cy="PersonCompanyHistoryCreateUpdateHeading">
            <Translate contentKey="fluxApp.personCompanyHistory.home.createOrEditLabel">Create or edit a PersonCompanyHistory</Translate>
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
                  id="person-company-history-id"
                  label={translate('fluxApp.personCompanyHistory.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fluxApp.personCompanyHistory.companyId')}
                id="person-company-history-companyId"
                name="companyId"
                data-cy="companyId"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.personCompanyHistory.personId')}
                id="person-company-history-personId"
                name="personId"
                data-cy="personId"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.personCompanyHistory.investor')}
                id="person-company-history-investor"
                name="investor"
                data-cy="investor"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fluxApp.personCompanyHistory.founder')}
                id="person-company-history-founder"
                name="founder"
                data-cy="founder"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/person-company-history" replace color="info">
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

export default PersonCompanyHistoryUpdate;
