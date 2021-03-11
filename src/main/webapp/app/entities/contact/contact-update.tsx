import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { getEntity, updateEntity, createEntity, reset } from './contact.reducer';
import { IContact } from 'app/shared/model/contact.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IContactUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ContactUpdate = (props: IContactUpdateProps) => {
  const [companyId, setCompanyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { contactEntity, companies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/contact');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCompanies();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...contactEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dashboardApp.contact.home.createOrEditLabel">
            <Translate contentKey="dashboardApp.contact.home.createOrEditLabel">Create or edit a Contact</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : contactEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="contact-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="contact-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contactIdLabel" for="contact-contactId">
                  <Translate contentKey="dashboardApp.contact.contactId">Contact Id</Translate>
                </Label>
                <AvField id="contact-contactId" type="string" className="form-control" name="contactId" />
              </AvGroup>
              <AvGroup>
                <Label id="contactNameLabel" for="contact-contactName">
                  <Translate contentKey="dashboardApp.contact.contactName">Contact Name</Translate>
                </Label>
                <AvField id="contact-contactName" type="text" name="contactName" />
              </AvGroup>
              <AvGroup>
                <Label id="companyIdLabel" for="contact-companyId">
                  <Translate contentKey="dashboardApp.contact.companyId">Company Id</Translate>
                </Label>
                <AvField id="contact-companyId" type="string" className="form-control" name="companyId" />
              </AvGroup>
              <AvGroup>
                <Label id="contactTelephoneLabel" for="contact-contactTelephone">
                  <Translate contentKey="dashboardApp.contact.contactTelephone">Contact Telephone</Translate>
                </Label>
                <AvField id="contact-contactTelephone" type="text" name="contactTelephone" />
              </AvGroup>
              <AvGroup>
                <Label id="contactEmailLabel" for="contact-contactEmail">
                  <Translate contentKey="dashboardApp.contact.contactEmail">Contact Email</Translate>
                </Label>
                <AvField id="contact-contactEmail" type="text" name="contactEmail" />
              </AvGroup>
              <AvGroup>
                <Label id="contactPasswordLabel" for="contact-contactPassword">
                  <Translate contentKey="dashboardApp.contact.contactPassword">Contact Password</Translate>
                </Label>
                <AvField id="contact-contactPassword" type="text" name="contactPassword" />
              </AvGroup>
              <AvGroup>
                <Label id="contactCreatedAtLabel" for="contact-contactCreatedAt">
                  <Translate contentKey="dashboardApp.contact.contactCreatedAt">Contact Created At</Translate>
                </Label>
                <AvField id="contact-contactCreatedAt" type="date" className="form-control" name="contactCreatedAt" />
              </AvGroup>
              <AvGroup>
                <Label id="contactModifiedAtLabel" for="contact-contactModifiedAt">
                  <Translate contentKey="dashboardApp.contact.contactModifiedAt">Contact Modified At</Translate>
                </Label>
                <AvField id="contact-contactModifiedAt" type="date" className="form-control" name="contactModifiedAt" />
              </AvGroup>
              <AvGroup>
                <Label for="contact-company">
                  <Translate contentKey="dashboardApp.contact.company">Company</Translate>
                </Label>
                <AvInput id="contact-company" type="select" className="form-control" name="company.id">
                  <option value="" key="0" />
                  {companies
                    ? companies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/contact" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  companies: storeState.company.entities,
  contactEntity: storeState.contact.entity,
  loading: storeState.contact.loading,
  updating: storeState.contact.updating,
  updateSuccess: storeState.contact.updateSuccess,
});

const mapDispatchToProps = {
  getCompanies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ContactUpdate);
