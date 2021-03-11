import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IReseller } from 'app/shared/model/reseller.model';
import { getEntities as getResellers } from 'app/entities/reseller/reseller.reducer';
import { getEntity, updateEntity, createEntity, reset } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICompanyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyUpdate = (props: ICompanyUpdateProps) => {
  const [resellerId, setResellerId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { companyEntity, resellers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/company');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getResellers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...companyEntity,
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
          <h2 id="dashboardApp.company.home.createOrEditLabel">
            <Translate contentKey="dashboardApp.company.home.createOrEditLabel">Create or edit a Company</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : companyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="company-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="company-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="companyIdLabel" for="company-companyId">
                  <Translate contentKey="dashboardApp.company.companyId">Company Id</Translate>
                </Label>
                <AvField id="company-companyId" type="string" className="form-control" name="companyId" />
              </AvGroup>
              <AvGroup>
                <Label id="resellerIdLabel" for="company-resellerId">
                  <Translate contentKey="dashboardApp.company.resellerId">Reseller Id</Translate>
                </Label>
                <AvField id="company-resellerId" type="string" className="form-control" name="resellerId" />
              </AvGroup>
              <AvGroup>
                <Label id="companyNameLabel" for="company-companyName">
                  <Translate contentKey="dashboardApp.company.companyName">Company Name</Translate>
                </Label>
                <AvField id="company-companyName" type="text" name="companyName" />
              </AvGroup>
              <AvGroup>
                <Label id="billingAdress1Label" for="company-billingAdress1">
                  <Translate contentKey="dashboardApp.company.billingAdress1">Billing Adress 1</Translate>
                </Label>
                <AvField id="company-billingAdress1" type="text" name="billingAdress1" />
              </AvGroup>
              <AvGroup>
                <Label id="billingAdress2Label" for="company-billingAdress2">
                  <Translate contentKey="dashboardApp.company.billingAdress2">Billing Adress 2</Translate>
                </Label>
                <AvField id="company-billingAdress2" type="text" name="billingAdress2" />
              </AvGroup>
              <AvGroup>
                <Label id="billingZipLabel" for="company-billingZip">
                  <Translate contentKey="dashboardApp.company.billingZip">Billing Zip</Translate>
                </Label>
                <AvField id="company-billingZip" type="text" name="billingZip" />
              </AvGroup>
              <AvGroup>
                <Label id="billingPlaceLabel" for="company-billingPlace">
                  <Translate contentKey="dashboardApp.company.billingPlace">Billing Place</Translate>
                </Label>
                <AvField id="company-billingPlace" type="text" name="billingPlace" />
              </AvGroup>
              <AvGroup>
                <Label id="billingCountryLabel" for="company-billingCountry">
                  <Translate contentKey="dashboardApp.company.billingCountry">Billing Country</Translate>
                </Label>
                <AvField id="company-billingCountry" type="text" name="billingCountry" />
              </AvGroup>
              <AvGroup>
                <Label id="billingEmailLabel" for="company-billingEmail">
                  <Translate contentKey="dashboardApp.company.billingEmail">Billing Email</Translate>
                </Label>
                <AvField id="company-billingEmail" type="text" name="billingEmail" />
              </AvGroup>
              <AvGroup>
                <Label id="billingPhoneLabel" for="company-billingPhone">
                  <Translate contentKey="dashboardApp.company.billingPhone">Billing Phone</Translate>
                </Label>
                <AvField id="company-billingPhone" type="text" name="billingPhone" />
              </AvGroup>
              <AvGroup>
                <Label id="shippingAdress1Label" for="company-shippingAdress1">
                  <Translate contentKey="dashboardApp.company.shippingAdress1">Shipping Adress 1</Translate>
                </Label>
                <AvField id="company-shippingAdress1" type="text" name="shippingAdress1" />
              </AvGroup>
              <AvGroup>
                <Label id="shippingAdress2Label" for="company-shippingAdress2">
                  <Translate contentKey="dashboardApp.company.shippingAdress2">Shipping Adress 2</Translate>
                </Label>
                <AvField id="company-shippingAdress2" type="text" name="shippingAdress2" />
              </AvGroup>
              <AvGroup>
                <Label id="shippingZipLabel" for="company-shippingZip">
                  <Translate contentKey="dashboardApp.company.shippingZip">Shipping Zip</Translate>
                </Label>
                <AvField id="company-shippingZip" type="text" name="shippingZip" />
              </AvGroup>
              <AvGroup>
                <Label id="shippingPlaceLabel" for="company-shippingPlace">
                  <Translate contentKey="dashboardApp.company.shippingPlace">Shipping Place</Translate>
                </Label>
                <AvField id="company-shippingPlace" type="text" name="shippingPlace" />
              </AvGroup>
              <AvGroup>
                <Label id="shippingCountryLabel" for="company-shippingCountry">
                  <Translate contentKey="dashboardApp.company.shippingCountry">Shipping Country</Translate>
                </Label>
                <AvField id="company-shippingCountry" type="text" name="shippingCountry" />
              </AvGroup>
              <AvGroup>
                <Label id="shippingEmailLabel" for="company-shippingEmail">
                  <Translate contentKey="dashboardApp.company.shippingEmail">Shipping Email</Translate>
                </Label>
                <AvField id="company-shippingEmail" type="text" name="shippingEmail" />
              </AvGroup>
              <AvGroup>
                <Label id="shippingPhoneLabel" for="company-shippingPhone">
                  <Translate contentKey="dashboardApp.company.shippingPhone">Shipping Phone</Translate>
                </Label>
                <AvField id="company-shippingPhone" type="text" name="shippingPhone" />
              </AvGroup>
              <AvGroup>
                <Label id="vatPercentageLabel" for="company-vatPercentage">
                  <Translate contentKey="dashboardApp.company.vatPercentage">Vat Percentage</Translate>
                </Label>
                <AvField id="company-vatPercentage" type="string" className="form-control" name="vatPercentage" />
              </AvGroup>
              <AvGroup>
                <Label id="vatNumberLabel" for="company-vatNumber">
                  <Translate contentKey="dashboardApp.company.vatNumber">Vat Number</Translate>
                </Label>
                <AvField id="company-vatNumber" type="text" name="vatNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="companyCreatedAtLabel" for="company-companyCreatedAt">
                  <Translate contentKey="dashboardApp.company.companyCreatedAt">Company Created At</Translate>
                </Label>
                <AvField id="company-companyCreatedAt" type="date" className="form-control" name="companyCreatedAt" />
              </AvGroup>
              <AvGroup>
                <Label id="companyModifiedAtLabel" for="company-companyModifiedAt">
                  <Translate contentKey="dashboardApp.company.companyModifiedAt">Company Modified At</Translate>
                </Label>
                <AvField id="company-companyModifiedAt" type="date" className="form-control" name="companyModifiedAt" />
              </AvGroup>
              <AvGroup>
                <Label for="company-reseller">
                  <Translate contentKey="dashboardApp.company.reseller">Reseller</Translate>
                </Label>
                <AvInput id="company-reseller" type="select" className="form-control" name="reseller.id">
                  <option value="" key="0" />
                  {resellers
                    ? resellers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/company" replace color="info">
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
  resellers: storeState.reseller.entities,
  companyEntity: storeState.company.entity,
  loading: storeState.company.loading,
  updating: storeState.company.updating,
  updateSuccess: storeState.company.updateSuccess,
});

const mapDispatchToProps = {
  getResellers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUpdate);
