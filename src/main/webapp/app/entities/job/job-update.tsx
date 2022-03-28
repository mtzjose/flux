import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { ICompanyPosition } from 'app/shared/model/company-position.model';
import { getEntities as getCompanyPositions } from 'app/entities/company-position/company-position.reducer';
import { getEntity, updateEntity, createEntity, reset } from './job.reducer';
import { IJob } from 'app/shared/model/job.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const JobUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const companies = useAppSelector(state => state.company.entities);
  const companyPositions = useAppSelector(state => state.companyPosition.entities);
  const jobEntity = useAppSelector(state => state.job.entity);
  const loading = useAppSelector(state => state.job.loading);
  const updating = useAppSelector(state => state.job.updating);
  const updateSuccess = useAppSelector(state => state.job.updateSuccess);
  const handleClose = () => {
    props.history.push('/job' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCompanies({}));
    dispatch(getCompanyPositions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...jobEntity,
      ...values,
      companyId: companies.find(it => it.id.toString() === values.companyId.toString()),
      companyId: companies.find(it => it.id.toString() === values.companyId.toString()),
      jobPositionId: companyPositions.find(it => it.id.toString() === values.jobPositionId.toString()),
      jobPositionId: companyPositions.find(it => it.id.toString() === values.jobPositionId.toString()),
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
          ...jobEntity,
          companyId: jobEntity?.companyId?.id,
          jobPositionId: jobEntity?.jobPositionId?.id,
          companyId: jobEntity?.companyId?.id,
          jobPositionId: jobEntity?.jobPositionId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fluxApp.job.home.createOrEditLabel" data-cy="JobCreateUpdateHeading">
            <Translate contentKey="fluxApp.job.home.createOrEditLabel">Create or edit a Job</Translate>
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
                <ValidatedField name="id" required readOnly id="job-id" label={translate('fluxApp.job.id')} validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label={translate('fluxApp.job.companyId')}
                id="job-companyId"
                name="companyId"
                data-cy="companyId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('fluxApp.job.jobPositionId')}
                id="job-jobPositionId"
                name="jobPositionId"
                data-cy="jobPositionId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="job-companyId"
                name="companyId"
                data-cy="companyId"
                label={translate('fluxApp.job.companyId')}
                type="select"
              >
                <option value="" key="0" />
                {companies
                  ? companies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-jobPositionId"
                name="jobPositionId"
                data-cy="jobPositionId"
                label={translate('fluxApp.job.jobPositionId')}
                type="select"
              >
                <option value="" key="0" />
                {companyPositions
                  ? companyPositions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-companyId"
                name="companyId"
                data-cy="companyId"
                label={translate('fluxApp.job.companyId')}
                type="select"
              >
                <option value="" key="0" />
                {companies
                  ? companies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-jobPositionId"
                name="jobPositionId"
                data-cy="jobPositionId"
                label={translate('fluxApp.job.jobPositionId')}
                type="select"
              >
                <option value="" key="0" />
                {companyPositions
                  ? companyPositions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job" replace color="info">
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

export default JobUpdate;
