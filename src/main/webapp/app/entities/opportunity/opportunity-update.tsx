import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { IContactSource } from 'app/shared/model/contact-source.model';
import { getEntities as getContactSources } from 'app/entities/contact-source/contact-source.reducer';
import { IProcessStage } from 'app/shared/model/process-stage.model';
import { getEntities as getProcessStages } from 'app/entities/process-stage/process-stage.reducer';
import { getEntity, updateEntity, createEntity, reset } from './opportunity.reducer';
import { IOpportunity } from 'app/shared/model/opportunity.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OpportunityUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const companies = useAppSelector(state => state.company.entities);
  const contactSources = useAppSelector(state => state.contactSource.entities);
  const processStages = useAppSelector(state => state.processStage.entities);
  const opportunityEntity = useAppSelector(state => state.opportunity.entity);
  const loading = useAppSelector(state => state.opportunity.loading);
  const updating = useAppSelector(state => state.opportunity.updating);
  const updateSuccess = useAppSelector(state => state.opportunity.updateSuccess);
  const handleClose = () => {
    props.history.push('/opportunity');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCompanies({}));
    dispatch(getContactSources({}));
    dispatch(getProcessStages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...opportunityEntity,
      ...values,
      companyId: companies.find(it => it.id.toString() === values.companyId.toString()),
      contactSourceId: contactSources.find(it => it.id.toString() === values.contactSourceId.toString()),
      processStageId: processStages.find(it => it.id.toString() === values.processStageId.toString()),
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
          ...opportunityEntity,
          companyId: opportunityEntity?.companyId?.id,
          contactSourceId: opportunityEntity?.contactSourceId?.id,
          processStageId: opportunityEntity?.processStageId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fluxApp.opportunity.home.createOrEditLabel" data-cy="OpportunityCreateUpdateHeading">
            <Translate contentKey="fluxApp.opportunity.home.createOrEditLabel">Create or edit a Opportunity</Translate>
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
                  id="opportunity-id"
                  label={translate('fluxApp.opportunity.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fluxApp.opportunity.companyId')}
                id="opportunity-companyId"
                name="companyId"
                data-cy="companyId"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.opportunity.applyDate')}
                id="opportunity-applyDate"
                name="applyDate"
                data-cy="applyDate"
                type="date"
              />
              <ValidatedField
                label={translate('fluxApp.opportunity.contactSourceId')}
                id="opportunity-contactSourceId"
                name="contactSourceId"
                data-cy="contactSourceId"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.opportunity.processStageId')}
                id="opportunity-processStageId"
                name="processStageId"
                data-cy="processStageId"
                type="text"
              />
              <ValidatedField
                id="opportunity-companyId"
                name="companyId"
                data-cy="companyId"
                label={translate('fluxApp.opportunity.companyId')}
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
                id="opportunity-contactSourceId"
                name="contactSourceId"
                data-cy="contactSourceId"
                label={translate('fluxApp.opportunity.contactSourceId')}
                type="select"
              >
                <option value="" key="0" />
                {contactSources
                  ? contactSources.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="opportunity-processStageId"
                name="processStageId"
                data-cy="processStageId"
                label={translate('fluxApp.opportunity.processStageId')}
                type="select"
              >
                <option value="" key="0" />
                {processStages
                  ? processStages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/opportunity" replace color="info">
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

export default OpportunityUpdate;
