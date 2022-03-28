import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { IEmployeeRange } from 'app/shared/model/employee-range.model';
import { getEntities as getEmployeeRanges } from 'app/entities/employee-range/employee-range.reducer';
import { getEntity, updateEntity, createEntity, reset } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompanyUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const addresses = useAppSelector(state => state.address.entities);
  const employeeRanges = useAppSelector(state => state.employeeRange.entities);
  const companyEntity = useAppSelector(state => state.company.entity);
  const loading = useAppSelector(state => state.company.loading);
  const updating = useAppSelector(state => state.company.updating);
  const updateSuccess = useAppSelector(state => state.company.updateSuccess);
  const handleClose = () => {
    props.history.push('/company');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAddresses({}));
    dispatch(getEmployeeRanges({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...companyEntity,
      ...values,
      addressId: addresses.find(it => it.id.toString() === values.addressId.toString()),
      employeeRange: employeeRanges.find(it => it.id.toString() === values.employeeRange.toString()),
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
          ...companyEntity,
          addressId: companyEntity?.addressId?.id,
          employeeRange: companyEntity?.employeeRange?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fluxApp.company.home.createOrEditLabel" data-cy="CompanyCreateUpdateHeading">
            <Translate contentKey="fluxApp.company.home.createOrEditLabel">Create or edit a Company</Translate>
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
                  id="company-id"
                  label={translate('fluxApp.company.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('fluxApp.company.meta')} id="company-meta" name="meta" data-cy="meta" type="textarea" />
              <ValidatedBlobField
                label={translate('fluxApp.company.logo')}
                id="company-logo"
                name="logo"
                data-cy="logo"
                isImage
                accept="image/*"
              />
              <ValidatedField label={translate('fluxApp.company.color')} id="company-color" name="color" data-cy="color" type="text" />
              <ValidatedField label={translate('fluxApp.company.name')} id="company-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('fluxApp.company.legalName')}
                id="company-legalName"
                name="legalName"
                data-cy="legalName"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.company.oneLiner')}
                id="company-oneLiner"
                name="oneLiner"
                data-cy="oneLiner"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.company.description')}
                id="company-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('fluxApp.company.foundingDate')}
                id="company-foundingDate"
                name="foundingDate"
                data-cy="foundingDate"
                type="date"
              />
              <ValidatedField
                label={translate('fluxApp.company.socialLinks')}
                id="company-socialLinks"
                name="socialLinks"
                data-cy="socialLinks"
                type="textarea"
              />
              <ValidatedField
                label={translate('fluxApp.company.addressId')}
                id="company-addressId"
                name="addressId"
                data-cy="addressId"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.company.employeeRange')}
                id="company-employeeRange"
                name="employeeRange"
                data-cy="employeeRange"
                type="text"
              />
              <ValidatedField
                id="company-addressId"
                name="addressId"
                data-cy="addressId"
                label={translate('fluxApp.company.addressId')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="company-employeeRange"
                name="employeeRange"
                data-cy="employeeRange"
                label={translate('fluxApp.company.employeeRange')}
                type="select"
              >
                <option value="" key="0" />
                {employeeRanges
                  ? employeeRanges.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/company" replace color="info">
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

export default CompanyUpdate;
